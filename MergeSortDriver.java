import java.io.IOException;
public class MergeSortDriver
{
    private static int SIZE = 32*1024;
    public static void main(String[] args) throws IOException
    {
        SimpleMergeSort sms = new SimpleMergeSort(SIZE);
// skip if data too big to print
        if (SIZE < 64)
        {
            System.out.println("Unsorted Data");
            sms.printData();
        }
        long startTime = System.nanoTime();
        sms.mergesort();
        long endTime = System.nanoTime();
// skip if data too big to print
        if (SIZE < 64)
        {
            System.out.println("Sorted Data");
            sms.printData();
        }
        sms.display(25);
        long totalNanos = endTime-startTime;
        long minutes = totalNanos/1000000000/60;
        totalNanos -= minutes*60000000000L;
        long seconds = (int) (totalNanos/1000000000.0);
        totalNanos -= seconds*1000000000L;
        long milliSeconds = (int) (totalNanos/1000000.0);
        System.out.println(sms.getRunInfo());
        System.out.printf("%nTime: %02d:%02d:%03d %n%n",
                minutes,seconds,milliSeconds);
        System.out.println("MergeSort Complete!");
        System.out.println("Check Order = " + sms.checkOrder());
    }
}
