package com.systex.SHL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SHL {
	public static String processSourceDirectory = "E:\\";
	public static String inSecurityNo;
	private String[] fileType = { "PSM", "PSL", "PLA", "PSR", "PID", "PCO", "LMK", "RAE", "PEL", "DRF", "CPM", "RSA" };
//	private Logger Logger = LoggerFactory.getLogger(Main.class);
	private unity shlFunction = new unity();
	private Map ptrData;
	private List PSMData;
	private List PSLData;
	private List PLAData;
	private List PSRData;
	private List PIDData;
	private List PCOData;
	private List LMKData;
	private List RAEData;
	private List PELData;
	private List DRFData;
	private List CPMData;
	private List RSAData;

	public SHL(String processSourceDirectory, String inSecurityNo) {
		this.processSourceDirectory = processSourceDirectory;
		this.inSecurityNo = inSecurityNo;
	}

	public String startSHL() {
		PSMData = new ArrayList<List<String>>();
		PSLData = new ArrayList<List<String>>();
		PLAData = new ArrayList<List<String>>();
		PSRData = new ArrayList<List<String>>();
		PIDData = new ArrayList<List<String>>();
		PCOData = new ArrayList<List<String>>();
		LMKData = new ArrayList<List<String>>();
		RAEData = new ArrayList<List<String>>();
		PELData = new ArrayList<List<String>>();
		DRFData = new ArrayList<List<String>>();
		CPMData = new ArrayList<List<String>>();
		RSAData = new ArrayList<List<String>>();

		setConfig();

		try {
			ptrData = readPTR();
			// 走訪所有檔案一次檢查完成
			for (String type : fileType) {
				List uncheckedFile = shlFunction.readFile(inSecurityNo + "." + type);
				if (!shlFunction.compareRecords((int) ptrData.get(type), uncheckedFile.size(),
						inSecurityNo + "." + type)) {
					return "ER";
				}
				for (Object s : uncheckedFile) {
					List targetData = new ArrayList<List<String>>();
					targetData.add(shlFunction.decodeOld((String) s, type));
					switch (type) {
					case "PSM":
						PSMData = targetData;
						break;
					case "PSL":
						PSLData = targetData;
						break;
					case "PLA":
						PLAData = targetData;
						break;
					case "PSR":
						PSRData = targetData;
						break;
					case "PID":
						PIDData = targetData;
						break;
					case "PCO":
						PCOData = targetData;
						break;
					case "LMK":
						LMKData = targetData;
						break;
					case "RAE":
						RAEData = targetData;
						break;
					case "PEL":
						PELData = targetData;
						break;
					case "DRF":
						DRFData = targetData;
						break;
					case "CPM":
						CPMData = targetData;
						break;
					case "RSA":
						RSAData = targetData;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("X");
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
//					Logger.error("*ERR* PTR 缺資料" + inSecurityNo + ".PTR");
					throw new NullPointerException();
				} else {
					PTRRecords.put(s, Integer.parseInt(dataNow));
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			// 寫LOG
//			Logger.error("*ERR* PTR下載筆數非數字。" + inSecurityNo + ".PTR" + " 檔案內容：" + dataNow);
			throw e;
		} catch (NullPointerException e) {
			throw e;
		}
		return PTRRecords;
	}
}
