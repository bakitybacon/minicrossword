package parser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ClueParser 
{
	public static void parse() throws Exception
	{
		HashMap<String,ArrayList<String>> cluedictionary = new HashMap<>();
		
		Scanner file = new Scanner(new File("nytclues/clues.txt"));
		
		//int numclues = 0;
		
		while(file.hasNextLine())
		{
			String line = file.nextLine();
			String clue = line.split("\t")[0];
			String answer = line.split("\t")[1];
			if(answer.length() <= 5)
			{
				if(cluedictionary.containsKey(answer))
					cluedictionary.get(answer).add(clue);
				else 
				{
					ArrayList<String> cluelist = new ArrayList<>();
					cluelist.add(clue);
					cluedictionary.put(answer, cluelist);
				}
			}
		}
		file.close();
		
		for(String answer : cluedictionary.keySet())
		{
			System.out.println(answer+":"+cluedictionary.get(answer));
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		parse();
	}
}
