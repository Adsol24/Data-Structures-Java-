import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.EOFException;

public class OutputBuffer {
    private RandomAccessFile file;
    private int BUFFER_SIZE;
    private byte[] buffer;
    private int currW;
    private int length;
    private String name;

    public OutputBuffer(RandomAccessFile file, int size, String name) {
        this.file = file;
        BUFFER_SIZE = size;
        buffer = new byte[BUFFER_SIZE];
        currW = 0;
        length = 0;
        this.name = name;
    }

    public boolean full() {
        return currW >= BUFFER_SIZE;
    }

    public void writeToFile() throws IOException
    {
        int count = 0;
        file.write(buffer, 0, length);
        length = 0;
        currW = 0;
    }
    public void append(byte value) throws IOException
    {
        if (full())
            writeToFile();
        buffer[currW++] = value;
        length++;
    }

    public void clear() {
        currW = length = 0;
    }

    public void reset() throws IOException {
        file.seek(0L);
    }
}