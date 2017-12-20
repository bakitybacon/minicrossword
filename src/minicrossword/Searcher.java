/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minicrossword;

import java.util.Arrays;

/**
 *
 * @author ciarwen
 */
public class Searcher 
{
    // This classes only purpose is to run the Search command.  I had tried to
    // write it as part of the node class, but found I was unable to talk about
    // a node's children without having this remove.
    public Searcher()
    {
        
    }
    // This function returns the first complete solution it finds using its
    // algorithm before it stops.
    public static Crossword Search(Crossword curr)
    {
        // Accesses crossword information
        //System.out.println(curr);
        // if the crossword is already solved, return the crossword
        // As a note, I have also changed this if to simply print out an answer
        // and then return null to continue looking.  When I put in 2 words using
        // the Search method below, I would typically find between 40 and 80 solutions.
        // Do not try this over an empty grid with the 9000 list.  I ran it for 4
        // minutes, after which it had not finished the loop looking at the first word
        // of the second insertion into the grid.  When one considers that each second loop
        // will have roughly 9000/26 possibilities to check, that there are roughly 9000 first
        // possibilities to check, and that a partial search into one level 2 took more than
        // 4 minutes, it could take several decades to complete the search.  I also had
        // more than 700 solutions after 4 minutes, so it isn't like one needs to
        // run it a long time to get several answers.
    	
    	System.out.println(curr);
    	
        if(curr.isFinished())
        {
            return curr;
        }
        //If the crossword is completely filled, but the answers are not valid,
        //as checked above, return null to signal no possible answer
        else if(curr.isFull())
        {
            return null;
        }
        // If the crossword cannot be solved from this point, return null
        else if(!curr.canBeFinished())
        {
            return null;
        }
        else
        {
            // This finds whether the index returned by select index is a row
            // or column, also initializes indy and poss for later
            boolean isRow = curr.IndexRow();
            int indy;
            String[] poss;
            // This if else block determines the index to search at
            if(isRow)
            {
                indy = curr.selectIndexRow();
            }
            else
            {
                indy = curr.selectIndexCol();
            }
            // This if else block gets a list of possible words at that index
            if(isRow)
            {
                poss = curr.getPossRow(indy);
            }
            else
            {
                poss = curr.getPossCol(indy);
            }
            
            // This forms an Array of words based on the possibilities available
            // at this index
            // It might be better to run natural for loops instead of for each
            // loops here, as I am still messing around with indices while they
            // are running
            Word[] posse = new Word[poss.length];
            for(int i = 0; i < poss.length; i++)
            {
                posse[i] = new Word(poss[i]);
            }
            
            // This calculates how many possible words can be added to the grid
            // when each word has been added.  This allows me to sort the words
            // by their likelihood of being put here in the final solution
            if(isRow)
            {
                for(int i = 0; i < poss.length; i++)
                {
                    curr.setWordRow(indy, poss[i]);
                    posse[i].setNumPoss(curr.sumPoss());
                    curr.undoLast();
                }
            }
            else
            {
                for(int i = 0; i < poss.length; i++)
                {
                    curr.setWordCol(indy, poss[i]);
                    posse[i].setNumPoss(curr.sumPoss());
                    curr.undoLast();
                }
            }
            // Sort array of words based on their flexibility
            // In future, I simply iterate through the list, so that I start
            // with the most likely word
            Arrays.sort(posse, new WordSortbyNumPoss());
            // Reverts Words into strings so that they can be inserted into the
            // grid in the proper order
            for(int i = 0; i < posse.length; i++)
            {
                poss[i] = posse[i].toString();
            }
            // This loop creates the next level in the recursion.
            for(int i = 0; i < poss.length; i++)
            {
                if(isRow)
                {
                    //The current crossword is modified and passed to the 
                    // next level of recursion.
                    curr.setWordRow(indy, poss[i]);
                    Crossword cw = Search(curr);
                    // If cw kicks back a null, either the puzzle is unsolvable
                    // at the next level or it couldn't find a solution deeper
                    // in the tree. More checking is needed
                    // If anything else is returned, it has to be a full solution.
                    if(!(cw == null))
                    {
                        return cw;
                    }
                    // if the puzzle found by the next level is not a solution,
                    // then undo the last move and try a different possibility
                    curr.undoLast();
                }
                else
                {
                    curr.setWordCol(indy, poss[i]);
                    Crossword cw = Search(curr);
                    if(!(cw == null))
                    {
                        return cw;
                    }
                    curr.undoLast();
                }
            }
            // If all possibilities at this level have been checked without
            // finding a solution, return null.
            return null;
        }
    }
    // This function will search for solutions.  Every time it finds one it will 
    // print it out and continue searching.  In order to have this finish in a 
    // reasonable amount of time, it is best to start SearchAll with at least two 
    // words already filled in.  At the moment, I am only using this function in 
    // my SearchwithWords function, which inserts 2 words into the grid before 
    // running SearchAll.
    public Crossword SearchAll(Crossword curr)
    {
        // Accesses crossword information
        //System.out.println(curr);
        // if the crossword is already solved, return the crossword
        // As a note, I have also changed this if to simply print out an answer
        // and then return null to continue looking.  When I put in 2 words using
        // the Search method below, I would typically find between 40 and 80 solutions.
        // Do not try this over an empty grid with the 9000 list.  I ran it for 4
        // minutes, after which it had not finished the loop looking at the first word
        // of the second insertion into the grid.  When one considers that each second loop
        // will have roughly 9000/26 possibilities to check, that there are roughly 9000 first
        // possibilities to check, and that a partial search into one level 2 took more than
        // 4 minutes, it could take several decades to complete the search.  I also had
        // more than 700 solutions after 4 minutes, so it isn't like one needs to
        // run it a long time to get several answers, though.
        if(curr.isFinished())
        {
            System.out.println(curr);
            return null;
        }
        //If the crossword is completely filled, but the answers are not valid,
        //as checked above, return null to signal no possible answer
        else if(curr.isFull())
        {
            return null;
        }
        // If the crossword cannot be solved from this point, return null
        else if(!curr.canBeFinished())
        {
            return null;
        }
        else
        {
            // This finds whether the index returned by select index is a row
            // or column, also initializes indy and poss for later
            boolean isRow = curr.IndexRow();
            int indy;
            String[] poss;
            // This if else block determines the index to search at
            if(isRow)
            {
                indy = curr.selectIndexRow();
            }
            else
            {
                indy = curr.selectIndexCol();
            }
            // This if else block gets a list of possible words at that index
            if(isRow)
            {
                poss = curr.getPossRow(indy);
            }
            else
            {
                poss = curr.getPossCol(indy);
            }
            // This forms an Array of words based on the possibilities available
            // at this index
            // It might be better to run natural for loops instead of for each
            // loops here, as I am still messing around with indices while they
            // are running
            Word[] posse = new Word[poss.length];
            for(int i = 0; i < poss.length; i++)
            {
                posse[i] = new Word(poss[i]);
            }
            // This calculates how many possible words can be added to the grid
            // when each word has been added.  This allows me to sort the words
            // by their likelihood of being put here in the final solution
            if(isRow)
            {
                for(int i = 0; i < poss.length; i++)
                {
                    curr.setWordRow(indy, poss[i]);
                    posse[i].setNumPoss(curr.sumPoss());
                    curr.undoLast();
                }
            }
            else
            {
                for(int i = 0; i < poss.length; i++)
                {
                    curr.setWordCol(indy, poss[i]);
                    posse[i].setNumPoss(curr.sumPoss());
                    curr.undoLast();
                }
            }
            // Sort array of words based on their flexibility
            // In future, I simply iterate through the list, so that I start
            // with the most likely word
            Arrays.sort(posse, new WordSortbyNumPoss());
            // Reverts Words into strings so that they can be inserted into the
            // grid in the proper order
            for(int i = 0; i < posse.length; i++)
            {
                poss[i] = posse[i].toString();
            }
            // This loop creates the next level in the recursion.
            for(int i = 0; i < poss.length; i++)
            {
                if(isRow)
                {
                    //The current crossword is modified and passed to the 
                    // next level of recursion.
                    curr.setWordRow(indy, poss[i]);
                    Crossword cw = SearchAll(curr);
                    // If cw kicks back a null, either the puzzle is unsolvable
                    // at the next level or it couldn't find a solution deeper
                    // in the tree. More checking is needed
                    // If anything else is returned, it has to be a full solution.
                    if(!(cw == null))
                    {
                        return cw;
                    }
                    // if the puzzle found by the next level is not a solution,
                    // then undo the last move and try a different possibility
                    curr.undoLast();
                }
                else
                {
                    curr.setWordCol(indy, poss[i]);
                    Crossword cw = SearchAll(curr);
                    if(!(cw == null))
                    {
                        return cw;
                    }
                    curr.undoLast();
                }
            }
            // If all possibilities at this level have been checked without
            // finding a solution, return null.
            return null;
        }
    }
    // THis function puts two words into a grid in all possible orientations and
    // prints all solutions that contain those two words
    public Crossword SearchwithWords(String word1, String word2, String[] wordy)
    {
        if(word1.length() == word2.length())
        {
            {
                for(int rowin = 0; rowin < word1.length(); rowin++)
                {
                    for(int colin = 0; colin < word1.length(); colin++)
                    {
                        Crossword grid1 = new Crossword(word1.length(), word1.length(), wordy);
                        if(rowin != colin)
                        {
                            grid1.setWord(rowin, word1, true);
                            grid1.setWord(colin, word2, true);
                            SearchAll(grid1);
                        }
                    }
                }
                for(int letterrow = 0; letterrow < word1.length(); letterrow++)
                {
                    for(int lettercol = 0; lettercol < word1.length(); lettercol++)
                    {
                        Crossword grid1 = new Crossword(word1.length(), word1.length(), wordy);
                        if(word1.charAt(letterrow) == word2.charAt(lettercol))
                        {
                            grid1.setWord(lettercol ,word1, true);
                            grid1.setWord(letterrow ,word2, false);
                            SearchAll(grid1);
                        }
                    }
                }
            }
        }
        return null;
    }
}