package com.zjw.convert.service;

import com.zjw.convert.exception.SuffixException;
import com.zjw.convert.util.ConvertorPool;
import com.zjw.convert.util.ConvertorPool.ConvertorObject;

public class DocumentConvert {

	private static ConvertorObject convertorObj;
	
	static{
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
	
	public String MSToPDF(String sourceFileName,String targetFileName) {
		try {
			int result = convertorObj.convertor.convertMStoPDF(sourceFileName, targetFileName);
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
	
	public String pdfToHtml(String sourceFileName,String targetFileName) {
		try {
			int result = convertorObj.convertor.convertPdfToHtml(sourceFileName, targetFileName);
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
	
	public String convertBySuffix(String suffix,String sourceFileName,String targetFileName) {
		String result = null;
		switch (suffix) {
		case "doc":
		case "docx":
			result = MSToHtml(sourceFileName, targetFileName);
			break;
		case "pdf":
			result = pdfToHtml(sourceFileName, targetFileName);
			break;
		default:
			break;
		}
		return result;
	}
	
	public String convertBySuffix(String sourceSuffix,String targetSuffix,String sourceFileName,String targetFileName) throws SuffixException {
		String result = null;
		if((sourceSuffix.equals("doc")||(sourceSuffix.equals("docx"))) && targetSuffix.equals("html")) {
			result = MSToHtml(sourceFileName, targetFileName);
		}else if((sourceSuffix.equals("doc")||(sourceSuffix.equals("docx"))) && targetSuffix.equals("pdf")) {
			result = MSToPDF(sourceFileName, targetFileName);
		}else if(sourceSuffix.equals("pdf") && targetSuffix.equals("html")) {
			result = pdfToHtml(sourceFileName, targetFileName);
		}else {
			throw new SuffixException("后缀名错误");
		}
		return result;
	}
	
}
