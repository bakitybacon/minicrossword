/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minicrossword;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author ciarwen
 */
public class MiniCrossword 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
    	String[] wordy = null;
        try
        {
            Scanner CrosswordClues = new Scanner(new File("5word.game"));
            int numWords = CrosswordClues.nextInt();
            wordy = new String[numWords];
            for(int i = 0; i < numWords; i++)
            {
                wordy[i] = CrosswordClues.next();
            }
            CrosswordClues.close();
            System.out.println("Success");
        }
        catch(FileNotFoundException e)
        {
        	System.err.println("Failure");
        }
        //All of the words list used are taken from poslarchive.com.
        //2 letter words have a list of 100 words
        //3 letter words are around 1000,
        //4 letter words over 4000
        //and 5 letter words nearly 9000;
        //Yes, yes, but it's not over 9000, so shut up.
        Crossword mine = new Crossword(5, 5, wordy);
        // don't do this again, pichu took 18 min 19 sec to fully check
        // with print statements, 13 min 38 sec without.  No solution found
        // This is the cost of checking the entire tree of a 5x5 if there is
        // no viable solution.  Other no solution entries could be even more
        // expensive, as pichu was iterating initially over all words starting
        // with i, of which there are only 120 in this list.
        // Upon further testing, it turns out pichu does have a solution,
        // which was found in minimal time.  The problem was that, as the list
        // I used did not contain pichu, the program could never recognize it
        // as a finished grid.  Therefore, when I added pichu to my list of words,
        // the program could consider solutions.  I implemented a new setWord
        // function that adds the word automatically to the list, but this
        // should never be used other than when you know you are adding a new
        // word, as the computation involves copying over an array to increase it.
        // Perhaps I should be working with ArrayLists, but this is what I
        // have right now.
        mine.setWord(0, "aspen", true);
        System.out.println(new Searcher().SearchAll(mine));
        //On the other hand, when a word with a solution is put in, the answer
        //can be returned quite quickly.  When checking for a general solution
        //in this set, a solution was found in 30 seconds with printing
        //a different initial word without printing took 1 second to finish
        //when a solution existed.  This was for 5x5

        //4 letter grids are much quicker.  4 seconds for both a blank
        //grid and a modified grid to finish, while a no solution grid was
        //able to check the entire tree in 30 seconds to return no solution;
        //another no solution grid took 2 min 51 sec to finish working

        new Searcher().SearchwithWords("xanax", "xerox", wordy);
    }
    
}