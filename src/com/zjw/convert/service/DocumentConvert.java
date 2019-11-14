package com.zjw.convert.service;

import com.zjw.convert.util.ConvertorPool;
import com.zjw.convert.util.ConvertorPool.ConvertorObject;

public class DocumentConvert {

	private ConvertorObject convertorObj;
	
	{
		convertorObj = ConvertorPool.getInstance().getConvertor();
	}
	
	public String MSToHtml(String sourceFileName,String targetFileName) {
		try {
			int result = convertorObj.convertor.convertMStoHTML(sourceFileName, targetFileName);
			System.out.println(result);
			if(result == 0) {
				return targetFileName;
			}
		} catch (Exception e) {
			System.out.println("转换出现异常");
		} finally {
			ConvertorPool.getInstance().returnConvertor(convertorObj);
		}
		return null;
	}
	
	public String MSToHtmlOfHD(String sourceFileName,String targetFileName) {
		try {
			int result = convertorObj.convertor.convertMStoHtmlOfSvg(targetFileName, sourceFileName);
			System.out.println(result);
			if(result == 0) {
				return targetFileName;
			}
		} catch (Exception e) {
			System.out.println("转换出现异常");
		} finally {
			ConvertorPool.getInstance().returnConvertor(convertorObj);
		}
		return null;
	}
	
	public void convertBySuffix(String suffix) {
		
	}
	
}
