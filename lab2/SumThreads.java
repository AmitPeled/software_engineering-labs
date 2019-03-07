import java.util.concurrent.TimeUnit;

public class SumThreads implements Runnable {
	static long[] sum = new long[10];
	long beginNum = 0, offset = 0;
	
	public SumThreads(long beginNum, long offset) { this.beginNum = beginNum; this.offset = offset;}
	
	public void run() {
		int threadNum = Integer.parseInt(Thread.currentThread().getName());
		sum[threadNum] = 0;
		while(offset-- > 0) 
			sum[threadNum] += (beginNum+offset);
	}
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		
		long offset = ((long) 1<<32) / 10, beginNum = 0;
		Thread[] threads = new Thread[10]; // create an array of threads
		for(int i = 0; i < 10; i++) {
			String threadName = Integer.toString(i);
			threads[i]=new Thread(new SumThreads(beginNum,offset),threadName);
			beginNum +=offset;
		}
		for (Thread thread : threads) {
			 thread.start(); // start the threads
		}
		for (Thread thread : threads) {
			 try{
				 thread.join(); // finish the threads
			 }
			 catch(InterruptedException e) {
				 e.printStackTrace();
				 } 
			}
		
		long totalSum = 0;
		for(int i = 0; i < 10; i++) totalSum+=sum[i];
		
		System.out.println("The sum is "+totalSum);
		long difference = System.nanoTime() - startTime;
		System.out.println("Total execution time: " +
		                String.format("%d min, %d sec",
		                TimeUnit.NANOSECONDS.toHours(difference),
		                TimeUnit.NANOSECONDS.toSeconds(difference) -
		                              TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(difference))));
		
	}
}
