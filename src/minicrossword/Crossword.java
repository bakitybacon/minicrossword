/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minicrossword;

import java.util.ArrayList;
import java.util.Arrays;

import grid.Letter;

/**
 *
 * @author ciarwen
 */
public class Crossword 
{
    public Crossword(int numRows, int numCols)
    {
        rw = numRows;
        cl = numCols;
        puzzle = new Letter[rw][cl];
        for(int i = 0; i < rw; i++)
        	for(int j = 0; j < cl; j++)
        		puzzle[i][j] = Letter.BLANK;
        //I initialize my list of words here, really should be read from a file,
        //as this is somewhat inflexible
        wordlist = new String[34];
        wordlist[0] = "grace";
        wordlist[1] = "helix";
        wordlist[2] = "abide";
        wordlist[3] = "never";
        wordlist[4] = "alert";
        wordlist[5] = "ghana";
        wordlist[6] = "rebel";
        wordlist[7] = "alive";
        wordlist[8] = "cider";
        wordlist[9] = "exert";
        wordlist[10] = "cabin";
        wordlist[10] = "alibi";
        wordlist[11] = "linen";
        wordlist[12] = "vegan";
        wordlist[13] = "enemy";
        wordlist[14] = "calve";
        wordlist[15] = "alien";
        wordlist[16] = "binge";
        wordlist[17] = "ibeam";
        wordlist[18] = "ninny";
        wordlist[19] = "chain";
        wordlist[20] = "honda";
        wordlist[21] = "owner";
        wordlist[22] = "isaac";
        wordlist[23] = "rolls";
        wordlist[24] = "choir";
        wordlist[25] = "howso";
        wordlist[26] = "annal";
        wordlist[27] = "ideal";
        wordlist[28] = "spain";
        wordlist[29] = "sssss";
        wordlist[30] = "ppppp";
        wordlist[31] = "aaaaa";
        wordlist[32] = "iiiii";
        wordlist[33] = "nnnnn";
        moveList = new ArrayList<Move>();
    }
    public Crossword(int numRows, int numCols, String[] wordy)
    {
        rw = numRows;
        cl = numCols;
        puzzle = new Letter[rw][cl];
        for(int i = 0; i < rw; i++)
        	for(int j = 0; j < cl; j++)
        		puzzle[i][j] = Letter.BLANK;
        wordlist = wordy;
        moveList = new ArrayList<Move>();
    }
    public Crossword(Letter[][] letters, String[] wordy)
    {
        puzzle = letters;
        rw = letters[0].length;
        cl = letters.length;
        wordlist = wordy;
        moveList = new ArrayList<Move>();
    }
    public void setLetter(int x, int y, Letter letter)
    {
        puzzle[x][y] = letter;
    }
    public Letter getLetter(int x, int y)
    {
        return puzzle[x][y];
    }
    public Letter[][] getGrid()
    {
    	return puzzle;
    }
    //Enters a word into the grid, stores it as a past move
    public void setWordRow(int row, String w)
    {
    	Letter[] word = Letter.fromString(w);
        int lastIndex = row;
        int i = 0;
        Letter[] last = new Letter[cl];
        while(i < cl)
        {
            last[i] = puzzle[row][i];
            setLetter(row, i, word[i]);
            i++;
        }
        Letter[] lastForm = last;
        boolean isLastRow = true;
        Move lastMove = new Move(lastForm, lastIndex, isLastRow);
        moveList.add(0, lastMove);

    }
    public void setWordCol(int col, String w)
    {
    	Letter[] word = Letter.fromString(w);
        int lastIndex = col;
        int j = 0;
        Letter[] last = new Letter[rw];
        while(j < rw)
        {
            last[j] = puzzle[j][col];
            setLetter(j, col, word[j]);
            j++;
        }
        Letter[] lastForm = last;
        boolean isLastRow = false;
        Move lastMove = new Move(lastForm, lastIndex, isLastRow);
        moveList.add(0, lastMove);
    }
    //This function should only, only be used when you are adding a word to the
    //grid that is not in the word list already.  Otherwise, it is performing
    //a lot of costly checking and updating that is wholly unimportant.  It is 
    //a lot simpler to make sure the words you are using are in the list to begin
    //with, but for the sake of making sure that the user doesn't have to think
    //about this aspect, I am including this method here.
    public void setWord(int index, String word, boolean isRow)
    {
        if(!containsWord(word))
        {
            String[] newWords = new String[wordlist.length + 1];
            for(int i = 0; i < wordlist.length; i++)
            {
                newWords[i] = wordlist[i];
            }
            newWords[wordlist.length] = word;
            wordlist = newWords;
        }
        if(isRow)
        {
            setWordRow(index, word);
        }
        else
        {
            setWordCol(index, word);
        }
    }
    // Checks the total number of words that can be fit in the grid at any index
    public int sumPoss()
    {
        int sum = 0;
        for(int i = 0; i< cl; i++)
        {
            sum += getPossRow(i).length;
        }
        for(int j = 0; j< cl; j++)
        {
            sum += getPossCol(j).length;
        }
        return sum;
    }
    // Undoes the last move in the crossword's history
    public void undoLast()
    {
        Move lastMove = moveList.get(0);
        Letter[] lastForm = lastMove.getLastMove();
        int lastIndex = lastMove.getLastIndex();
        boolean isLastRow = lastMove.getIsRow();
        if(isLastRow)
        {
            for(int i = 0; i < cl; i++)
            {
                setLetter(lastIndex, i, lastForm[i]);
            }
        }
        else
        {
            for(int j = 0; j < rw; j++)
            {
                setLetter(j, lastIndex, lastForm[j]);
            }
        }
        moveList.remove(0);
    }
    //Checks if all squares are filled
    public boolean isFull()
    {
        for(int i = 0; i < rw; i++)
            for(int j = 0; j < cl; j++)
                if(puzzle[i][j] == Letter.BLANK)
                    return false;
        return true;
    }
    public boolean isLegalWord(int index, boolean isRow)
    {
        if(isRow)
        {
            String wordAtIndex = "";
            for(int i = 0; i < rw; i++)
            {
                wordAtIndex += puzzle[i][index].getChar();
            }
            for(String s : wordlist)
            {
                if(wordAtIndex.equalsIgnoreCase(s))
                {
                    return true;
                }
            }
            return false;
        }
        else
        {
            String wordAtIndex = "";
            for(int j = 0; j < cl; j++)
            {
                wordAtIndex += puzzle[index][j].getChar();
            }
            for(String s: wordlist)
            {
                if(wordAtIndex.equalsIgnoreCase(s))
                {
                    return true;
                }
            }
            return false;
        }
    }
    public boolean isFinished()
    {
        for(int i = 0; i < rw; i++)
        {
            if(!isLegalWord(i, true))
            {
                return false;
            }
        }
        for(int j = 0; j < cl; j++)
        {
            if(!isLegalWord(j, false))
            {
                return false;
            }
        }
        return true;
    }
    // Checks if any of the indices have no possible answers that can work,
    // meaning that the puzzle is unsolvable at that point
    public boolean canBeFinished()
    {
        for(int i = 0; i < rw; i++)
        {
            String[] poss = getPossRow(i);
            if(poss.length == 0)
            {
                return false;
            }
        }
        for(int j = 0; j < cl; j++)
        {
            String[] poss = getPossCol(j);
            if(poss.length == 0)
            {
                return false;
            }
        }
        return true;
    }
    public boolean containsWord(String s)
    {
        for(int index = 0; index < cl; index++)
        {
            boolean charsMatch = true;
            for(int j = 0; j < cl; j++)
            {
                try
                {
	                if(puzzle[index][j] != Letter.fromChar(s.charAt(j)))
	                {
	                    charsMatch = false;
	                }
                }
                catch(StringIndexOutOfBoundsException e)
                {
                    System.out.println("Index equals" + index);
                    System.out.println("j equals " + j);
                    System.out.println("String equals " + s);
                }
            }
            if(charsMatch)
            {
                return true;
            }
        }
        for(int index = 0; index < rw; index++)
        {
            boolean charsMatch = true;
            for(int j = 0; j < rw; j++)
            {
                if(puzzle[j][index] != Letter.fromChar(s.charAt(j)))
                {
                    charsMatch = false;
                }
            }
            if(charsMatch)
            {
                return true;
            }
        }
        return false;
    }
    // These functions return a list of possible words at a given index
    public String[] getPossRow(int index)
    {
        Letter[] form = new Letter[cl];
        for(int j = 0; j < cl; j++)
        {
            form[j] = puzzle[index][j];
        }
        // This might be a stupid way to deal with words that have already been
        // filled, but it was the solution I came up with for not getting caught
        // checking all the old words over and over.  Each word needs a potential
        // value based on how many possibilities it has.  Since I am trying to 
        // minimize the number of possibilities, I pick high field and return all
        // available possibilities if a word has already been filled in.  This
        // ensures that these words will never be picked as potential avenues
        // of exploration
        if(getSpaces(index,true) == 0)
        {
            return wordlist;
        }
        ArrayList<String> possi = new ArrayList<String>();
        for(String s: wordlist)
        {
            boolean fits = true;
            for(int j = 0; j < cl; j++)
            {
                if(form[j] != Letter.BLANK)
                {
                    if(form[j] != Letter.fromChar(s.charAt(j)))
                    {
                        fits = false;
                    }
                }
            }
            if(fits && !containsWord(s))
                possi.add(s);
        }
        String[] poss = new String[possi.size()];
        for(int i = 0; i < possi.size(); i++)
        {
            poss[i] = possi.get(i);
        }
        return poss;
    }
    public String[] getPossCol(int index)
    {
        Letter[] form = new Letter[rw];
        for(int i = 0; i < rw; i++)
        {
            form[i] = puzzle[i][index];
        }
        
        if(getSpaces(index,false) == 0)
        {
            return wordlist;
        }
        ArrayList<String> possi = new ArrayList<String>();
        for(String s : wordlist)
        {
            boolean fits = true;
            for(int i = 0; i < rw; i++)
            {
                if(form[i] != Letter.BLANK)
                {
                    if(form[i] != Letter.fromChar(s.charAt(i)))
                    {
                        fits = false;
                    }
                }
            }
            if(fits && !containsWord(s))
                possi.add(s);
        }
        String[] poss = new String[possi.size()];
        for(int i = 0; i < possi.size(); i++)
        {
            poss[i] = possi.get(i);
        }
        return poss;
    }
    // These functions select an index to explore, trying to minimize the
    // number of possible answers to check by getting the index with the least
    // number of possible answers.
    public int getSpaces(int index, boolean isRow)
    {
        int numSpaces = 0;
        if(isRow)
        {
            for(int i = 0; i < cl; i++)
            {
                if(getLetter(index, i) == Letter.BLANK)
                {
                    numSpaces += 1;
                }
            }
            //System.out.println("row "+index+":" + numSpaces + " spaces");
            return numSpaces;
        }
        else
        {
            for(int j = 0; j < rw; j++)
            {
                if(getLetter(j, index) == Letter.BLANK)
                {
                    numSpaces += 1;
                }
            }
            //System.out.println("column "+index+":" + numSpaces + " spaces");
            return numSpaces;
        }
    }
    public int selectIndexRow()
    {
        int min = wordlist.length;
        int index = 0;
        int numSpaces = 0;
        for(int i = 0; i < cl; i++)
        {
            int spaces = getSpaces(i, true);
            String[] poss = getPossRow(i);
            if(poss.length < min)
            {
                min = poss.length;
                index = i;
                numSpaces = spaces;
            }
            else if(poss.length == min && spaces > numSpaces)
            {
                min = poss.length;
                index = i;
                numSpaces = spaces;
                System.out.println(min + " " + index + " " + numSpaces);
            }
        }
        return index;
    }
    public int selectIndexCol()
    {
        int min = wordlist.length;
        int index = 0;
        int numSpaces = 0;
        for(int j = 0; j < rw; j++)
        {
            int spaces = getSpaces(j, false);
            String[] poss = getPossCol(j);
            if(poss.length < min)
            {
                min = poss.length;
                index = j;
                numSpaces = spaces;
            }
            else if(poss.length == min && spaces > numSpaces)
            {
                min = poss.length;
                index = j;
                numSpaces = spaces;
            }
        }
        return index;
    }
    // Checks whether the index that is being explored is a row or a column
    public boolean IndexRow()
    {
        int colmin = wordlist.length;
        int colindex = 0;
        int colSpaces = 0;
        for(int j = 0; j < rw; j++)
        {
            String[] poss = getPossCol(j);
            //System.out.println("poss array "+Arrays.toString(poss));
            int spaces = getSpaces(j, false);
            //System.out.println("we have "+spaces+" col spaces");
            if(poss.length < colmin)
            {
                colmin = poss.length;
                colindex = j;
                colSpaces = spaces;
            }
            else if(poss.length == colmin && spaces > colSpaces)
            {
                colmin = poss.length;
                colindex = j;
                colSpaces = spaces;
            }
        }
        int rowmin = wordlist.length;
        int rowindex = 0;
        int rowSpaces = 0;
        for(int i = 0; i < cl; i++)
        {
            int spaces = getSpaces(i, true);
            //System.out.println("we have "+spaces+" row spaces");
            String[] poss = getPossRow(i);
            //System.out.println("poss array "+Arrays.toString(poss));
            if(poss.length < rowmin)
            {
                rowmin = poss.length;
                rowindex = i;
                rowSpaces = spaces;
            }
            else if(poss.length == rowmin && spaces > rowSpaces)
            {
                rowmin = poss.length;
                rowindex = i;
                rowSpaces = spaces;
            }
        }
        if(rowmin < colmin)
            return true;
        else if(rowmin == colmin && rowSpaces > colSpaces)
            return true;
        else return false;
    }
    public String toString()
    {
        String s = "";
        for(int i = 0; i < rw; i++)
        {
            for(int j = 0; j < cl; j++)
            {
                s += puzzle[i][j];
            }
            s += "\n";
        }
        return s;
    }
    private int rw;
    private int cl;
    private Letter[][] puzzle;
    private String[] wordlist;
    private ArrayList<Move> moveList;
}
