package display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

import grid.Letter;

public class GridPanel extends JPanel implements MouseListener, KeyListener
{
	private static final long serialVersionUID = -8288262956831802797L;
	private int numClues = 0;
	LetterBox[][] grid = new LetterBox[5][5];
	
	int focusX;
	int focusY;
	
	public GridPanel()
	{
		for(int i = 0; i < 5; i++)
			for(int j = 0; j < 5; j++)
			{
				grid[i][j] = new LetterBox();
				if(i == 0 && j == 0)
					grid[i][j] = new LetterBox(Letter.BLACK);
				if(i == 1 && j == 0)
					grid[i][j] = new LetterBox(Letter.BLACK);
				if(i == 0 && j == 1)
					grid[i][j] = new LetterBox(Letter.C);
				if(i == 0 && j == 2)
					grid[i][j] = new LetterBox(Letter.H);
				if(i == 0 && j == 3)
					grid[i][j] = new LetterBox(Letter.A);
				if(i == 0 && j == 4)
					grid[i][j] = new LetterBox(Letter.T);
			}
		
		focusX = -1;
		focusY = -1;
		generateClues();
		
		addMouseListener(this);
		addKeyListener(this);
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, getWidth(),getHeight());
		
		int squareWidth = getWidth()/5;
		int squareHeight = getHeight()/5;
		
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2));
		
		for(int i = 0; i < 5; i++)
			for(int j = 0; j < 5; j++)
			{
				
				g2.drawRect(squareWidth * j, squareHeight * i, squareWidth, squareHeight);
				LetterBox lb = grid[i][j];
				
				Font font = new Font("liberation serif", Font.BOLD, 30);
				g2.setFont(font);
				
				if(lb.letter.equals(Letter.BLACK))
				{
					g2.fillRect(squareWidth * j, squareHeight * i, squareWidth, squareHeight);
				}
				
				if(j == focusX && i == focusY)
				{
					g2.setColor(Color.CYAN);
					g2.fillRect(squareWidth * j, squareHeight * i, squareWidth, squareHeight);
					g2.setColor(Color.BLACK);
				}
				
				if(!lb.letter.equals(Letter.BLANK) && !lb.letter.equals(Letter.BLACK))
				{
					g2.drawChars(new char[]{lb.letter.getChar()}, 0, 1, squareWidth * j + squareWidth/3, squareHeight * i + squareHeight*2/3);
				}
				
				if(lb.clueNum != -1)
				{
					g2.setFont(new Font("liberation serif", Font.PLAIN, 10));
					g2.drawString(""+lb.clueNum, squareWidth*(j+1) - squareWidth/9, squareHeight*i+squareHeight/6);
				}
			}
	}
	
	public int numClues()
	{
		generateClues();
		return numClues;
	}
	
	private void generateClues()
	{
		boolean[][] isClueSquare = new boolean[5][5];
		for(int row = 0; row < 5; row++)
		{
			for(int col = 0; col < 5; col++)
				if(!grid[row][col].letter.equals(Letter.BLACK))
				{
					isClueSquare[row][col] = true;
					break;
				}
		}
		
		for(int col = 0; col < 5; col++)
		{
			for(int row = 0; row < 5; row++)
				if(!grid[row][col].letter.equals(Letter.BLACK))
				{
					isClueSquare[row][col] = true;
					break;
				}
		}
		
		int clueNum = 1;
		
		for(int i = 0; i < 5; i++)
			for(int j = 0; j < 5; j++)
			{
				if(isClueSquare[i][j])
				{
					grid[i][j].setClueNum(clueNum++);
				}
				else grid[i][j].setClueNum(-1);
			}
		for(LetterBox[] lb : grid)
			System.out.println(Arrays.toString(lb));
		
		numClues = clueNum;
	}

	@Override
	public void mouseClicked(MouseEvent me)
	{
		int x = me.getX();
		int y = me.getY();
		
		int squareWidth = getWidth()/5;
		int squareHeight = getHeight()/5;
		
		if(me.getClickCount() == 2)
		{
			if(!grid[y/squareHeight][x/squareWidth].getLetter().equals(Letter.BLACK))
				grid[y/squareHeight][x/squareWidth].setLetter(Letter.BLACK);
			else grid[y/squareHeight][x/squareWidth].setLetter(Letter.BLANK);
		}
		else 
		{
			focusX = x/squareWidth;
			focusY = y/squareHeight;
			
			System.out.println(focusX);
			System.out.println(focusY);
		}
		generateClues();
		repaint();
	}
	
	

	@Override
	public void keyPressed(KeyEvent ke) 
	{
		if(focusX != -1 && focusY != -1)
		{
			char c = ke.getKeyChar();
			if(!Letter.letterExists(c))
			{
				if(ke.getKeyCode() == KeyEvent.VK_DOWN)
				{
					if(focusY+1 < 5)
						focusY++;
					repaint();
				}
				else if(ke.getKeyCode() == KeyEvent.VK_UP)
				{
					if(focusY-1 >= 0)
						focusY--;
					repaint();
				}
				else if(ke.getKeyCode() == KeyEvent.VK_LEFT)
				{
					if(focusX-1 >= 0)
						focusX--;
					repaint();
				}
				else if(ke.getKeyCode() == KeyEvent.VK_RIGHT)
				{
					if(focusX+1 < 5)
						focusX++;
					repaint();
				}
				return;
			}
			grid[focusY][focusX].setLetter(Letter.fromString(c+"")[0]);
			generateClues();
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
	
	public static void main(String[] args)
	{
		GridPanel gp = new GridPanel();
		gp.setFocusable(true);
		JFrame jf = new JFrame();
		jf.add(gp);
		jf.setTitle("Minnie");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(300,300);
		jf.setVisible(true);
		gp.requestFocusInWindow();
	}
}
