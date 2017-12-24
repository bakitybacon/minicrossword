package grid;

import java.util.Arrays;

public enum Letter 
{
	A('A'),B('B'),C('C'),D('D'),E('E'),
	F('F'),G('G'),H('H'),I('I'),J('J'),
	K('K'),L('L'),M('M'),N('N'),O('O'),
	P('P'),Q('Q'),R('R'),S('S'),T('T'),
	U('U'),V('V'),W('W'),X('X'),Y('Y'),
	Z('Z'),BLACK('$'),BLANK('*');
	
	public final char display;
	private Letter(char display)
	{
		this.display = display;
	}
	public String toString()
	{
		return display + "";
	}
	public char getChar()
	{
		return display;
	}
	
	public static Letter[] fromString(String string)
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
	
	public static Letter fromChar(char c)
	{
		c = Character.toUpperCase(c);
		for(Letter l : Letter.values())
			if(c == l.getChar())
			{
				return l;
			}
		return null;
	}
	
	public static void main(String ... args)
	{
		System.out.println(Arrays.toString(Letter.fromString("$gash")));
	}
	
	public static boolean letterExists(char c)
	{
		if(c >= 'a' && c <= 'z')
			return true;
		if(c >= 'A' && c <= 'Z')
			return true;
		return false;
	}
}
