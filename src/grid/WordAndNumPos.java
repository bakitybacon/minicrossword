package grid;

public class WordAndNumPos implements Comparable
{
	public final String word;
	public final int numPos;
	public WordAndNumPos(String word, int numPos)
	{
		this.word = word;
		this.numPos = numPos;
	}
	
	@Override
	public int compareTo(Object other)
	{
		if(!(other instanceof WordAndNumPos))
			throw new IllegalArgumentException("Can't compare");
		return numPos - ((WordAndNumPos)other).numPos; 
	}
}
