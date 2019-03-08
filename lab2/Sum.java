import java.util.Scanner;
import java.util.concurrent.TimeUnit;
public class Sum {
 public static void main( String[] Args){
	 long startTime = System.nanoTime();
	 //sum
	 long n = (long) 1<<32, sum = 0;
	 while(n-->0) {
		 sum+=n;
	 }
	 //print the time
	 System.out.println("simple sum of 0 till 2^32 is "+sum);
	 long difference = System.nanoTime() - startTime;
		System.out.println("Total execution time: " +
		                String.format("%d min, %d sec",
		                TimeUnit.NANOSECONDS.toHours(difference),
		                TimeUnit.NANOSECONDS.toSeconds(difference) -
		                              TimeUnit.MINUTES.toSeconds(TimeUnit.NANOSECONDS.toMinutes(difference))));
		
 }
}