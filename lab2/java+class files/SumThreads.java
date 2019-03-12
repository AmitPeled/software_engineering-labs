import java.util.concurrent.TimeUnit;

public class SumThreads implements Runnable {
	static int NUM_THREADS=10;
	static long[] sum = new long[NUM_THREADS];
	long offset = 0;
	int index = 0;
	public SumThreads(long offset,int index) { this.offset = offset;this.index=index;}
	
	public void run() {
		int temp = 0;
		while(temp++ < offset)
			sum[index] += 1;
	}
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		//set the threads
		long offset = ((long) 1<<32) / sum.length;
		Thread[] threads = new Thread[sum.length]; // create an array of threads
		for(int i = 0; i < sum.length-1; i++) {
			sum[i]=0;
			threads[i]=new Thread(new SumThreads(offset,i));
		}
		sum[sum.length-1]=0;
		threads[sum.length-1]=new Thread(new SumThreads((offset + ((long) 1<<32)%sum.length),sum.length-1)); // completing the range space
		//start the threads
		for (Thread thread : threads) {
			 thread.start(); // start the threads
		}
		//wait for the threads to finish
		for (Thread thread : threads) {
			 try{
				 thread.join(); // finish the threads
			 }
			 catch(InterruptedException e) {
				 e.printStackTrace();
				 } 
			}
		//sum the results
		long totalSum = 0;
		for(long l : sum) totalSum+=l;
		//print the time
		System.out.println("The sum is "+totalSum);
		long difference = System.nanoTime() - startTime;
		System.out.println("Total execution time: " +
		                String.format("%d min, %d sec",
		                TimeUnit.NANOSECONDS.toHours(difference),
		                TimeUnit.NANOSECONDS.toSeconds(difference) -
		                              TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(difference))));
		
	}
}
