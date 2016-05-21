import java.util.Random;

public class BinarySearch
{
	public static void main(String[] args)
	{
		int[] myArr = new int[100000000];
		
		fillRand(myArr);
		
		race(myArr);
	}
	
	/*
	 * Pits getIndexSlow vs getIndexFast to see which can locate both a random
	 * element in the array, and a value not in the array in shorter time
	 */
	public static void race(int[] array)
	{
		// will be used to keep track of time
		long start;
		long end;
		long t1;
		long t2;
		
		// first element to be searched for is in 'array'
		Random myRand = new Random();
		int randIndex = myRand.nextInt(array.length);
		int firstElement = array[randIndex];
		
		// second element is NOT in 'array'
		int secondElement = array[array.length - 1] + 1;
		
		// time linear search
		start = System.currentTimeMillis();
		getIndexSlow(firstElement, array);
		end = System.currentTimeMillis();
		t1 = (end - start);
		
		// time binary search
		start = System.currentTimeMillis();
		getIndexFast(firstElement, array, 0, array.length - 1);
		end = System.currentTimeMillis();
		t2 = (end - start);
		
		// display results
		System.out.println("Round 1");
		System.out.println("Element is in array");
		System.out.println("Linear Search time: " + t1 + " milliseconds");
		System.out.println("Binary Search time: " + t2 + " milliseconds");
		System.out.println();
		
		// time linear search
		start = System.currentTimeMillis();
		getIndexSlow(secondElement, array);
		end = System.currentTimeMillis();
		t1 = (end - start);
				
		// time binary search
		start = System.currentTimeMillis();
		getIndexFast(secondElement, array, 0, array.length - 1);
		end = System.currentTimeMillis();
		t2 = (end - start);
		
		// display results
		System.out.println("Round 2");
		System.out.println("Element is NOT in array");
		System.out.println("Linear search time: " + t1 + " milliseconds");
		System.out.println("Binary search time: " + t2 + " milliseconds");
		System.out.println();
		
	}
	
	/*
	 * Fills 'array' with random integers between 0 and 19 inclusive in ascending order
	 */
	public static void fillRand(int[] array)
	{
		// random number generator
		Random r = new Random();

		array[0] = r.nextInt(20);
		
		for (int i = 1; i < array.length; i++)
		{
			array[i] = array[i - 1] + r.nextInt(20);
		}
	}
	
	/*
	 * Determines the index of 'key' in 'array' by checking every element
	 */
	public static int getIndexSlow(int key, int[] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			// 'key' was found
			if (array[i] == key)
			{
				return i;
			}
		}
		
		// 'key' was not found
		return -1;
	}
	
	/*
	 * Determines the index of 'key' by recursively
	 *  searching 'array' between the bounds 'low' and 'high'
	 */
	public static int getIndexFast(int key, int[] array, int low, int high)
	{
		// lower midpoint between 'low' and 'high'
		int midIndex = (low + high) / 2;
		
		// 'key' was not found
		if (low > high)
		{
			return -1;
		}
		
		// if midpoint is 'key'
		else if (array[midIndex] == key)
		{
			return midIndex;
		}
		
		// 'key' cannot lie in upper half
		else if (array[midIndex] > key)
		{
			return getIndexFast(key, array, low, midIndex - 1);
		}
		
		// 'key' cannot lie in lower half
		else if (array[midIndex] < key)
		{
			return getIndexFast(key, array, midIndex + 1, high);
		}
		
		// shouldn't happen
		else
		{
			return -2;
		}
	}
}
