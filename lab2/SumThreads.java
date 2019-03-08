import java.util.concurrent.TimeUnit;

public class SumThreads implements Runnable {
	static int NUM_THREADS=10;
	static long[] sum = new long[NUM_THREADS];
	long beginNum = 0, offset = 0;
	int index = 0;
	
	public SumThreads(long beginNum, long offset,int index) { this.beginNum = beginNum; this.offset = offset;this.index=index;}
	
	public void run() {
		long temp=beginNum+offset;
		while(temp-- > beginNum)
			sum[index] += temp;
	}
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		//set the threads
		long offset = ((long) 1<<32) / sum.length, beginNum = 0;
		Thread[] threads = new Thread[sum.length]; // create an array of threads
		for(int i = 0; i < sum.length-1; i++) {
			sum[i]=0;
			threads[i]=new Thread(new SumThreads(beginNum,offset,i));
			beginNum +=offset;
		}
		sum[sum.length-1]=0;
		threads[sum.length-1]=new Thread(new SumThreads(beginNum,((long) 1<<32)-beginNum,sum.length-1));
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
