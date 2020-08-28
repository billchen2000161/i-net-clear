package com.systex.SHL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class unity {
	private final static int[] PSMIndex = { 11, 19, 10, 39, 39, 13, 13, 8, 9, 8, 6, 1, 8, 20, 1, 1, 1, 3, 17, 10, 2,
			1, 1, 6, 2, 1, 1 };
	private final static int[] PSLIndex = { 11, 10, 40, 39, 6 };
	private final static int[] PLAIndex = { 6, 11, 19, 10, 39, 11, 19, 10, 39, 13, 1, 11, 1, 1, 11, 1 };
	private final static int[] PSRIndex = { 6, 10, 1, 13, 13, 13, 19, 1, 10, 20, 1 };
	private final static int[] PIDIndex = { 11, 19, 10, 39, 39, 13, 13, 8, 9, 8, 6, 1, 8, 20, 1, 1, 1, 3, 17, 10, 1,
			1 };
	private final static int[] PCOIndex = { 6, 1, 11, 19, 10, 39, 39, 13, 13, 8, 9, 8, 8, 20, 1, 2, 3, 17 };
	private final static int[] LMKIndex = { 6, 11, 19, 10, 39, 39, 13, 8, 9, 8, 8, 20, 3, 17 };
	private final static int[] RAEIndex = { 11, 19, 10, 39, 39, 9, 6 };
	private final static int[] PELIndex = { 11, 10, 300, 6 };
	private final static int[] DRFIndex = { 6, 11, 19, 10, 39, 11, 19, 10, 39, 13, 10, 19, 10, 21, 1 };
	private final static int[] CPMIndex = { 11, 19, 10, 6, 19, 10, 8, 39, 39, 20, 11, 1, 13, 29, 13, 1, 13 };
	private final static int[] RSAIndex = { 6, 11, 10, 19, 13 };

//	private Logger Logger = LoggerFactory.getLogger(Main.class);

	public boolean compareRecords(int ptrData, int records, String fileName) throws Exception {
		try {
			if (ptrData != records) {
//				Logger.error("*ERR* {} 筆數不符  PTR-檔案筆數：{} 下載筆數：{}", fileName, ptrData, records);
				return false;
			} else {
				System.out.println("*MSG* " + fileName + "資料筆數檢核正確，下載筆數：" + records);
				// 寫LOG
//				Logger.info("*MSG* " + fileName + "資料筆數檢核正確，下載筆數為：" + records);
				return true;
			}
		} catch (Exception e) {
//			Logger.error("Error");
			throw e;
		}
	}

	public List readFile(String fileName) throws Exception {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(SHL.processSourceDirectory + fileName)), "UTF-8"));) {
			String temp;
			List targetData = new ArrayList();
			while ((temp = br.readLine()) != null) {
				targetData.add(temp);
			}
			return targetData;
		} catch (FileNotFoundException e) {
			// 要改彈性?
//			Logger.error("*ERR* 缺 PSL 檔" + fileName + "FILE STATUS : " + e.getMessage());
			throw e;
		}
	}

	public List<String> decode(String request) {
		List response = new ArrayList();
		for (String result : request.split("\\|")) {
			response.add(result.trim());
		}
		return response;
	}

	public List<String> decodeOld(String request, String type) {
		int[] split = null;
		switch (type) {
		case "PSM":
			split = PSMIndex;
			break;
		case "PSL":
			split = PSLIndex;
			break;
		case "PLA":
			split = PLAIndex;
			break;
		case "PSR":
			split = PSRIndex;
			break;
		case "PID":
			split = PIDIndex;
			break;
		case "PCO":
			split = PCOIndex;
			break;
		case "LMK":
			split = LMKIndex;
			break;
		case "RAE":
			split = RAEIndex;
			break;
		case "PEL":
			split = PELIndex;
			break;
		case "DRF":
			split = DRFIndex;
			break;
		case "CPM":
			split = CPMIndex;
			break;
		case "RSA":
			split = RSAIndex;
			break;
		}
		List response = new ArrayList();
		int index = 0;
		for (int position : split) {
			response.add(request.substring(index, index + position).replace("　", "").replace(" ", ""));
			index += position;
		}
		return response;
	}
}
