import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
public class FindPrimeNumbers implements Runnable {
	static ArrayList<Long> PrimeArray = new ArrayList<>();
	long beginNum = 0, offset = 0;
	
	public FindPrimeNumbers(long beginNum, long offset) { this.beginNum = beginNum; this.offset = offset;}
	
	public void run() {
		ArrayList<Long> primes = getPrimes(beginNum,beginNum+offset);
		if(!primes.isEmpty())
			PrimeArray.addAll(primes);
		//System.out.println("Thread"+ Thread.currentThread().getName()+" received the segment: ["+
		//beginNum+ "," + (beginNum+offset)+"] " + "and added: " +getPrimes(beginNum,beginNum+offset));
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		long max = Long.parseLong(in.next());
		long L = Long.parseLong(in.next());
		int N = Integer.parseInt(in.next());
		
		
		System.out.println("try1: " + getPrimes(0,max).toString());
		long startTime = System.nanoTime();
		
		long offset = max / (N-2), beginNum = 0;
		Thread[] threads = new Thread[N-1]; // create an array of threads
		for(int i = 0; i < N-1; i++) {
			String threadName = Integer.toString(i);
			if(i == N-2) offset = max-beginNum; // for those that is not included because of the complete integer division
			// note: there are at most N-3 of those (this num is exactly max%(N-2))
			
			threads[i]=new Thread(new FindPrimeNumbers(beginNum,offset),threadName);
			beginNum +=offset;
		}
		int i = 0;
		for (Thread thread : threads) {
			//System.out.println("started thread "+ i++);
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
		
		//ArrayList<Long> primes = new ArrayList<>();
		//for(int i = 0; i < N-1; i++) primes.addAll(PrimeArray.get(i));
		
		System.out.println("prime array before sorting: "+PrimeArray.toString());
		printTime(startTime);
		
		if(!PrimeArray.isEmpty())
			Collections.sort(PrimeArray);
		System.out.println("prime array after sorting: "+PrimeArray.toString());
		printTime(startTime);

	}
	public static boolean isPrime(long num){ // basic prime function
		if(num < 2) return false;
		for(int i = 2; i*i <= num; i++) {
			if((int)num%i == 0) return false;
		}
		return true;
	}
	public static synchronized ArrayList<Long> getPrimes(long startNum, long endNum) {
		ArrayList<Long> primes = new ArrayList<>();
		for(long num = startNum; num < endNum; num++) {
			if(isPrime(num)) primes.add(num);
		}
		return primes;
	}
	public static void printTime(long startTime) {
		long difference = System.nanoTime() - startTime;
		System.out.println("Total execution time: " +
		                String.format("%d min, %d sec\n",
		                TimeUnit.NANOSECONDS.toHours(difference),
		                TimeUnit.NANOSECONDS.toSeconds(difference) -
		                              TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(difference))));
		
	}
}


