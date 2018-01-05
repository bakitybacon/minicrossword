package display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import grid.Letter;
import minicrossword.Crossword;
import minicrossword.Word;
import minicrossword.WordSortbyNumPoss;
import output.Exporter;

public class GridPanel extends JPanel implements MouseListener, KeyListener
{
	private static final long serialVersionUID = -8288262956831802797L;
	private LetterBox[][] grid = new LetterBox[5][5];
	private CluePanel cp = null;
	
	private double wordlistpercentage;
	
	Crossword cross = null;
	
	private String[] masterlist3 = null;
	private String[] masterlist4 = null;
	private String[] masterlist5 = null;
	
	private String[] workinglist = null;
	
	private int focusX;
	private int focusY;
	
	private boolean noLetters = false;
	
	public GridPanel()
	{
		for(int i = 0; i < 5; i++)
			for(int j = 0; j < 5; j++)
			{
				grid[i][j] = new LetterBox();
				if(i == 0 && j == 0)
					grid[i][j] = new LetterBox(Letter.A);
				if(i == 0 && j == 1)
					grid[i][j] = new LetterBox(Letter.S);
				if(i == 0 && j == 2)
					grid[i][j] = new LetterBox(Letter.P);
				if(i == 0 && j == 3)
					grid[i][j] = new LetterBox(Letter.E);
				if(i == 0 && j == 4)
					grid[i][j] = new LetterBox(Letter.N);
			}
		
		focusX = -1;
		focusY = -1;
		
        Scanner words = new Scanner(this.getClass().getResourceAsStream("/wordlists/3sorted.game"));
		ArrayList<String> word3al = new ArrayList<>();
		while(words.hasNext())
		{
		    word3al.add(words.next());
		}
		words.close();
		masterlist3 = new String[word3al.size()];
		word3al.toArray(masterlist3);
		
		words = new Scanner(this.getClass().getResourceAsStream("/wordlists/4sorted.game"));
		ArrayList<String> word4al = new ArrayList<>();
		while(words.hasNext())
		{
		    word4al.add(words.next());
		}
		words.close();
		masterlist4 = new String[word4al.size()];
		word4al.toArray(masterlist4);
		
		words = new Scanner(this.getClass().getResourceAsStream("/wordlists/5sorted.game"));
		ArrayList<String> word5al = new ArrayList<>();
		while(words.hasNext())
		{
		    word5al.add(words.next());
		}
		words.close();
		masterlist5 = new String[word5al.size()];
		word5al.toArray(masterlist5);
		
		workinglist = new String[masterlist3.length + masterlist4.length + masterlist5.length];
		System.arraycopy(masterlist3, 0, workinglist, 0, masterlist3.length);
		System.arraycopy(masterlist4, 0, workinglist, masterlist3.length, masterlist4.length);
		System.arraycopy(masterlist5, 0, workinglist, masterlist3.length+masterlist4.length, masterlist5.length);
		
		System.out.println("Success");
		
		setWordListPercentage(60);
		
		addMouseListener(this);
		addKeyListener(this);
	}
	
	public void setWordListPercentage(double percentage)
	{
		if(percentage > 1)
			percentage /= 100;
		if(percentage > 1 || percentage <= 0)
			throw new IllegalArgumentException("unusable percentage!");
		
		wordlistpercentage = percentage;
		
		int list3len = (int)(masterlist3.length*percentage);
		int list4len = (int)(masterlist4.length*percentage);
		int list5len = (int)(masterlist5.length*percentage);
		workinglist = new String[list3len + list4len + list5len];
		System.arraycopy(masterlist3, 0, workinglist, 0, list3len);
		System.arraycopy(masterlist4, 0, workinglist, list3len, list4len);
		System.arraycopy(masterlist5, 0, workinglist, list3len + list4len, list5len);
	}
	
	public double getWordListPercentage()
	{
		return wordlistpercentage;
	}
	
	public void export()
	{
		noLetters = true;
		BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB); 
		Graphics g = bi.createGraphics();
		paint(g);
		g.dispose();
		try{ImageIO.write(bi,"png",new File("grid.png"));}catch (Exception e) {}
		noLetters = false;
		System.out.println("wrote image!");
		Exporter.toWordFile(cp.getAcrossClues(),cp.getDownClues());
		try 
		{
			Desktop.getDesktop().open(new File("minicrossword.docx"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void clear()
	{
		for(LetterBox[] lb : grid)
			for(LetterBox box : lb)
				if(!box.getLetter().equals(Letter.BLACK))
					box.setLetter(Letter.BLANK);
		repaint();
	}
	
	public void setCluePanel(CluePanel cp)
	{
		this.cp = cp;
	}
	
	public void paintComponent(Graphics g)
	{
		generateClues();	
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, getWidth(),getHeight());
		
		int squareWidth = getWidth()/grid.length;
		int squareHeight = getHeight()/grid[0].length;
		
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2));
		
		for(int i = 0; i < grid.length; i++)
			for(int j = 0; j < grid[0].length; j++)
			{
				
				g2.drawRect(squareWidth * j, squareHeight * i, squareWidth, squareHeight);
				LetterBox lb = grid[i][j];
				
				Font font = new Font("liberation serif", Font.BOLD, 30);
				g2.setFont(font);
				
				if(lb.letter.equals(Letter.BLACK))
				{
					g2.fillRect(squareWidth * j, squareHeight * i, squareWidth, squareHeight);
				}
				
				if(!noLetters && j == focusX && i == focusY)
				{
					if(lb.getLetter().equals(Letter.BLACK))
						g2.setColor(Color.MAGENTA);
					else g2.setColor(Color.CYAN);
					g2.fillRect(squareWidth * j, squareHeight * i, squareWidth, squareHeight);
					g2.setColor(Color.BLACK);
				}
				
				if(!noLetters && !lb.letter.equals(Letter.BLANK) && !lb.letter.equals(Letter.BLACK))
				{
					if(lb.isGray())
						g2.setColor(Color.LIGHT_GRAY);
					else g2.setColor(Color.BLACK);
					g2.drawChars(new char[]{lb.letter.getChar()}, 0, 1, squareWidth * j + squareWidth/3, squareHeight * i + squareHeight*2/3);
					g2.setColor(Color.BLACK);
				}
				
				if(lb.clueNum != -1)
				{
					g2.setFont(new Font("liberation serif", Font.PLAIN, 14));
					g2.drawString(""+lb.clueNum, squareWidth*(j+1) - squareWidth/7, squareHeight*i+squareHeight/4);
				}
			}
	}
	
	public ArrayList<Integer>[] generateClues()
	{
		ArrayList[] cluemap = new ArrayList[2];
		cluemap[0] = new ArrayList<Integer>();
		cluemap[1] = new ArrayList<Integer>();
		int clueNum = 1;
		for(int row = 0; row < 5; row++)
		{
			for(int col = 0; col < 5; col++)
			{
				int aboverow = row - 1;
				int leftcol = col - 1;
				if(!grid[row][col].getLetter().equals(Letter.BLACK) && 
						(leftcol < 0 || aboverow < 0 || grid[aboverow][col].getLetter().equals(Letter.BLACK)
						|| grid[row][leftcol].getLetter().equals(Letter.BLACK)))
				{
					grid[row][col].setClueNum(clueNum);
					if(aboverow < 0 || grid[aboverow][col].getLetter().equals(Letter.BLACK))
						cluemap[1].add(clueNum);
					if(leftcol < 0 || grid[row][leftcol].getLetter().equals(Letter.BLACK))
						cluemap[0].add(clueNum);
					clueNum++;
				}
				else
					grid[row][col].setClueNum(-1);
			}
		}
		return cluemap;
	}

	@Override
	public void mouseClicked(MouseEvent me)
	{
		requestFocus();
		int x = me.getX();
		int y = me.getY();
		
		int squareWidth = getWidth()/5;
		int squareHeight = getHeight()/5;
		
		if(me.getClickCount() == 2)
		{
			if(!grid[y/squareHeight][x/squareWidth].getLetter().equals(Letter.BLACK))
				grid[y/squareHeight][x/squareWidth].setLetter(Letter.BLACK);
			else grid[y/squareHeight][x/squareWidth].setLetter(Letter.BLANK);
			
			generateClues();
			
			if(cp != null)
			{
				ArrayList<Integer>[] clues = generateClues();
				ArrayList<Integer> across = clues[0];
				ArrayList<Integer> down = clues[1];
				
				HashMap<Integer, String> acrossClues = new HashMap<>();
				for(int i = 0; i < across.size(); i++)
					acrossClues.put(across.get(i), "");
				
				HashMap<Integer, String> downClues = new HashMap<>();
				for(int i = 0; i < down.size(); i++)
					downClues.put(down.get(i), "");
				
				cp.setAcrossClues(acrossClues);
				cp.setDownClues(downClues);
			}
		}
		else 
		{
			focusX = x/squareWidth;
			focusY = y/squareHeight;
		}
		repaint();
	}
	
	public void search()
	{
		LetterBox[][] oldgrid = new LetterBox[5][5];
		Letter[][] oldgridletters = new Letter[5][5];
		for(int i = 0; i < grid.length;i++)
		{
			for(int j = 0; j < grid[0].length;j++)
			{
				oldgrid[i][j] = grid[i][j];
				oldgridletters[i][j] = grid[i][j].getLetter();
			}
		}
		Letter[][] lettergrid = new Letter[5][5];
		for(int i = 0; i < grid.length;i++)
			for(int j = 0; j < grid.length;j++)
				lettergrid[i][j] = grid[i][j].getLetter();
		cross = new Crossword(lettergrid,workinglist);
		Searcher searcher = new Searcher();
		cross = searcher.Search(cross,oldgridletters);
		if(cross == null)
		{
			grid = oldgrid;
			repaint();
			return;
		}
		Letter[][] solution = searcher.Search(cross,oldgridletters).getGrid();
		updateFromLetterArray(solution);
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent ke) 
	{
		char c = ke.getKeyChar();
		if(focusX != -1 && focusY != -1)
		{
			if(ke.getKeyCode() == KeyEvent.VK_BACK_SPACE)
			{
				grid[focusY][focusX].setLetter(Letter.BLANK);
				repaint();
			}
			if(!Letter.letterExists(c))
			{
				if(ke.getKeyCode() == KeyEvent.VK_DOWN)
				{
					if(focusY+1 < 5)
						focusY++;
				}
				else if(ke.getKeyCode() == KeyEvent.VK_UP)
				{
					if(focusY-1 >= 0)
						focusY--;
				}
				else if(ke.getKeyCode() == KeyEvent.VK_LEFT)
				{
					if(focusX-1 >= 0)
						focusX--;
				}
				else if(ke.getKeyCode() == KeyEvent.VK_RIGHT)
				{
					if(focusX+1 < 5)
						focusX++;
				}
				repaint();
				return;
			}
			if(cp != null && grid[focusX][focusY].getLetter().equals(Letter.BLACK))
			{
				ArrayList<Integer>[] clues = generateClues();
				ArrayList<Integer> across = clues[0];
				ArrayList<Integer> down = clues[1];
				
				HashMap<Integer, String> acrossClues = new HashMap<>();
				for(int i = 0; i < across.size(); i++)
					acrossClues.put(across.get(i), "");
				
				HashMap<Integer, String> downClues = new HashMap<>();
				for(int i = 0; i < down.size(); i++)
					downClues.put(down.get(i), "");
				
				cp.setAcrossClues(acrossClues);
				cp.setDownClues(downClues);
			}
			grid[focusY][focusX].setLetter(Letter.fromString(c+"")[0]);
			
			if(focusX+1 < 5)
				focusX++;
			else if(focusY+1 < 5) 
			{
				focusX = 0;
				focusY++;
			}
		}
		repaint();
	}

	//don't need any of these~
	@Override
	public void mouseEntered(MouseEvent me) {}
	@Override
	public void mouseExited(MouseEvent me) {}
	@Override
	public void mousePressed(MouseEvent me) {}
	@Override
	public void mouseReleased(MouseEvent me) {}
	@Override
	public void keyReleased(KeyEvent ke) {}
	@Override
	public void keyTyped(KeyEvent ke) {}
	
	public static void main(String[] args) throws Exception
	{
		GridPanel gp = new GridPanel();
		gp.setFocusable(true);
		JFrame jf = new JFrame();
		jf.add(gp);
		jf.setTitle("Minnie");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(300,300);
		jf.setVisible(true);
	}
	
	public void updateFromLetterArray(Letter[][] letters)
	{
		grid = new LetterBox[5][5];
		for(int i = 0; i < grid.length;i++)
			for(int j = 0; j < grid.length;j++)
				grid[i][j] = new LetterBox(letters[i][j]);
		generateClues();
		update(this.getGraphics());
	}
	
	public void updateFromTempSolution(Letter[][] letters, Letter[][] old)
	{
		grid = new LetterBox[5][5];
		for(int i = 0; i < grid.length;i++)
			for(int j = 0; j < grid.length;j++)
			{
				boolean isGray = !letters[i][j].equals(old[i][j]);
				grid[i][j] = new LetterBox(letters[i][j],isGray);
			}
		generateClues();
		update(this.getGraphics());
	}
	
	public class Searcher 
	{
	    // This classes only purpose is to run the Search command.  I had tried to
	    // write it as part of the node class, but found I was unable to talk about
	    // a node's children without having this remove.

	    // This function returns the first complete solution it finds using its
	    // algorithm before it stops.
	    public Crossword Search(Crossword curr, Letter[][] old)
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
	    	
	    	updateFromTempSolution(curr.getGrid(),old);
	    	
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
	                    Crossword cw = Search(curr,old);
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
	                    Crossword cw = Search(curr,old);
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
	}
}
