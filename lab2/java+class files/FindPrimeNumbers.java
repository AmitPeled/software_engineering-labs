import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;
import java.math.BigInteger;
public class FindPrimeNumbers implements Runnable {
	static Object lock=new Object();
	static Integer producersAlive=0;
	static ArrayList<Long> PrimeArray = new ArrayList<>();
	long beginNum = 0, offset = 0, L=0;
	boolean isProducer;

	public FindPrimeNumbers(long beginNum, long offset) { this.beginNum = beginNum; this.offset = offset;this.isProducer=true;}
	public FindPrimeNumbers(long L) {this.isProducer=false;this.L=L;}
	
	public void run() {
		if(isProducer)
			producer();
		else
			consumer();
	}

	public void producer()
	{
		long endNum=beginNum+offset;
		for(long num = beginNum; num < endNum; num++) {
			if(isPrime(num))
				synchronized (lock){
					PrimeArray.add(num);
					lock.notifyAll(); //releases the consumer if it's waiting for primes to print
				}
		}
		synchronized(lock) {
			producersAlive--;
			lock.notifyAll(); //releases the consumer the check whether all the producers are finished
		}
	}
	public void consumer()
	{
		System.out.print("Primes before sorting:");
		int index=0;
		synchronized (lock)
		{
			while(producersAlive>0)
			{
				//wait for next prime
				while(index>=PrimeArray.size() && producersAlive>0)
				{
					try {
						lock.wait(); // waiting for prime to be loaded
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(index<PrimeArray.size())
				{
					//print it
					if(index%L==0)
						System.out.print("\n");
					System.out.print(" "+PrimeArray.get(index));
					index++;
				}
			}
		}
		//print sorted
		if(!PrimeArray.isEmpty())
			Collections.sort(PrimeArray);
		System.out.print("\nPrime array after sorting: ");
		for(index=0; index<PrimeArray.size(); index++)
		{
			if(index%L==0)
				System.out.print("\n");
			System.out.print(" "+PrimeArray.get(index));
		}
		System.out.print("\n");
	}
	
	public static void main(String[] args) {

		
		/**  runnable jar creation **/
		/** ------------------------------------------------------------ **/

//		JUnitCore junit = new JUnitCore();
//		  junit.addListener(new TextListener(System.out));
//		  org.junit.runner.Result result = junit.run(FindPrimeNumbers.class); // Replace "SampleTest" with the name of your class
//		  if (result.getFailureCount() > 0) {
//		    System.out.println("Test failed.");
//		    System.exit(1);
//		  } else {
//		    System.out.println("Test finished successfully.");
//		    System.exit(0);
//		  }

		 /** ------------------------------------------------------------ **/
		  
		Scanner in = new Scanner(System.in);
		System.out.println("Enter (3) parameters:");
		long max = in.nextLong();
		long L = in.nextLong();
		int N = in.nextInt();
		long startTime = System.nanoTime();

		//create threads
		long offset = max / (N-1), beginNum = 0;
		Thread[] producers = new Thread[N-1];
		int i;
		for(i = 0; i < N-2; i++) {
			producers[i]=new Thread(new FindPrimeNumbers(beginNum,offset),"producer"+i);
			beginNum +=offset;
		}
		//the last producer thread gets an interal of offset + max%offset values to cover all values- because of integer complete division
		producers[i]=new Thread(new FindPrimeNumbers(beginNum,max-beginNum),"producer"+i); 
		Thread consumer=new Thread(new FindPrimeNumbers(L),"consumer");

		//start the threads
		synchronized (producersAlive) {
			producersAlive=N-1;
		}
		for (Thread thread : producers) {
			thread.start();
		}
		consumer.start();

		// wait for all of them to finish
		for (Thread thread : producers) {
			 try{
				 thread.join();
			 }
			 catch(InterruptedException e) {
				 e.printStackTrace();
			 } 
		}
		try{
			consumer.join();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}

		//print the time it took
		printTimeFrom(startTime);
		
	}
	
	public static boolean isPrime(long num){ // returns true if num is prime.
		return new  BigInteger(Objects.toString(num,"4")).isProbablePrime(1); //"4" handles the case num is null- false will be returned (as 4 isn't prime)
		}
	
	public static void printTimeFrom(long startTime) {
		long difference = System.nanoTime() - startTime;
		System.out.println("Total execution time: " +
		                String.format("%d min, %d sec\n",
		                TimeUnit.NANOSECONDS.toHours(difference),
		                TimeUnit.NANOSECONDS.toSeconds(difference) -
		                              TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(difference))));
		
	}
}


