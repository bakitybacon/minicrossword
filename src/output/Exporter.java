package output;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHeightRule;

public class Exporter 
{
	public static void toWordFile(HashMap<Integer,String> acrossclues, HashMap<Integer,String> downclues)
	{
		System.out.println(acrossclues);
		System.out.println(downclues);
		String[] acrosscluetexts = new String[acrossclues.size()];
		String[] downcluetexts = new String[downclues.size()];
		
		int index = 0;
		for(Map.Entry<Integer, String> thing : acrossclues.entrySet())
		{
			acrosscluetexts[index++] = thing.getKey()+". "+thing.getValue();
		}
		index = 0;
		for(Map.Entry<Integer, String> thing : downclues.entrySet())
		{
			downcluetexts[index++] = thing.getKey()+". "+thing.getValue();
		}
		
		Arrays.sort(acrosscluetexts);
		Arrays.sort(downcluetexts);
		
		
		
		try
		{
			XWPFDocument doc = new XWPFDocument();
	
			XWPFParagraph title = doc.createParagraph();    
			XWPFRun run = title.createRun();
			Calendar cal = new GregorianCalendar();
			run.setText("Mini Crossword ("+cal.get(Calendar.MONTH)+"/"+
					cal.get(Calendar.DAY_OF_MONTH)+"/"+(cal.get(Calendar.YEAR)%100)+")");
			run.setBold(true);
			run.addBreak();
			title.setAlignment(ParagraphAlignment.CENTER);
	
			String imgFile = "grid.png";
			FileInputStream is = new FileInputStream(imgFile);
			run.addBreak();
			run.addPicture(is, XWPFDocument.PICTURE_TYPE_PNG, imgFile, Units.toEMU(200), Units.toEMU(200)); // 200x200 pixels
			is.close();
			
			run.addCarriageReturn();
			
			//column width in Twentieths of a Point
			int tablen = Math.max(acrossclues.keySet().size(), downclues.keySet().size())+1;
			XWPFTable table = doc.createTable(tablen, 2);
			//table.getCTTbl().getTblPr().unsetTblBorders();
			
			
			XWPFTableRow labels = table.getRow(0);
			
			XWPFParagraph across = labels.getCell(0).addParagraph();    
			XWPFRun acrun = across.createRun();
			acrun.setBold(true);
			acrun.setUnderline(UnderlinePatterns.SINGLE);
			acrun.setText("ACROSS");
			acrun.setFontSize(16);
			across.setAlignment(ParagraphAlignment.CENTER);
			
			XWPFParagraph down = labels.getCell(1).addParagraph();    
			XWPFRun downrun = down.createRun();
			downrun.setBold(true);
			downrun.setUnderline(UnderlinePatterns.SINGLE);
			downrun.setText("DOWN");
			downrun.setFontSize(16);
			down.setAlignment(ParagraphAlignment.CENTER);
			
			for(int i = 1; i < tablen; i++)
			{
				XWPFTableRow row = table.getRow(i);
				row.setHeight((int)(1440*1/2)); //set height 1/10 inch.
				row.getCtRow().getTrPr().getTrHeightArray(0).setHRule(STHeightRule.EXACT); 
				int numCells = row.getTableCells().size();
				for(int j = 0; j < numCells; j++)
				{
					XWPFTableCell cell = row.getCell(j);
					
					
					if(j == 0 && i <= acrosscluetexts.length)
					{
						cell.setText(acrosscluetexts[i-1]);
					}
					else if(j == 1 && i <= downcluetexts.length)
					{
						
						cell.setText(downcluetexts[i-1]);
					}
					cell.setVerticalAlignment(XWPFVertAlign.CENTER);
					cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(5000));
				}
			}
			
			FileOutputStream fos = new FileOutputStream("minicrossword.docx");
			doc.write(fos);
			fos.close();
			doc.close();
			System.out.println("Wrote to file!");
		}
		catch(IOException | InvalidFormatException e)
		{
			JOptionPane.showMessageDialog(null, "Could not write file!", "real bad error o no", JOptionPane.ERROR_MESSAGE);
		}
	}
}