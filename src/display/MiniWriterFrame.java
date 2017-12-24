package display;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

public class MiniWriterFrame extends JFrame
{

	private static final long serialVersionUID = 370198487525302767L;
	final GridPanel gp;
	final CluePanel cp;
	public MiniWriterFrame()
	{
		setLayout(new GridLayout(2,0));
		gp = new GridPanel();
		add(gp);
		gp.setFocusable(true);
		
		ArrayList<Integer>[] clues = gp.generateClues();
		ArrayList<Integer> across = clues[0];
		ArrayList<Integer> down = clues[1];
		
		HashMap<Integer, String> acrossClues = new HashMap<>();
		for(int i = 0; i < across.size(); i++)
			acrossClues.put(across.get(i), "");
		
		HashMap<Integer, String> downClues = new HashMap<>();
		for(int i = 0; i < down.size(); i++)
			downClues.put(down.get(i), "");
		
		cp = new CluePanel(acrossClues, downClues);
		cp.setFocusable(true);
		gp.setCluePanel(cp);
		add(cp);
	}
	
	public static void main(String[] args)
	{
		MiniWriterFrame mwf = new MiniWriterFrame();
		mwf.setTitle("Minnie");
		mwf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mwf.setSize(300,600);
		mwf.setVisible(true);
	}
}
