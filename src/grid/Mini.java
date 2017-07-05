package grid;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Mini 
{
	private final Letter[][] grid = new Letter[5][5];
	
	private final String[] threeLetters = new String[500];
	private final String[] fourLetters = new String[500];
	private final String[] fiveLetters = new String[500];
	
	public Mini()
	{
		loadClues();
		for(int i = 0; i < grid.length; i++)
			for(int j = 0; j < grid.length;j++)
			{
				grid[i][j] = Letter.BLANK;
			}
		
	//	grid[2][3] = Letter.C;
	//	fillRow(0, fromString("catch"));
		
		solve();
	}
	
	private void solve()
	{
		String[] rows = new String[5];
		String[] cols = new String[5];
		for(int i = 0; i < 5; i++)
		{
			rows[i] = "";
			cols[i] = "";
		}
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5; j++)
				rows[i] += grid[i][j];
		}
		for(int j = 0; j < 5; j++)
		{
			for(int i = 0; i < 5; i++)
				cols[j] += grid[i][j];
		}
		
		for(String row : rows)
		{
			System.out.println(row);
		}
		for(String col : cols)
		{
			System.out.println(col);
		}
		
		//calculate all possibilities for each row/col
		// run through every single possibility, check to see which decreases the total number of possibilities the least
		// choose that one, continue with the next row, etc. 
		
		//ArrayList<String>[][] possA = generatePossibilities();
		//ArrayList<String>[] rowPossibilities = possA[0];
		//ArrayList<String>[] colPossibilities = possA[1];
		
		int depth = 0;
		Letter[][][] previousStates = new Letter[5][5][10];
		
		
		HashMap<String,Integer> nonpossibilities = new HashMap<>();
		
		String[] previousChoices = new String[10];
		
		while(!solved())
		{			
			
			System.out.println(this);
			
			
			ArrayList<String>[][] possA = generatePossibilities();
			ArrayList<String>[] rowPossibilities = possA[0];
			ArrayList<String>[] colPossibilities = possA[1];
			
			
			//calculate the minimum number of possibilities for any row/col
			boolean isRow = true;
			int index = -1;
			int min = 501;
			
			for(int i = 0; i < 5; i++)
			{
				if(rowPossibilities[i].size() < min)
				{
					if(isRowFilled(i))
						continue;
					isRow = true;
					index = i;
					min = rowPossibilities[i].size();
				}
				if(colPossibilities[i].size() < min)
				{
					if(isColFilled(i))
						continue;
					isRow = false;
					index = i;
					min = colPossibilities[i].size();
				}
			}
			
			for(int i = 0; i < 5; i++)
				for(int j = 0; j < 5; j++)
					previousStates[i][j][depth] = grid[i][j];
			
			int max = 0;
			int index2 = -1;
			
			if(isRow)
			{
				for(int i = 0; i < rowPossibilities[index].size(); i++)
				{
					fillRow(index, fromString(rowPossibilities[index].get(i)));
					int pos = possibilitiesCount();
					if(pos > max)
					{
						String key = rowPossibilities[index].get(i);
						if(!(nonpossibilities.containsKey(key) && nonpossibilities.get(key) == depth))
						{
							index2 = i;
							max = pos;
						}
						else
						{
							System.err.println("hng?");
						}
					}
					
					//we need to be able to decrement the depth if no possibilities are solvable...
					
					for(int j = 0; j < 5; j++)
						for(int k = 0; k < 5; k++)
							grid[j][k] = previousStates[j][k][depth];

				}
				
				System.out.println("indices: "+index+","+index2);
				
				if(index2 == -1)//no possibilities available given the choice at the depth above this one, so kick it back another level
				{
					depth--;
					nonpossibilities.put(previousChoices[depth],depth);
					for(int j = 0; j < 5; j++)
						for(int k = 0; k < 5; k++)
							grid[j][k] = previousStates[j][k][depth];
					
					/*ArrayList nonKeys = new ArrayList(Arrays.asList(nonpossibilities.keySet().toArray()));
					for(int i = 0; i < nonKeys.size(); i++)
					{
						String str = (String)nonKeys.get(i);
						if(nonpossibilities.get(str).equals(depth+1))
							nonpossibilities.remove(str,depth+1);
					}*/
					
					continue;
				}
				fillRow(index, fromString(rowPossibilities[index].get(index2)));
				previousChoices[depth] = rowPossibilities[index].get(index2);
				
				if(!isSolvable())
				{
					System.out.println("q_q");
					
					
					for(int j = 0; j < 5; j++)
						for(int k = 0; k < 5; k++)
							grid[j][k] = previousStates[j][k][depth];
					
					
					nonpossibilities.put(rowPossibilities[index].get(index2),depth);
					depth--;
					System.out.println(nonpossibilities);
				}
			}
			else
			{
				for(int i = 0; i < colPossibilities[index].size(); i++)
				{
					fillCol(index, fromString(colPossibilities[index].get(i)));
					int pos = possibilitiesCount();
					if(pos > max)
					{
						String key = colPossibilities[index].get(i);
						if(!(nonpossibilities.containsKey(key) && nonpossibilities.get(key) == depth))
						{
							index2 = i;
							max = pos;
						}
						else
						{
							System.err.println("hng?");
						}
					}
					
					for(int j = 0; j < 5; j++)
						for(int k = 0; k < 5; k++)
							grid[j][k] = previousStates[j][k][depth];
				}
				System.out.println("indices: "+index+","+index2);
				
				if(index2 == -1)//no possibilities available given the choice at the depth above this one, so kick it back another level
				{
					depth--;
					nonpossibilities.put(previousChoices[depth],depth);
					for(int j = 0; j < 5; j++)
						for(int k = 0; k < 5; k++)
							grid[j][k] = previousStates[j][k][depth];
					
					/*
					ArrayList nonKeys = new ArrayList(Arrays.asList(nonpossibilities.keySet().toArray()));
					for(int i = 0; i < nonKeys.size(); i++)
					{
						String str = (String)nonKeys.get(i);
						if(nonpossibilities.get(str).equals(depth+1))
							nonpossibilities.remove(str,depth+1);
					}*/
					
					continue;
				}
				
				
				fillCol(index, fromString(colPossibilities[index].get(index2)));
				previousChoices[depth] = colPossibilities[index].get(index2);
				
				if(!isSolvable())
				{
					System.out.println("q_q");
					
					
					for(int j = 0; j < 5; j++)
						for(int k = 0; k < 5; k++)
							grid[j][k] = previousStates[j][k][depth];
					
					depth--;
					nonpossibilities.put(colPossibilities[index].get(index2),depth);
					
					
					System.out.println(nonpossibilities);
				}
			}
			depth++;
			try
			{Thread.sleep(1000);}
			catch(Exception e){}
		}
	}
	
	private int possibilitiesCount()
	{
		ArrayList<String>[][] possA = generatePossibilities();
		ArrayList<String>[] rowPossibilities = possA[0];
		ArrayList<String>[] colPossibilities = possA[1];
		//calculate total number of possibilities so we know what choice decreases this value by the least amount
		int wordNum = 0;
		
		for(int i = 0; i < 5; i++)
		{
			wordNum += rowPossibilities[i].size();
			wordNum += colPossibilities[i].size();
		}
		
		return wordNum;
	}
	
	private boolean isSolvable()
	{
		ArrayList<String>[][] possA = generatePossibilities();
		ArrayList<String>[] rowPossibilities = possA[0];
		ArrayList<String>[] colPossibilities = possA[1];
		
		for(int i = 0; i < 5; i++)
		{
			if(rowPossibilities[i].size() == 0)
				return false;
			if(colPossibilities[i].size() == 0)
				return false;
		}
		
		return true;
	}
	
	private boolean solved()
	{
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				if(grid[i][j] == Letter.BLANK)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	private void fillRow(int rowNum, Letter[] letters)
	{
		for(int i = 0; i < 5; i++)
			grid[rowNum][i] = letters[i];
	}
	
	private void fillCol(int colNum, Letter[] letters)
	{
		for(int i = 0; i < 5; i++)
			grid[i][colNum] = letters[i];
	}
	
	private boolean isRowFilled(int rowNum)
	{
		for(int i = 0; i < 5; i++)
			if(grid[rowNum][i] == Letter.BLANK)
				return false;
		return true;
	}
	private boolean isColFilled(int colNum)
	{
		for(int i = 0; i < 5; i++)
			if(grid[i][colNum] == Letter.BLANK)
				return false;
		return true;
	}
	
	private Letter[] fromString(String string)
	{
		string = string.toUpperCase();
		Letter[] letters = new Letter[string.length()];
		OUTER: for(int i = 0; i < string.length(); i++)
		{
			for(Letter l : Letter.values())
				if(string.charAt(i) == l.getChar())
				{
					letters[i] = l;
					continue OUTER;
				}
		}
		return letters;
	}
	
	private ArrayList<String>[][] generatePossibilities()
	{
		ArrayList<String>[] rowPossibilities = new ArrayList[5];
		ArrayList<String>[] colPossibilities = new ArrayList[5];

		String[] rows = new String[5];
		String[] cols = new String[5];
		
		for(int i = 0; i < 5; i++)
		{
			rows[i] = "";
			cols[i] = "";
		}
		
		for(int i = 0; i < 5; i++)
			for(int j = 0; j < 5; j++)
				rows[i] += grid[i][j];
		
		for(int j = 0; j < 5; j++)
			for(int i = 0; i < 5; i++)
				cols[j] += grid[i][j];
		
		for(int i = 0; i < 5; i++)
		{
			rowPossibilities[i] = matchesPattern(rows[i]);
			colPossibilities[i] = matchesPattern(cols[i]);
		}
		
		/*for(ArrayList<String> poss : rowPossibilities)
			System.out.println(poss);
		for(ArrayList<String> poss : colPossibilities)
			System.out.println(poss);*/
		
		return new ArrayList[][] {rowPossibilities, colPossibilities};
	}
	
	private void loadClues()
	{
		try 
		{
			Scanner sc3 = new Scanner(new File("words/3letters"));
			Scanner sc4 = new Scanner(new File("words/4letters"));
			Scanner sc5 = new Scanner(new File("words/5letters"));
			
			for(int i = 0; i < 500; i++)
			{
				threeLetters[i] = sc3.nextLine();
				fourLetters[i] = sc4.nextLine();
				fiveLetters[i] = sc5.nextLine();
			}
			
			sc3.close();
			sc4.close();
			sc5.close();
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			System.err.println("HOLY SHIT NO!");
		}
	}
	
	private ArrayList<String> matchesPattern(String input)
	{
		input = input.toLowerCase();
		
		if(input.length() == 3)
		{
			ArrayList<String> matches = new ArrayList<String>(Arrays.asList(threeLetters));
			for(int i = 0; i < input.length(); i++)
			{
				char c = input.charAt(i);
				if(c == '*')
					continue;
				for(int j = 0; j < matches.size(); j++)
				{
					String test = matches.get(j);
					if(test.charAt(i) != c)
					{
						matches.remove(test);
						j--;
					}
				}
			}
			return matches;
		}
		if(input.length() == 4)
		{
			ArrayList<String> matches = new ArrayList<String>(Arrays.asList(fourLetters));
			for(int i = 0; i < input.length(); i++)
			{
				char c = input.charAt(i);
				if(c == '*')
					continue;
				for(int j = 0; j < matches.size(); j++)
				{
					String test = matches.get(j);
					if(test.charAt(i) != c)
					{
						matches.remove(test);
						j--;
					}
				}
			}
			return matches;
		}
		else
		{
			ArrayList<String> matches = new ArrayList<String>(Arrays.asList(fiveLetters));
			for(int i = 0; i < input.length(); i++)
			{
				char c = input.charAt(i);
				if(c == '*')
					continue;
				for(int j = 0; j < matches.size(); j++)
				{
					String test = matches.get(j);
					if(test.charAt(i) != c)
					{
						matches.remove(test);
						j--;
					}
				}
			}
			return matches;
		}
	}
	
	public String toString()
	{
		String result = "";
		for(int row = 0; row < 5; row++)
		{
			result += "-----------\n";
			for(int col = 0; col < 5; col++)
			{
				result += "|"+grid[row][col];
			}
			result += "|\n";
		}
		result += "-----------\n";
		return result;
	}
	
	public static void main(String[] args)
	{
		System.out.println(new Mini());
	}
}
