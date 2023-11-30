import java.util.Random;
import java.io.RandomAccessFile;
import java.io.IOException;
public class SimpleMergeSort
{
    private int size;
    private int maxPass;
    private RandomAccessFile F, A, B;
    private StringBuffer runInfo;
    public SimpleMergeSort() throws IOException
    {
        this(1024);
    }
    public SimpleMergeSort(int size) throws IOException
    {
        this.size = size;
        maxPass = (int)Math.ceil(Math.log(size)/Math.log(2));
        createFiles();
        runInfo = new StringBuffer(2048);
        runInfo.append("MaxPass = " + maxPass + "\n\n");
    }
    private void createFiles() throws IOException {
        Random rand = new Random();
        F = new RandomAccessFile("F.bin", "rw");
        A = new RandomAccessFile("A.bin", "rw");
        B = new RandomAccessFile("B.bin", "rw");
        for (int i = 0; i < size ; i++)
            F.writeInt(rand.nextInt(10000));
        for (int i = 0; i < size/2; i++)
        {
            A.writeInt(0);
            B.writeInt(0);
        } // for
    }
    public void printData() throws IOException {
        F.seek(0L);
        for (int i = 0; i < size ; i++)
        { System.out.print("(" + F.getFilePointer() + ") ");
            System.out.println("" + F.readInt());
        }
    }
    public void mergesort() throws IOException
    {
        for(int pass = 0, count = 1; pass < maxPass; pass++, count *=2)
        {
            split(F, A, B, count);
            merge(F, A, B, count);
        }
    }
    private void split(RandomAccessFile F, RandomAccessFile A, RandomAccessFile B, int n) throws IOException {
        F.seek(0L);
        A.seek(0L);
        B.seek(0L);
        runInfo.append("Split: " + n + "\n");
        byte[] buff = new byte[512 * 4];

        int packet = n <= (512 * 4) ? n : (512 * 4);
        int iterations = n > (512 * 4) ? n / (512 * 4) : 1;

        for (int i = 0; i < size / (2 * n); i++) {
            for (int it = 0; it < iterations; it++) {
                F.read(buff);
                A.write(buff);
            }
            for (int it = 0; it < iterations; it++) {
                F.read(buff);
                B.write(buff);
            }
        }
    }


    private void merge(RandomAccessFile F, RandomAccessFile A, RandomAccessFile B, int
            n) throws IOException
    {
        runInfo.append("Merge: " + n + "\n");
        int a, b; // current values from subfiles A and B
        int aCount = 0, bCount = 0; // count of remaining elements in the A and b subgroups
        F.seek(0L);
        A.seek(0L);
        B.seek(0L);
        for (int i = 0; i < size/(2*n); i++)
        {
            aCount = bCount = n;
            a = A.readInt();
            b = B.readInt();
            while(aCount != 0 && bCount != 0)
            {
                if (a < b)
                {
                    F.writeInt(a);
                    aCount--;
                    if(aCount != 0)
                        a = A.readInt();
                }
                else
                {
                    F.writeInt(b);
                    bCount--;
                    if(bCount != 0)
                        b = B.readInt();
                }
            } // while
            while(aCount-- != 0)
            {
                F.writeInt(a);
                if(aCount != 0)
                    a = A.readInt();
            }
            while(bCount-- != 0)
            {
                F.writeInt(b);
                if(bCount != 0)
                    b = B.readInt();
            }
        } // for
    }
    public String getRunInfo()
    {
        return runInfo.toString();
    }
    public void display(int n) throws IOException
    {
        F.seek(0L);
        System.out.print("[");
        for (int i = 0; i < n; i++)
            System.out.print(F.readInt() + ",");
        System.out.print(" ... ");
        F.seek(size*4 - n*4);
        for (int i = 0; i < n-1; i++)
            System.out.print(F.readInt() + ",");
        System.out.println(F.readInt() + "]");
    }
    public boolean checkOrder() throws IOException
    {
        F.seek(0L);
        int cur = F.readInt();
        for(int i = 1; i < size-1; i++)
        {
            int next = F.readInt();
            if (next < cur)
            {
                System.out.println("i = " + i + " cur = " + cur + " next = " +
                        next);
                return false;
            }
            else
                cur = next;
        }
        return true;
    }
} // SimpleMergeSo