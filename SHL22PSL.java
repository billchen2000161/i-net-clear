package com.systex.SHL22PSL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.systex.FileProcess.fileprocess;
import com.systex.Record.Main;

public class SHL22PSL {
	private static Properties properties;
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	private fileprocess fileprocess;
	private String ProcessSourceDirectory;
	
	private String inSecurityNo;
	private String account;
	private String uniformNumber;
	private String name;

	public SHL22PSL() {

	}

	public String SHL22PSL(String inSecurityNo) {
		try {
			setConfig();
			fileprocess = new fileprocess();

			String ptrFile = inSecurityNo + ".PTR";
			String pslFile = inSecurityNo + ".PSL";
			List ptrData = (ArrayList) fileprocess.getData(ProcessSourceDirectory + ptrFile);
			List pslData = (ArrayList) fileprocess.getData(ProcessSourceDirectory + pslFile);
			check(ptrData,pslData);
			exportTXT(new ArrayList(), true, "E:/exportTXT.txt");
			return "OK";
		} catch (Exception e) {
			return "ER";
		}

	}

	private void setConfig() {
		try (FileInputStream configInputStream = new FileInputStream("SHLConfig.properties");) {
			properties = new Properties();

			properties.load(configInputStream);
			ProcessSourceDirectory = properties.getProperty("ProcessSourceDirectory");
		} catch (Exception e) {
			logger.error("");
		}
	}

	private boolean check(List ptrdata,List pslData) {
		return false;
	}

	private boolean exportTXT(List Data, boolean isFirst, String txtPath) {
		File file = new File(txtPath);
		try (BufferedWriter fw = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));) {
			fw.append(headerGen(isFirst));
			fw.newLine();
			fw.append("input sentence 1 ");
			fw.flush(); // 全部寫入緩存中的內容
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private String supplementGen(String data, String element, int totalAmount) throws UnsupportedEncodingException {
		String result = data;
		for (int i = 0; i < (totalAmount - data.getBytes("utf-8").length); i++) {
			result += element;
		}
		return result;
	}

	private String headerGen(boolean isFirst) throws UnsupportedEncodingException {
		String result = "";
		if (isFirst) {
			result += "1";
		} else {
			result += "*";
		}
		// 1
		result += supplementGen(""," ", 80);
		result += supplementGen("臺灣集中保管結算所   長戶名資料"," ", 97);
		System.out.println("space: " + (result.getBytes("utf-8").length - 81));
		result += supplementGen("日期 :"," ", 7);
		result += supplementGen("20200812"," ", 10);
		result += "\n";

		// 2
		result += "1";

		result += "\n";
		// 3
		result += "1";
		result += supplementGen("","=", 194);
		result += "\n";
		
		return result;
	}

	private String recordGen() {
		String result = "";
		return result;
	}

}
