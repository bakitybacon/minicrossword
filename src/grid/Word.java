package grid;

public class Word implements Comparable
{
	public final String word;
	public final int numPos;
	public Word(String word, int numPos)
	{
		this.word = word;
		this.numPos = numPos;
	}
	
	@Override
	public int compareTo(Object other)
	{
		if(!(other instanceof Word))
			throw new IllegalArgumentException("Can't compare");
		return numPos - ((Word)other).numPos; 
	}
}
