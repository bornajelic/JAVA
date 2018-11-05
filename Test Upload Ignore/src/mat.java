import java.util.Scanner;
import java.io.*;

/*
 * 1) popuniti gdje nedostaje throws <klasa> u deklaraciji funkcije
 * 2) popuniti gdje su ... unutar funkcije
 */

class ProblemSMatricama extends Exception
{
    ProblemSMatricama(){ super("neodredjeni problem s matricama"); }    
}
 
abstract class Matrica {
    abstract public double get( int i, int j ) /*...*/ ;
    abstract public void set( int i, int j, double v ) /*...*/ ;
    abstract public int getRedova();
    abstract public int getStupaca();
    static public void mnozenje( Matrica A, Matrica B, Matrica C ) /*...*/
    {
        // napraviti po uzoru na zbrajanje
        // ...
    }
    static public void zbrajanje( Matrica A, Matrica B, Matrica C ) /*...*/
    {
        if( !(A.getRedova() == B.getRedova() &&
              A.getStupaca() == B.getStupaca() &&
              A.getRedova() == C.getRedova() &&
              A.getStupaca() == C.getStupaca() ) )throw new ProblemSMatricama();
        
        int i, j;
        
        for( i=0; i<A.getRedova(); i++ )
          for( j=0; j<A.getStupaca(); j++ )
            C.set( i, j, A.get(i,j)+B.get(i,j) );
    }
    public void ispis() throws ProblemSMatricama
    {
        System.out.println( getRedova() + " " + getStupaca() );
        
        int i, j;
        for( i=0; i<getRedova(); i++ ){
          for( j=0; j<getStupaca(); j++ )
            System.out.printf("%8.1f",get(i,j));
          System.out.println();
        }
    }
}

class MatricaM extends Matrica
{
    private int redova, stupaca;
    private double p[][];
    public MatricaM( Scanner s ) /*...*/
    {
        // ucitati iz s: 1*int redova, 1*int stupaca, redova*stupaca*double                
        redova = s.nextInt();
        stupaca = s.nextInt();
        
        p = new double[redova][stupaca];
        
        int i, j;
        
        for( i=0; i<getRedova(); i++ )
          for( j=0; j<getStupaca(); j++ )
            p[i][j] = s.nextDouble();        
    }
    public MatricaM( int redova, int stupaca ) /*...*/
    {
        // konstruktor koji alocira i popuni matricu nulama        
        // ...
    }
    public double get( int i, int j ) throws ProblemSMatricama
    { 
        return p[i][j]; 
    }
    public void set( int i, int j, double v ) throws ProblemSMatricama
    {
        p[i][j] = v;
    }
    public int getRedova(){ /* ... */ }
    public int getStupaca(){ return stupaca; }
}
class MatricaD extends Matrica
{
    final int sizeof_double = 8;
    int redova, stupaca;
    RandomAccessFile f;
    long p0;  //pamti se mjesto u datoteci gdje pocinju elementi, to se koristi za seek
    
    public MatricaD( RandomAccessFile f ) throws IOException, ProblemSMatricama
    {
        // ucitati iz f: 1*int redova, 1*int stupaca, redova*stupaca*double                
        // tj. kao MatricaM samo pomocu RandomAccessFile.
        // u funkciji main() napraviti otvaranje i zatvaranje od f.
        
        this.f = f;        
        redova = f.readInt();
        stupaca = f.readInt();
        p0 = f.getFilePointer(); //pamti se trenutno mjesto u datoteci jer tu pocinju elementi
    }
    public MatricaD( RandomAccessFile f, int redova, int stupaca ) throws IOException, ProblemSMatricama
    {
        // konstruktor koji upise u datoteku i popuni matricu nulama        

        // inicijalizirati f, redova, stupaca
        // ...
        
        // upisati u f: 1*int redova, 1*int stupaca, redova*stupaca*double                
        
        f.writeInt( redova );
        f.writeInt( stupaca );
        
        p0 = f.getFilePointer();  //pamti se trenutno mjesto u datoteci jer ce tu poceti elementi

        // spremiti u f samo nule
        // ... 
    }
    public double get( int i, int j ) throws ProblemSMatricama
    {
        // citanje s odredjenog mjesta u datoteci
        try{
            f.seek( p0 + (i * stupaca + j) * sizeof_double );
            return f.readDouble();
        }catch( IOException e ){
            throw new ProblemSMatricama();
        }
    }
    public void set( int i, int j, double v ) throws ProblemSMatricama
    {
        // ...
    }
    public int getRedova(){ return redova; }
    public int getStupaca(){ /* ... */ }
}