package com.systex.SHL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
				List targetData = new ArrayList<List<String>>();
				if (!shlFunction.compareRecords((int) ptrData.get(type), uncheckedFile.size(),
						inSecurityNo + "." + type)) {
					return "ER";
				}
				for (Object s : uncheckedFile) {
					targetData.add(shlFunction.decodeOld((String) s, type));
				}
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
			writePRU();
			writeTRU();
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

			String stockData = br.readLine();
			String cdStock = stockData.substring(0, 4);
			String szStock = stockData.substring(4).trim();
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

	public void writePRU() throws IOException {
		int PRURecords = 0;
		System.out.println("*MSG* SHL35PRU 程式開始!");
		System.out.println("*MSG* 建立PRU檔、檢核資料筆數與產生PDF報表檔");
		System.out.println("*MSG* 證券代號：" + inSecurityNo);
		// 寫LOG
//		Logger.info("*MSG* 程式開始，證券代號：" + inSecurityNo”);
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(processSourceDirectory + inSecurityNo + ".PRU"))));
			for (Object s : PSMData) {
				if (((List) s).get(21).equals("1") || ((List) s).get(21).equals("3")) {
					String temp = "";
					for (Object str : (List) s) {
						temp += (String) str;
						temp += "|";
					}
					bw.write(temp + "\n");
					PRURecords++;
				}
			}
			bw.flush();
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// 寫LOG
//			Logger.error("*ERR* 缺 PRU 檔" + PRU檔名 +"FILE STATUS : " + e.printStackTrace());
			throw e;
		}
		System.out.println("*MSG* 建立PRU檔案成功，資料筆數：" + PRURecords);
		// 寫LOG
//		Logger.info("*MSG*建立PRU檔案成功，資料筆數："+ PRURecords);

	}

	public void writeTRU() throws IOException {
		int TRURecords = 0;
		System.out.println("*MSG* SHL36TRU 程式開始!");
		System.out.println("*MSG* 建立TRU檔、檢核資料筆數與產生PDF報表檔");
		System.out.println("*MSG* 證券代號：" + inSecurityNo);
		// 寫LOG
//		Logger.info("*MSG* 程式開始，證券代號：" + inSecurityNo”);
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(processSourceDirectory + inSecurityNo + ".TRU"))));
			for (Object s : PSMData) {
				if (((List) s).get(21).equals("2") || ((List) s).get(21).equals("3")) {
					String temp = "";
					for (Object str : (List) s) {
						temp += (String) str;
						temp += "|";
					}
					bw.write(temp + "\n");
					TRURecords++;
				}
			}
			bw.flush();
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// 寫LOG
//			Logger.error("*ERR* 缺 TRU 檔" + PRU檔名 +"FILE STATUS : " + e.printStackTrace());
			throw e;
		}
		System.out.println("*MSG* 建立TRU檔案成功，資料筆數：" + TRURecords);
		// 寫LOG
//		Logger.info("*MSG*建立TRU檔案成功，資料筆數："+ TRURecords);

	}
}
