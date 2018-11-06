import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream.GetField;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Crypto {
	/*
	 * fileovi nisu valjani.. ovo nisu fileovi od te zadace.. no cisto
	 * da se poanta skuzi,
	 */
	private static int SIZE = 4096;
	//digest 2 check -> 96628666249F0F60FBE5FA0AE3748C876D4DBDE5E1C385155C5CE71E01EEBC4D
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		File file1 = new File("hw07test.bin");
		File file2 = new File("hw07part2.bin");
		
		
		
		//checksha(file1,sc);
		/*
		 * javlja mi badPadding except... ne valja key
		 */
		AES("decrypt", file2);
		
		
	}
	
	public static void checksha(File file,Scanner sc) throws IOException {
	
		
		InputStream inputStream = new FileInputStream(file);
		
		
		System.out.println("Please provide expected sha-256 digest for file: ");
		String expectedDigest = sc.nextLine(); //ovo je u hex
		MessageDigest SHA256 = null;
		
		try{
			SHA256 = MessageDigest.getInstance("SHA-256");
			byte[] data = new byte[SIZE];
			int bytes2Read = 0; 
			
			while(bytes2Read != -1) {
				bytes2Read = inputStream.read(data);
				SHA256.update(data);	
			}	
		}catch(NoSuchAlgorithmException e) {
			e.getStackTrace();
		}
		
		byte[] digest = SHA256.digest();
		String output = Util.bytesToHex(digest);
		System.out.println(output);
		if(output.equals(expectedDigest)) {
			System.out.println("Good match! :)");
		}else {
			System.out.println("WARNING! BAD MATCH");
		}
		inputStream.close();
		sc.close();
	}
	
	@SuppressWarnings("resource")
	public static void AES(String algorithm,File file) throws Exception {
		
		Scanner sc = new Scanner(System.in);
		InputStream inputStream = new FileInputStream(file);
		String path = "C:\\Users\\barto\\workspace\\Crypto\\proba.txt";
		OutputStream output = new FileOutputStream(path);
		System.out.println("Please provide hex encoded text"); 
		String hexText = sc.nextLine();
		
		System.out.println("Please provide initialization vector");
		String initVector = sc.nextLine();
		
		AlgorithmParameterSpec iv = generateIV(initVector);
		SecretKeySpec key = generateKey(hexText);
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		byte[] data = new byte[SIZE];
		int byte2Read = 0;
		String operation;
		
		switch(algorithm) {
		
			case "decrypt":
				c.init(Cipher.DECRYPT_MODE, key,iv);
				operation = "Decryption";
				break;
			case "encrypt":
				c.init(Cipher.ENCRYPT_MODE, key,iv);
				operation = "Encryption";
				break;
			default: 
				throw new Exception();
		}
		
		while(byte2Read != -1) {
			
			byte2Read = inputStream.read(data);
			
			if(byte2Read < SIZE) {
				output.write(c.doFinal(data));
			}else {
				output.write(c.update(data));
			}
			
			
		}
	
		
		System.out.println(operation + " ended!");
		inputStream.close();
		sc.close();
		
		return;
	}
	
	private static SecretKeySpec generateKey(String str) throws Exception {
		
		SecretKeySpec key = new SecretKeySpec(Util.hexToByte(str), "AES");
		return key;
	}
	
	private static AlgorithmParameterSpec generateIV(String str) {
		
		AlgorithmParameterSpec initializationVector = new IvParameterSpec(Util.hexToByte(str));
		return initializationVector;
	
	}
}
