package psyche.androidapp.workcalcs;

import java.text.DecimalFormat;
import java.util.Random;

public class Utilities 
{
	
	
	// prints out the strings in a string array without a space in between the individual strings (to console)
	public static void printStringArray(String[] s)
	{
		for(int i = 0; i < s.length; i++)
		{
			if(s[i] != null)
				System.out.print(s[i]);
		}
		System.out.println();
	}
	
	//counts how many characters in a string that are unique, i.e no duplicates
	public static int countUniqueChars(String s)
	{
		int uniqueChars = 0;
		boolean[] present = new boolean[Character.MAX_VALUE];
		
		for(int i = 0; i < s.length(); i++)
		{
			present[s.charAt(i)] = true;
		}
		
		for(int j = 0; j < present.length; j++)
		{
			if(present[j])
				uniqueChars++;
		}
		
		return uniqueChars;
	}
	
	//counts how many elements in a string array are not null
	public static int nElementsNotNull(String[] s)
	{
		int counter = 0;
		
		for(int i = 0; i < s.length; i++)
		{
			if(s[i] != null)
				counter++;
		}
		
		return counter;
	}
	
	//splits a string into a char array
	public static char[] stringSplitToChars(String word)
	{
		char[] letters = word.toCharArray();
		return letters;
	}
	
	//selects a random word from an array of words
	public static String randomWordSelect(String[] words)
	{
		Random rand = new Random();
		int index = rand.nextInt(words.length);
		String word = words[index];		
		return word;
	}
	
	public static String toString2dp(float res)
	{
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format((double)res);
	}
	
	public static String toString2dp(int res)
	{
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format((double)res);
	}
	
	public static float sumArray(float[] f)
	{
		float sum = 0;
		for(float i: f)
			sum += i;
		return sum;
	}
	
	public static int sumArray(int[] f)
	{
		int sum = 0;
		for(int i: f)
			sum += i;
		return sum;
	}
	
	public static int toInt(float f)
	{
		return (int) f;
	}
	
	public static String stringParse(String s, int d)
	{
		String newString = "";
		newString = String.format("%s%s", s, d);
		return newString; 
	}
	
	// converts string array to string
	public static String stringArrayToString(String[] sArray)
	{
		String s = "";
		
		for(int i = 0; i < sArray.length; i++)
		{
			if(sArray[i] != null)
				s += sArray[i];
		}
		return s;
	}

	//returns the index of a given member in a given array
		public static <T> int indexOf(T member, T[] array)
		{
			for(int i = 0; i < array.length; i++)
			{
				if(array[i] != null && array[i].equals(member) 
						|| member == null && array[i] == null)
					return i;
			}
			return -1;
		}
}
