package com.systex.SHL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.systex.Main;

public class SHL {
	public static String processSourceDirectory;
	public static String inSecurityNo;
	private String[] fileType = { "PSM", "PSL", "PLA", "PSR", "PID", "PCO", "LMK", "RAE", "PEL", "DRF", "CPM", "RSA" };
	private Logger Logger = LoggerFactory.getLogger(Main.class);
	private unity shlFunction = new unity();
	private Map ptrData;

	public SHL(String processSourceDirectory, String inSecurityNo) {
		this.processSourceDirectory = processSourceDirectory;
		this.inSecurityNo = inSecurityNo;
	}

	public String startSHL() {
		
		setConfig();
		
		try {
			
			ptrData = readPTR();

			List pslData = shlFunction.readFile(inSecurityNo + ".PSL");
			if(!shlFunction.compareRecords((int)ptrData.get("PSL"), pslData.size(), inSecurityNo + ".PSL")) {
				return "ER";
			}
			
			
		} catch (Exception e) {
			return "ER";
		}
		return "OK";
	}

	public void setConfig() {

	}

	public Map<String, Integer> readPTR() throws IOException {
		Map<String, Integer> PTRRecords = new HashMap<String, Integer>();
		String dataNow = "";
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(processSourceDirectory + inSecurityNo + ".PTR")), "BIG5"))) {

			System.out.println(br.readLine());
			String dtProcess = br.readLine().substring(11, 21);
			System.out.println(dtProcess);
			System.out.println(br.readLine());
			System.out.println(br.readLine());

			for (String s : fileType) {
				dataNow = br.readLine().substring(14).trim();
				if (dataNow.equals("")) {
					// 寫LOG
					Logger.error("*ERR* PTR 缺資料" + inSecurityNo + ".PTR");
					throw new NullPointerException();
				} else {
					PTRRecords.put(s, Integer.parseInt(dataNow));
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			// 寫LOG
			Logger.error("*ERR* PTR下載筆數非數字。" + inSecurityNo + ".PTR" + " 檔案內容：" + dataNow);
			throw e;
		} catch (NullPointerException e) {
			throw e;
		}
		return PTRRecords;
	}
}
