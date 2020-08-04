package com.systex.inet;

import java.io.File;
import java.io.FileOutputStream;

import com.inet.report.Area;
import com.inet.report.Engine;
import com.inet.report.FormulaField;
import com.inet.report.Paragraph;
import com.inet.report.RDC;
import com.inet.report.Section;
import com.inet.report.Text;

/**
 * This sample explains how to export pdf files at runtime. Don't forget to
 * change the target of the output file (on top of main).
 * 
 * @see Engine#EXPORT_HTML
 * @see Engine#EXPORT_PDF
 * @see Engine#EXPORT_RTF
 * @see Engine#EXPORT_XLS
 * @see Engine#EXPORT_CSV
 * @see Engine#EXPORT_PS
 * @see Engine#EXPORT_XML
 */
public class inetDataSource {

	/**
	 * Create a new report, adds a box and text field and exports the report to PDF.
	 * 
	 * @param args arguments not used
	 */
	public static void main(String[] args) {
		try {
			// create a new empty engine with target PDF export
			Engine eng = RDC.createEmptyEngine(Engine.EXPORT_PDF);

			// fill the engine

			// get the detail area
			Area area = eng.getArea("D");
			// get the first section in detail area
			Section section = area.getSection(0);
			// add some stuff to detail area, like a box
			section.addBox(100, 100, 1000, 1000);
			// and a text
			Text text = section.addText(2300, 100, 1000, 1000);
			Paragraph para = text.addParagraph();
			para.addTextPart("whatever");
			// fill everything you need, for details look for the other examples

			section.addFieldElement(
					eng.getFields().addFormulaField("name", "currentDateTime()", FormulaField.FORMULA_USER), 100, 1000,
					2000, 500);

			// execute the engine
			eng.execute();

			// write the content in a PDF file
			File pdfFile = new File("E:/sample_create.pdf");
			FileOutputStream fos = new FileOutputStream(pdfFile);
			for (int i = 1; i <= eng.getPageCount(); i++) {
				fos.write(eng.getPageData(i));
			}
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		System.exit(0);
	}
}
