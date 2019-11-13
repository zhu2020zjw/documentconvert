package com.zjw.convert.service;

import application.dcs.Convert;

public class DocumentConvert {

	private Convert convert = new Convert();
	
	public String MSToHtml(String sourceFileName,String targetFileName) {
		int result = convert.convertMStoHTML(sourceFileName, targetFileName);
		System.out.println(result);
		if(result == 0) {
			return targetFileName;
		}
		return null;
	}
	
}
