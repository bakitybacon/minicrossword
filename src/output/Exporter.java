package output;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class Exporter 
{
	public static void toWordFile(String[] acrossclues, String[] downclues)
	{
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
			int[] cols = {2000, 2000, 2000};
			XWPFTable table = doc.createTable(3, 3);
			for(int i = 0; i < table.getNumberOfRows(); i++)
			{
				XWPFTableRow row = table.getRow(i);
				int numCells = row.getTableCells().size();
				for(int j = 0; j < numCells; j++)
				{
					XWPFTableCell cell = row.getCell(j);
					cell.setText("lol "+j);
					cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(cols[j]));
				}
			} 
			
			
			FileOutputStream fos = new FileOutputStream("minicrossword.docx");
			doc.write(fos);
			fos.close();
			doc.close();
		}
		catch(IOException | InvalidFormatException e)
		{
			JOptionPane.showMessageDialog(null, "Could not write file!", "real bad error o no", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void main(String[] args)
	{
		toWordFile(null,null);
	}
}


/*
 */