// File: CreateFile.java
import java.io.*;
public class CreateFile {
    private Record record;
    private RandomAccessFile file;
    String [] last = {"Aurelius","Bellingham","Jones","Vick","Pacino",
            "Soprano","Lillard", "Biden"};
    String [] first = {"Marcus", "Jude", "Jon", "Michael", "Al",
            "Tony","Damian","Joe"};
    int [] account = { 1001, 1003, 1005, 1006, 1102,5556,1009,2388 };
    double [] bal = {1075.35, 755, 23000, 45000, 115560.77, 3005.11,1000000, -999999999
    };
    public CreateFile() {
        record = new Record();
        try {
            file = new RandomAccessFile( "rand.dat", "rw" );
            for (int i = 0 ; i < last.length ; i++) {
                record.setAccount(account[i]);
                record.setBalance(bal[i]);
                record.setFirstName(first[i]);
                record.setLastName(last[i]);
                record.write(file);
                System.out.println("file ptr at offset: " + file.getFilePointer());
            } // for loop
        } // try
        catch( IOException e ) {
            System.err.println( "File not opened properly\n" +
                    e.toString() );
            System.exit(1); }
        try
        {
            file.seek(1*Record.size());
            record.read(file);
            System.out.println(record);
            file.seek(file.length());
            record.setAccount(99999);
            record.setBalance(9999.99);
            record.setFirstName("Micas");
            record.setLastName("Wetherall");
            record.write(file);
        }
        catch (IOException ioe) { }
        try {
            file.close(); }
        catch (IOException ioe) { }
    }
    public static void main( String args[] ) {
        CreateFile accounts = new CreateFile();
        System.out.println("Done");
    } }