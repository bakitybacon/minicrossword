/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minicrossword;

import grid.Letter;

/**
 *
 * @author ciarwen
 */
public class Word 
{
    // This class stores information along with a String so that a list of Words
    // can be sorted according to how flexible they are in the puzzle
    public Word(String s)
    {
        word = Letter.fromString(s);
        numposs = 0;
    }
    public Word(Letter[] letters)
    {
        word = letters;
        numposs = 0;
    }
    public void setNumPoss(int np)
    {
        numposs = np;
    }
    public int getNumPoss()
    {
        return numposs;
    }
    public String toString()
    {
        String s = "";
        for(int i = 0; i < word.length; i++)
        {
            s += word[i];
        }
        return s;
    }
    private Letter[] word;
    private int numposs;
}