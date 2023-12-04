// File: ProcessFile.java
import java.io.*;
public class ProcessFile {
    private Record record;
    private RandomAccessFile file;
    private double balance;
    public ProcessFile() {
        record = new Record();
        try {
            file = new RandomAccessFile( "rand.dat", "rw" );
            System.out.println ("Data from File:\n");
            while (true) {
                record.read(file);
                System.out.println(record.toString());
            } // while loop
        } // try
        catch(EOFException eof) { }
        catch( IOException e ) {
            System.err.println( "File not opened properly\n" + e.toString() );
            System.exit( 1 ); }
        long location = -1L;
        try {
            location = file.getFilePointer(); }
        catch(IOException ioe) { }
        System.out.printf("Location is now: %d",location);
        try {
            file.seek(0L); // rewind the file
            while (true) {
                location = file.getFilePointer();
                System.out.printf("%nLocation is now: %d%n",location);
                record.read(file);
                if (record.getBalance() <= 1000) ; // do nothing
                else
                {
                    if (record.getBalance()>1000 && record.getBalance()< 10000)
                        record.setBalance(record.getBalance() * 1.05);
                    else
                        record.setBalance(record.getBalance() * 1.15);
                    file.seek(location);
                    record.write(file);
                }
            } } // try
        catch (EOFException eof) { }
        catch (IOException ioe) { }
        System.out.println ("\nUpdated Data from File:\n");
        try
        {
            file.seek(0L);
            while (true)
            {
                record.read(file);
                System.out.println(record.toString());
            } // while loop
        } // try
        catch(EOFException eof) { }
        catch( IOException e ) { }
    }
    public static void main( String args[] ) {
        ProcessFile accounts = new ProcessFile(); } }