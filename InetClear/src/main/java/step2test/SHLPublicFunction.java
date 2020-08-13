package step2test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SHLPublicFunction {
	// PROPERTIES
	static String ProcessSourceDirectory = "E:\\";
	static String inSecurityNo = "3562";

	public static Map<String, Integer> readPTR() throws IOException {
		Map<String, Integer> PTRRecords = new HashMap<String, Integer>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(ProcessSourceDirectory + inSecurityNo + ".PTR")), "BIG5"))) {

			System.out.println(br.readLine());
			String dtProcess = br.readLine().substring(11, 21);
			System.out.println(dtProcess);
			System.out.println(br.readLine());
			System.out.println(br.readLine());
			String dataNow = "";
			String[] fileType = { "PSM", "PSL", "PLA", "PSR", "PID", "PCO", "LMK", "RAE", "PEL", "DRF", "CPM", "RSA" };
			for (String s : fileType) {
				dataNow = br.readLine().substring(14).trim();
				if (dataNow.equals("")) {
					// 寫LOG
//					Logger.error("*ERR* PTR 缺資料" + inSecurityNo + ".PTR");
					return null;
				} else {
					PTRRecords.put(s, Integer.parseInt(dataNow));
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			// 寫LOG
//			Logger.error("*ERR* PTR下載筆數非數字。" + inSecurityNo + ".PTR" + " 檔案內容：" + dataNow);
			return null;
		}
		return PTRRecords;
	}

	public static Map<Integer, String> compareRecords(int PTRRecords, String type) throws IOException {
		Map<Integer, String> targetData = new HashMap<Integer, String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(ProcessSourceDirectory + inSecurityNo + "." + type)), "UTF-8"));
			String temp;
			int records = 0;
			while ((temp = br.readLine()) != null) {
				targetData.put(records+1, temp);
				records++;
			}
			if (PTRRecords!= records) {
				// 寫LOG
//				Logger.error("*ERR* PSL筆數不符 " + inSecurityNo + "." + type + " PTR-檔案筆數:" + PTRRecords.get(type) +" 下載筆數:" + records);
				return null;
			} else {
				System.out.println("*MSG* " + type + "資料筆數檢核正確，下載筆數：" + records);
				// 寫LOG
//				Logger.info("*MSG* " + type + "資料筆數檢核正確，下載筆數為："+ records);
				return targetData;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// 寫LOG
//			Logger.error("*ERR* 缺 PSL 檔" + inSecurityNo + "." + type + "FILE STATUS : " + e.getMessage(););
			return null;
		}
	}

	public static void main(String[] args) throws IOException {
		System.out.println(readPTR());
//		System.out.println(compareRecords(e));
	}
}
