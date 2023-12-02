import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import javax.swing.JFileChooser;
public class file_copyatt
{
    private static final int BUFFER_SIZE = 20000;
    private static InputBuffer inBuf;
    private static OutputBuffer outBuf;
    public static void main(String[] args)
    {
        JFileChooser jfc = new JFileChooser(".");
        RandomAccessFile in = null, out = null;
        int returnValue = jfc.showOpenDialog(null);
        File selectedFile = null;
        String copyName = null;
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = jfc.getSelectedFile();
            copyName = selectedFile.getAbsolutePath();
        }
        try
        {
            in = new RandomAccessFile(selectedFile,"r");
            String ext = copyName.substring(copyName.indexOf(".")+1);
            copyName = copyName.substring(0,copyName.indexOf("."));
            copyName = copyName + ".COPY." + ext;
            System.out.println("copyName = " + copyName);
            out = new RandomAccessFile(copyName,"rw");
        }
        catch (FileNotFoundException fnfe) { }
        createBuffers(in, out);

        long startTime = System.nanoTime();
        try
        {
            while (true)
                outBuf.append(inBuf.read());
        }
        catch (IOException ioe) {
        }
        try
        {
            outBuf.writeToFile();
            in.close();
            out.close();
        }
        catch (IOException ioe)
        {
        }
        long endTime = System.nanoTime();
        long totalNanos = endTime-startTime;
        long minutes = totalNanos/1000000000/60;
        totalNanos -= minutes*60000000000L;
        long seconds = (int) (totalNanos/1000000000.0);
        totalNanos -= seconds*1000000000L;
        long milliSeconds = (int) (totalNanos/1000000.0);
        System.out.printf("%nTime: %d:%d:%d %n%n", minutes,seconds,milliSeconds);
        System.out.println("Simple File Copy Complete!");
    }
    private static void createBuffers(RandomAccessFile in, RandomAccessFile out)
    {
        inBuf = new InputBuffer(in, BUFFER_SIZE, "inBuf");
        outBuf = new OutputBuffer(out, BUFFER_SIZE, "outBuf");
    }
}