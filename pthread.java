class PrimeThread extends Thread {
    private int start;
    private int end;
    private int pCount;
    private long sTime;
    public PrimeThread(int start, int end) {
        this.start = start;
        this.end = end;
        this.pCount = 0;
        this.sTime = 0;
    }

    @Override
    public void run() {
        long startTime = System.nanoTime();

        for (int i = start; i <= end; i++) {
            if (isPrime(i)) {
                pCount++;
            }
        }

        long endTime = System.nanoTime();
        sTime = endTime - startTime;
    }

    public int getPCount() {
        return pCount;
    }

    public long getSTime() {
        return sTime;
    }

    public int getRangeSize() {
        return end - start + 1;
    }

    private static boolean isPrime(int num) {
        if (num < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
