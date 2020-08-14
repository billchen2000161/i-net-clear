package com.systex.SHL;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.systex.Main;

public class unity {

	private Logger Logger = LoggerFactory.getLogger(Main.class);

	public boolean compareRecords(int ptrData,int records,String fileName) throws Exception {
		try {
			if (ptrData != records) {
				Logger.error("*ERR* {} 筆數不符  PRT-檔案筆數：{} 下載筆數：{}",fileName,ptrData,records);
				return false;
			} else {
				System.out.println("*MSG* " + fileName + "資料筆數檢核正確，下載筆數：" + records);
				// 寫LOG
				Logger.info("*MSG* " + fileName + "資料筆數檢核正確，下載筆數為：" + records);
				return true;
			}
		} catch (Exception e) {
			Logger.error("Error");
			throw e;
		}
	}
	
	public List readFile (String fileName) throws Exception{
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(SHL.processSourceDirectory + fileName)), "Big5"));) {
			String temp;
			List targetData = new ArrayList();
			while ((temp = br.readLine()) != null) {
				targetData.add(temp);
			}
			return targetData;
		}catch(FileNotFoundException e) {
			Logger.error("*ERR* 缺 PSL 檔" + fileName + "FILE STATUS : " + e.getMessage());
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

	public List<String> decodeOld(String request, int[] split) {
		List response = new ArrayList();
		int index = 0;
		for (int position : split) {
			response.add(request.substring(index, index + position));
			index += position;
		}
		return response;
	}
}
