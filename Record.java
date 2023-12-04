// File: Record.java
import java.io.*;
public class Record {
    private int account;
    private String lastName;
    private String firstName;
    private double balance;
    private static final int LENGTH = 15;
    public void read( RandomAccessFile file ) throws IOException
    {
        account = file.readInt();
        firstName = file.readUTF();
        lastName = file.readUTF();
        balance = file.readDouble();
    }
    public void write( RandomAccessFile file ) throws IOException
    {
        file.writeInt(account);
        StringBuffer buf = new StringBuffer();
        buf.setLength(LENGTH);
        for (int i = 0; i < firstName.length(); i++)
            buf.setCharAt(i, firstName.charAt(i));
        for (int i = firstName.length(); i < LENGTH; i++)
            buf.setCharAt(i, ' ');
        file.writeUTF(buf.toString());
        for (int i = 0; i < lastName.length(); i++)
            buf.setCharAt(i, lastName.charAt(i));
        for (int i = lastName.length(); i < LENGTH; i++)
            buf.setCharAt(i, ' ');
        buf.setLength(LENGTH);
        file.writeUTF(buf.toString());
        file.writeDouble( balance );
    }
    public void setAccount( int a ) { account = a; }
    public int getAccount() { return account; }
    public void setFirstName( String f ) { firstName = new String(f); }
    public String getFirstName() { return firstName; }
    public void setLastName( String l ) { lastName = new String(l); }
    public String getLastName() { return lastName; }
    public void setBalance( double b ) { balance = b; }
    public double getBalance() { return balance; }
    public String toString() {
        return String.format("%-15s",firstName) + String.format("%-15s",lastName)
                + String.format("%-8d",account) + " " + String.format("$%,10.2f",balance); }
    // NOTE: This method contains a hard coded value for the
// size of a record of information.
    public static int size() { return 46; } }