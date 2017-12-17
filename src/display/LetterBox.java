package display;

import grid.Letter;

public class LetterBox 
{
	Letter letter;
	int clueNum = -1;
	boolean isGray = false;
	public LetterBox(Letter letter)
	{
		this.letter = letter;
	}
	public LetterBox(Letter letter, boolean isGray)
	{
		this.letter = letter;
		this.isGray = isGray;
	}
	public LetterBox(Letter letter, int clueNum)
	{
		this.letter = letter;
		this.clueNum = clueNum;
	}
	public LetterBox(Letter letter, int clueNum, boolean isGray)
	{
		this.letter = letter;
		this.clueNum = clueNum;
		this.isGray = isGray;
	}
	public LetterBox()
	{
		this.letter = Letter.BLANK;
	}
	public void setClueNum(int clueNum)
	{
		this.clueNum = clueNum;
	}
	
	public String toString()
	{
		return getClass().getName() + "[letter="+letter+", clueNum="+clueNum+"]";
	}
	public void setLetter(Letter letter)
	{
		this.letter = letter;
	}
	public Letter getLetter()
	{
		return letter;
	}
	public int getClueNum()
	{
		return clueNum;
	}
	public void setGray(boolean isGray)
	{
		this.isGray = isGray;
	}
	public boolean isGray()
	{
		return isGray;
	}
}
