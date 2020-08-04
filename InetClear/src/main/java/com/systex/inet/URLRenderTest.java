package com.systex.inet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.inet.report.Engine;
import com.inet.report.RDC;
import com.inet.viewer.URLRenderData;

public class URLRenderTest {

	public static void main(String[] args) throws IOException {
		String url_various = "http://10.10.56.198:1500/?report=http://10.10.56.198:1500/repository/Issue.rpt";
		String url_Issue = "http://10.10.56.198:1500/?reports=C:\\ProgramData\\i-net software\\reporting_System_Default\\samplereports\\Issue.rpt";
		URLRenderData data = new URLRenderData(url_various);
		File file = new File("E:\\Crosstab.pdf");
		FileOutputStream fos = new FileOutputStream(file);
		Properties props = data.getProperties();
		props.setProperty("export_fmt", "pdf");
		props.setProperty("file", "E:/Crosstab.pdf");
		int count = data.getExportChunkCount(props);
		System.out.println("cunck amount: " + count);
		for (int i = 1; count == 0 || i <= count; i++) { // count can be 0 if there is gzip compression
			System.out.println(i);
			byte[] pageData = data.getNextExportChunk();
			if (pageData != null) {
				fos.write(pageData);
				fos.flush();
			} else {
				break;
			}
		}

	}

	public Engine CreateRemoteEngine() {
		String MAINREPORT = "http://10.10.56.198:1500/?reports=C:\\ProgramData\\i-net software\\reporting_System_Default\\samplereports\\Issue.rpt";
		Engine eng = null;
		try {
			// create a new Engine
//			eng = RDC.loadEngine(MAINREPORT, null, "", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eng;
	}

}
