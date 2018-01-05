package display;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MiniWriterFrame extends JFrame
{

	private static final long serialVersionUID = 370198487525302767L;
	final GridPanel gp;
	final CluePanel cp;
	public MiniWriterFrame()
	{	
		setLayout(new BorderLayout());
		JMenuBar menubar = new JMenuBar();
		JMenu actions = new JMenu("Actions");
		actions.setMnemonic(KeyEvent.VK_F);
		menubar.add(actions);
		
		JMenuItem search = new JMenuItem("Search");
		search.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
		search.addActionListener(new ActionListener( )
				{ @Override public void actionPerformed(ActionEvent e) {gp.search();}});
		actions.add(search);
		
		JMenuItem export = new JMenuItem("Export");
		export.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,ActionEvent.CTRL_MASK));
		export.addActionListener(new ActionListener( )
				{ @Override public void actionPerformed(ActionEvent e) {gp.export();}});
		actions.add(export);
		
		JMenuItem reset = new JMenuItem("Reset");
		reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,ActionEvent.CTRL_MASK));
		reset.addActionListener(new ActionListener( )
				{ @Override public void actionPerformed(ActionEvent e) {gp.clear();}});
		actions.add(reset);
		
		JMenuItem quit = new JMenuItem("Quit");
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,ActionEvent.CTRL_MASK));
		quit.addActionListener(new ActionListener( )
				{ @Override public void actionPerformed(ActionEvent e) {System.exit(0);}});
		actions.add(quit);
		
		JMenuItem wordlist = new JMenuItem("Word List");
		wordlist.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,ActionEvent.CTRL_MASK));
		wordlist.addActionListener(new ActionListener( )
				{ @Override public void actionPerformed(ActionEvent e) {showSliderDialog();}});
		actions.add(wordlist);
		
		add(menubar, BorderLayout.NORTH);
		
		JPanel info = new JPanel();
		info.setLayout(new GridLayout(2,0));
		gp = new GridPanel();
		gp.setFocusable(true);
		
		info.add(gp);
		
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
		
		info.add(cp);
		add(info);
	}
	
	private void showSliderDialog()
	{
	    JOptionPane optionPane = new JOptionPane();
	    JSlider slider = new JSlider(0,100,(int)(gp.getWordListPercentage()*100));
	    slider.setMajorTickSpacing(10);
	    slider.setPaintTicks(true);
	    slider.setPaintLabels(true);

	    slider.addChangeListener(new ChangeListener() {
	    	public void stateChanged(ChangeEvent changeEvent) {
	        JSlider theSlider = (JSlider) changeEvent.getSource();
	        if (!theSlider.getValueIsAdjusting()) {
	          optionPane.setInputValue(new Integer(theSlider.getValue()));
	        }
	      }});
	    optionPane.setMessage(new Object[] { "Percentage of Word List to Use: ", slider });
	    optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
	    optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
	    JDialog dialog = optionPane.createDialog(this, "Word List Percentage");
	    dialog.setVisible(true);
	    if(optionPane.getInputValue() instanceof Integer)
	    {
	    	gp.setWordListPercentage((int)optionPane.getInputValue());
	    }
	}
	
	public static void main(String[] args)
	{
		MiniWriterFrame mwf = new MiniWriterFrame();
		mwf.setTitle("Mini Crossword Generator");
		mwf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mwf.setSize(300,610);
		mwf.setVisible(true);
	}
}
