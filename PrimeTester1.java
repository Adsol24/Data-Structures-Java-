public class PrimeTester1 {

    public static final int MAX = 100000000;

    public static void main(String[] args) throws InterruptedException {
        long start = System.nanoTime();

        PrimeThread pThread1 = new PrimeThread(3, MAX / 3);
        PrimeThread pThread2 = new PrimeThread(MAX / 3 + 1, 2 * MAX / 3);
        PrimeThread pThread3 = new PrimeThread(2 * MAX / 3 + 1, MAX);

        PrimeThread[] threads = new PrimeThread[3];
        threads[0] = pThread1;
        threads[1] = pThread2;
        threads[2] = pThread3;

        for (PrimeThread t : threads)
            t.start();

        for (PrimeThread t : threads)
            t.join();

        long end = System.nanoTime();
        long nTime = end - start;
        String sTime = getsTime(nTime);

        int totalPrimes = 0;

        for (PrimeThread t : threads) {
            System.out.println("Thread " + t.getName() + " Prime Count = " + t.getPCount());
            System.out.println("# Seconds Used = " + t.getSTime());
            System.out.println();
            totalPrimes += t.getPCount();
        }

        System.out.printf("Main # Seconds Used = %s%n", sTime);
        System.out.printf("Total Primes: %,d%n", totalPrimes);

        int totalRange = pThread1.getRangeSize() + pThread2.getRangeSize() + pThread3.getRangeSize();
        System.out.printf("Total Range: %d%n", totalRange);
    }

    public static String getsTime(long nTime) {
        return String.format("%6.3f", nTime / 1000000000.0);
    }
}