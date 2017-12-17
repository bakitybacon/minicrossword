/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minicrossword;
import java.util.Comparator;
/**
 *
 * @author ciarwen
 */
public class WordSortbyNumPoss implements Comparator<Word>
{
    //This class is used to sort Words according to how flexible they are
        public int compare(Word a, Word b)
        {
            return b.getNumPoss()-a.getNumPoss();
        }
}