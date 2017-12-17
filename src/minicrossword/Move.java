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
public class Move 
{
    // This class stores the information necessary to undo a move, basically 
    // storing a previous form of the grid that the undo function can use
    // to reinitialize the puzzle at a previous iteration
    public Move(Letter[] prev, int last, boolean isRow)
    {
        lastMove = prev;
        lastIndex = last;
        isLastRow = isRow;
    }
    public Letter[] getLastMove()
    {
        return lastMove;
    }
    public int getLastIndex()
    {
        return lastIndex;
    }
    public boolean getIsRow()
    {
        return isLastRow;
    }
    private Letter[] lastMove;
    private int lastIndex;
    boolean isLastRow;
}