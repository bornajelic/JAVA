import java.util.Arrays;

public class Util {

	private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

	
	public static String bytesToHex(byte[] bytes) {

		char[] hexChars = new char[bytes.length * 2];

		for (int i = 0; i < bytes.length; i++) {
			int v = bytes[i] & 0xFF; // maskiram da dobijem oktet
			
			if(v >> 4 == 0000) {
				hexChars[i * 2] = '0';
			}else {
				hexChars[i * 2] = hexArray[v >>> 4];
			}
			
			if((v & 0x0F) == 0000) { // maskiram da dobijem pola okteta
				
				hexChars[i * 2 + 1] = '0';
			}else {
				hexChars[i * 2 + 1] = hexArray[v & 0x0F];
			}
			
			
		}
		return new String(hexChars);
	}
	
	public static byte[] hexToByte(String keyText) {

		if (keyText.length() % 2 != 0) {
			keyText = "0" + keyText;
		}

		byte[] keyByte = new byte[keyText.length() / 2];

		for (int i = 0; i < keyByte.length; i++) {
			
			int hexValue = (Character.digit(keyText.charAt(2 * i), 16) << 4)
					+ Character.digit(keyText.charAt(2 * i + 1), 16);
			keyByte[i] = (byte) hexValue;

		}

		return keyByte;
	}

	public static void main(String[] args) throws Exception {

		byte[] bytes = {1, -82 ,34 };

		String s = bytesToHex(bytes);

		System.out.println(s);
		
		System.out.println(Arrays.toString(hexToByte("01aE22")));
		
	}
}
