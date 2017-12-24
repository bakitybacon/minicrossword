package display;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CluePanel extends JPanel
{
	private static final long serialVersionUID = -4429409071091275624L;
	HashMap<Integer,String> acrossClues = new HashMap<>();
	HashMap<Integer,String> downClues = new HashMap<>();
	
	ClueListPanel acrossPanel;
	ClueListPanel downPanel;
	
	public CluePanel(HashMap<Integer,String> acrossClues, HashMap<Integer,String> downClues)
	{
		this.acrossClues = acrossClues;
		this.downClues = downClues;
		setLayout(new GridLayout(0,2));
		acrossPanel = new ClueListPanel(acrossClues,true);
		downPanel = new ClueListPanel(downClues,false);
		add(acrossPanel);
		add(downPanel);
	}
	public CluePanel()
	{
		setLayout(new GridLayout(0,2));
		acrossPanel = new ClueListPanel(acrossClues,true);
		downPanel = new ClueListPanel(downClues,false);
		add(acrossPanel);
		add(downPanel);
	}
	public void setAcrossClues(HashMap<Integer, String> hm)
	{
		acrossClues = hm;
		acrossPanel.setClues(hm);
	}
	public void setDownClues(HashMap<Integer, String> hm)
	{
		downClues = hm;
		downPanel.setClues(hm);
	}
}


class ClueListPanel extends JPanel
{
	private static final long serialVersionUID = -2475823024253988357L;
	HashMap<Integer,String> clues;
	final boolean isAcross;
	public ClueListPanel(HashMap<Integer,String> clues, boolean isAcross)
	{
		this.clues = clues;
		this.isAcross = isAcross;
		
		setLayout(new GridLayout(6,0));
		JLabel header = new JLabel("<html><u>"+(isAcross ? "Across" : "Down")+"</u></html>", SwingConstants.CENTER);
		header.setFont(new Font("liberation serif",Font.BOLD,20));
		add(header);
		for(int i : clues.keySet())
		{
			JPanel jp  = new JPanel();
			jp.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.weightx = 1;
			jp.add(new JLabel(i+""),gbc);
			gbc.weightx = 4;
			JTextField jtf = new JTextField(clues.get(i),10);
			jp.add(jtf,gbc);
			add(jp);
		}
	}
	public void setClues(HashMap<Integer, String> clues)
	{
		this.clues = clues;
		removeAll();
		updateUI();
		setLayout(new GridLayout(6,0));
		JLabel header = new JLabel("<html><u>"+(isAcross ? "Across" : "Down")+"</u></html>", SwingConstants.CENTER);
		header.setFont(new Font("liberation serif",Font.BOLD,20));
		add(header);
		for(int i : clues.keySet())
		{
			JPanel jp  = new JPanel();
			jp.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.weightx = 1;
			jp.add(new JLabel(i+""),gbc);
			gbc.weightx = 4;
			JTextField jtf = new JTextField(clues.get(i),10);
			jp.add(jtf,gbc);
			add(jp);
		}
	}
}