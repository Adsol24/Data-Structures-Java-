//File: MenuRecords.java
import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JFrame;


import static sun.java2d.Disposer.addRecord;


public class MenuRecordsTODO
{
    public void openFile() {
        try {
            file = new RandomAccessFile("rand.dat", "rw");
        } catch (IOException e) {
            System.err.println("File not opened properly\n" + e.toString());
            System.exit(1);
        }
    }


    public void closeFile() {
        try {
            if (file != null)
                file.close();
        } catch (IOException ioe) { }
    }


    private Record record;
    private RandomAccessFile file;
    private double balance;
    private Scanner kbd = new Scanner(System.in);
    public MenuRecordsTODO() {
        record = new Record();
        try
        {
            file = new RandomAccessFile( "rand.dat", "rw" );
        }
        catch( IOException e )
        {
            System.err.println( "File not opened properly\n" + e.toString() );
            System.exit( 1 ); }
    }
    public void menu()
    {
        System.out.printf("%nChoose an Option%n");
        System.out.printf("0 - Display All%n");
        System.out.printf("1 - Display by Name%n");
        System.out.printf("2 - Display by Balance%n");
        System.out.printf("3 - Add Record%n");
        System.out.printf("4 - Delete Record%n");
        System.out.printf("5 - File Dump%n");
        System.out.printf("6 - Print to Textfile%n");
        System.out.printf("7 - Display Record it Position (0 is first)%n");
        System.out.printf("8 - Quit%n");
    }
    public void runMenu()
    {
        openFile();
        do {
            menu();
            String input = kbd.nextLine();
            int choice = Integer.parseInt(input);
            System.out.printf("%n");
            switch(choice)
            {
                case 0: displayAll(); break;
                case 1: displayByName(); break;
                case 2: displayByBalance(); break;
                case 3: addRecord(); break;
                case 4: deleteRecord(); break;
                case 5: fileDump(); break;
                case 6: printToTextfile(); break;
                case 7: displayByPosition(); break;
                case 8: closeFile();
                    return;
            }
            try { Thread.currentThread().sleep(2000); }
            catch (InterruptedException ie) { }
        } while (true);
    }


    private void addRecord() {
        openFile();

        try {
            Record newRecord = new Record();

            System.out.print("Enter First Name: ");
            String firstName = kbd.nextLine();
            System.out.print("Enter Last Name: ");
            String lastName = kbd.nextLine();
            System.out.print("Enter Account Number: ");
            int account = Integer.parseInt(kbd.nextLine());
            System.out.print("Enter Balance: ");
            double balance = Double.parseDouble(kbd.nextLine());

            newRecord.setAccount(account);
            newRecord.setFirstName(firstName);
            newRecord.setLastName(lastName);
            newRecord.setBalance(balance);


            file.seek(file.length());


            newRecord.write(file);

            System.out.println("Record added successfully.");
        } catch (IOException e) {
            System.err.println("Error adding a record: " + e.getMessage());
        } finally {
            closeFile();
        }
    }

    public void deleteRecord() {

        String lastNameToDelete = JOptionPane.showInputDialog("Enter last name to delete");

        boolean recordDeleted = false;

        try {
            long fileLength = file.length();
            long recordSize = Record.size();
            file.seek(0L);

            while (file.getFilePointer() < fileLength) {
                long currentFilePointer = file.getFilePointer();
                record.read(file);


                if (record.getLastName().trim().equalsIgnoreCase(lastNameToDelete.trim())) {

                    record.setFirstName("VOID");
                    file.seek(currentFilePointer);
                    record.write(file);
                    recordDeleted = true;
                    break;
                }
            }

            if (recordDeleted) {
                System.out.println("Record with last name '" + lastNameToDelete + "' successfully deleted.");
            } else {
                System.out.println("Record with last name '" + lastNameToDelete + "' not found. Nothing deleted.");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }



    public void displayByName() {
        String lastname = JOptionPane.showInputDialog("Enter last name to find");
        System.out.println("name = " + lastname);

        System.out.printf("%-15s%-15s%-9s%-10s%n", "First", "Last", "Account", "Balance");
        System.out.printf("%-15s%-15s%-9s%-10s%n", "-----", "----", "-------", "-------");

        try {
            long end = file.length();
            file.seek(0L);
            while (file.getFilePointer() < end) {
                record.read(file);

                // Check if the last name of the record matches the name to search for
                if (record.getLastName().trim().equalsIgnoreCase(lastname .trim())) {
                    System.out.println(record);
                }
            }

            System.out.println("Records with the last name '" + lastname + "' displayed");
        } catch (IOException ioe) {
        }
    }




    public void displayByPosition() {
        String pos = JOptionPane.showInputDialog("Enter position (0 is 1st record, 1 is second ...)");
        int position = Integer.parseInt(pos);

        System.out.printf("%-15s%-15s%-9s%-10s%n", "First", "Last", "Account", "Balance");
        System.out.printf("%-15s%-15s%-9s%-10s%n", "-----", "----", "-------", "-------");

        try {
            long end = file.length();
            long recordSize = Record.size();
            long seekPosition = position * recordSize;

            if (seekPosition < end) {
                file.seek(seekPosition);
                record.read(file);
                System.out.println(record);
            } else {
                System.out.println("Record number " + position + " does not exist");
            }
        } catch (IOException ioe) {

        }
    }



        public void displayAll()
    {


        System.out.printf("%-15s%-15s%-9s%-10s%n","First", "Last", "Account",
                "Balance");
        System.out.printf("%-15s%-15s%-9s%-10s%n","-----", "----", "-------",
                "-------");
        try
        {
            long end = file.length();
            file.seek(0L);
            while (!(file.getFilePointer() >= end))
            {
                record.read(file);
                if (!record.getFirstName().contains("VOID"))
                    System.out.println(record);
            }
            System.out.println("All records displayed");
        }
        catch (IOException ioe) { }
    }
        public void printToTextfile()
    {
        PrintWriter out = null;
        String filename = JOptionPane.showInputDialog("Please Enter the Filename");
        try
        {
            out = new PrintWriter(filename);
        }
        catch(IOException ioe) { }
        out.printf("%-15s%-15s%-9s%-10s%n","First", "Last", "Account", "Balance");
        out.printf("%-15s%-15s%-9s%-10s%n","-----", "----", "-------", "-------");
        try
        {
            long end = file.length();
            file.seek(0L);
            while (file.getFilePointer() != end)
            {
                record.read(file);
                if (!record.getFirstName().contains("VOID"))
                    out.println(record);
            }
            out.println("All records displayed");
            out.close();
            System.out.println("All recoreds written to " + filename + " file");
        }
        catch (IOException ioe) { }
    }
    public void fileDump() {

        System.out.printf("%-15s%-15s%-9s%-10s%n", "First", "Last", "Account", "Balance");
        System.out.printf("%-15s%-15s%-9s%-10s%n", "-----", "----", "-------", "-------");

        try {
            long fileLength = file.length();
            long recordSize = Record.size();
            file.seek(0L);

            while (file.getFilePointer() < fileLength) {
                record.read(file);
                System.out.println(record);
            }

            System.out.println("All records, including those with 'VOID' in the first name, displayed");
        } catch (IOException ioe) {

        }
    }


    public void displayByBalance() {

        System.out.printf("%-15s%-15s%-9s%-10s%n","First", "Last", "Account",
                "Balance");
        System.out.printf("%-15s%-15s%-9s%-10s%n","-----", "----", "-------",
                "-------");

        String bal = JOptionPane.showInputDialog("Enter Balance");
        double balance = Double.parseDouble(bal);

        try{
            long fileLength = file.length();
            long recordSize = Record.size();
            file.seek(0L);

            while(file.getFilePointer() < fileLength){
                record.read(file);

                if(record.getBalance() == balance){
                    System.out.println(record);
                }
            }
            System.out.println("Records with Balances less then or equal to " + balance + " displayed");
        }catch(IOException e){

        }


    }

 public static void main(String[] args) {
    MenuRecordsTODO menu = new MenuRecordsTODO();
    menu.runMenu();
}
}

