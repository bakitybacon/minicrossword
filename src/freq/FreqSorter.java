package freq;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FreqSorter 
{
	public static void main(String[] args) throws Exception
	{
		Scanner CrosswordClues = new Scanner(new File("src/wordlists/43word.game"));
		HashMap<String,Integer> wordal = new HashMap<>();
		while(CrosswordClues.hasNext())
		{
		    wordal.put(CrosswordClues.next(),0);
		}
		CrosswordClues.close();
		
		Scanner freqs = new Scanner(new File("wordfrequency.txt"));
		
		while(freqs.hasNextLine())
		{
		    String[] data = freqs.nextLine().split(" ");
		    if(wordal.containsKey(data[0]))
		    {
		    	wordal.put(data[0], Integer.parseInt(data[1]));
		    }
		}
		freqs.close();

		ArrayList<Record> records = new ArrayList<>();
		for(Map.Entry<String, Integer> entry : wordal.entrySet())
		{
			records.add(new Record(entry.getKey(),entry.getValue()));
		}
		Collections.sort(records);
		
		
		ArrayList<String> threes = new ArrayList<>();
		ArrayList<String> fours = new ArrayList<>();
		ArrayList<String> fives = new ArrayList<>();
		
		for(Record r : records)
		{
			String word = r.getString();
			if(word.length() == 3)
				threes.add(word);
			else if(word.length() == 4)
				fours.add(word);
			else if(word.length() == 5)
				fives.add(word);
		}
		
		PrintWriter out = new PrintWriter(new FileOutputStream(new File("3sorted.game")));
		for(String s : threes)
			out.println(s);
		out.close();
		out = new PrintWriter(new FileOutputStream(new File("4sorted.game")));
		for(String s : fours)
			out.println(s);
		out.close();
		out = new PrintWriter(new FileOutputStream(new File("5sorted.game")));
		for(String s : fives)
			out.println(s);
		out.close();
	}
}
class Record implements Comparable
{
	private final String word;
	private int freq;
	public Record(String word, int freq)
	{
		this.word = word;
		this.freq = freq;
	}
	
	public String getString()
	{
		return word;
	}
	public String toString()
	{
		return getClass().getName()+"[word="+word+", frequency="+freq+"]";
	}
	public int getFreq()
	{
		return freq;
	}
	
	public void setFreq(int newFreq)
	{
		freq = newFreq;
	}

	@Override
	public int compareTo(Object o) 
	{
		if(!(o instanceof Record))
			throw new IllegalArgumentException();
		Record other = (Record)o;
		return other.getFreq()-freq;
	}
}