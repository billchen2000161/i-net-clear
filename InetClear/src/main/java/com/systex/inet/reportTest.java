package com.systex.inet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

import com.inet.report.Engine;
import com.inet.report.Fields;
import com.inet.report.PromptField;
import com.systex.MssqlComponent.MssqlComponent;

public class reportTest {
	private static Properties properties;
	private static String rptPath, DBUrl;

	public static void main(String[] args) {
//		try (FileOutputStream rptFile = new FileOutputStream("E:/RPT_Create.pdf");) {
//			CreateNewReportWithDatabaseFields tt = new CreateNewReportWithDatabaseFields();
//			Engine eng = tt.createAndFillEngine(Engine.EXPORT_PDF);
//			eng.execute();
//			eng.getArea("PH").getSection(0).addPicture(8100, 100, 400, 375, "E:/document.png");
//			for (int i = 1; i <= eng.getPageCount(); i++) {
//				rptFile.write(eng.getPageData(i));
//			}
//			System.out.println("done");
//			eng.setConnectionCloseOnFinishing(true);
//			System.exit(0);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		setConfig();
		try (FileOutputStream pdfFile = new FileOutputStream("E:/sample2.pdf");) {
			Engine eng = new Engine(Engine.EXPORT_PDF);
			String urlQ = "E:\\Issue.rpt";
			eng.setReportFile(urlQ);
			String[] columns = { "cdType", "cdAgent" };
			Object[][] data = { { "a", "John" }, { "b", "Peter" }, { "c", "Ana" } };
			eng.setData(getData());
//			eng.setData(columns, data);
			eng.execute();

			for (int i = 1; i <= eng.getPageCount(); i++) {
				pdfFile.write(eng.getPageData(i));
			}

			System.out.println("done");
			eng.stopAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		System.exit(0);
	}

	public static void setConfig() {
		try (FileInputStream configInputStream = new FileInputStream("rpt.properties");) {
			properties = new Properties();
			properties.load(configInputStream);

			// set up arg
			rptPath = properties.getProperty("rptPath");
			DBUrl = properties.getProperty("DBConnectString");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static ResultSet getData() throws SQLException, Exception {
		MssqlComponent mssql = new MssqlComponent(DBUrl);
		ResultSet rs = mssql.Query("SELECT TOP 10 * FROM Issue");
		ResultSetMetaData rsmd = rs.getMetaData();
		return rs;
	}

}
