import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.EOFException;

public class InputBuffer {
    private RandomAccessFile file;
    private int BUFFER_SIZE;
    private byte[] buffer;
    private int currR;
    private int length;
    private boolean finishedFile = false;
    private String name;

    public InputBuffer(RandomAccessFile file, int size, String name) {
        this.file = file;
        BUFFER_SIZE = size;
        buffer = new byte[BUFFER_SIZE];
        currR = 0;
        length = 0;
        this.name = name;
    }

    public byte read() throws IOException
    {
        if (empty())
        {
            fill();
            if (finishedFile)
                throw new IOException("completed");
        }
        return buffer[currR++];
    }
    public boolean empty() {
        return currR >= length;
    }

    public void fill()
    {
        int n = 0;
        try {
            n = file.read(buffer);
            if (n == -1) {
                System.out.println("File Read Completely");
                finishedFile = true;
            }
        }
        catch (EOFException eof) { }
        catch (IOException ioe) { System.err.println("Error in fillBuffer()");
            ioe.printStackTrace(); }
        length = n;
        currR = 0;

    }
    }
