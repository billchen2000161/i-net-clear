package com.systex;

import com.systex.SHL.SHL;

public class Main {

	public static void main(String[] args) {
		try {

			SHL shl = new SHL("E:/", "0050");
			shl.startSHL();

		} catch (Exception e) {
			System.exit(-1);
		}
	}

}
