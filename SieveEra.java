import java.lang.Math;

public class SieveEra
{
	public static void main(String[] args)
	{
		boolean[] bools = new boolean[100000];
		
		race(bools);
	}
	
	/*
	 * Pits Sieve of Eratosthenes against brute force method of generating a list of primes
	 */
	public static void race(boolean[] array)
	{
		// used to time prime methods
		long start;
		long end;
		long t1;
		long t2;
		
		// time Sieve
		start = System.currentTimeMillis();
		slowToPrime(array);
		end = System.currentTimeMillis();
		t1 = (end - start);
		
		// time brute force
		start = System.currentTimeMillis();
		fastToPrime(array);
		end = System.currentTimeMillis();
		t2 = (end - start);
		
		// display results
		System.out.println("Brute force time: " + t1);
		System.out.println("Sieve of Eratosthenes time: " + t2);
	}
	
	/*
	 * manually checks all numbers, and sets composite ones to false
	 */
	public static void slowToPrime(boolean[] bools)
	{
		// Initialize all elements are true
		setAll(bools, true);
		
		// manually set these
		bools[0] = false;
		bools[1] = false;
		
		for (int i = 2; i < bools.length; i++)
		{
			if (!isPrime(i))
			{
				bools[i] = false;
			}
		}
	}
	/*
	 * Converts all composite indexes in a boolean array to false using sieve of Eratosthenes
	 * The indexes of the boolean array represent the integer
	 * false is composite, true is prime
	 */
	public static void fastToPrime(boolean[] bools)
	{
		// by default all numbers are prime. Innocent until proven guilty
		setAll(bools, true);
		
		// manually set 0 and 1
		bools[0] = false;
		bools[1] = false;
		
		// sqrt(length(bools)) needs to be checked
		for (int i = 2; i < Math.pow(bools.length, .5); i++)
		{
			// if number has not already been proven to be composite
			if (bools[i])
			{
				// set all multiples of number to false/composite
				for (int j = i; j < (bools.length - 1) / i + 1; j++)
				{
					bools[i * j] = false;
				}
			}
		}
	}

	/*
	 * Sets every element in boolean 'array' to the state of 'b'
	 */
	public static void setAll(boolean[] array, boolean b)
	{
		for (int i = 0; i < array.length; i++)
		{
			array[i] = b;
		}
	}
	
	/*
	 * Brute force method of checking if a number is prime
	 * VERY time consuming
	 */
	public static boolean isPrime(int n)
	{
		for (int i = 2; i < n / 2 + 1; i++)
		{
			if (n % i == 0)
			{
				return false;
			}
		}
		
		return true;
	}
}

