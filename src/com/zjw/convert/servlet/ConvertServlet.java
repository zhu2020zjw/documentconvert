package com.zjw.convert.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zjw.convert.exception.PathException;
import com.zjw.convert.exception.SuffixException;
import com.zjw.convert.service.DocumentConvert;

public class ConvertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static String ip;
	private static String path; //文件上传的地址
	private static String suffix;//获得上传文件的后缀名
	
	static {
		try {
			Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
	
			while (nifs.hasMoreElements()) {
			    NetworkInterface nif = nifs.nextElement();
			
			    // 获得与该网络接口绑定的 IP 地址，一般只有一个
			    Enumeration<InetAddress> addresses = nif.getInetAddresses();
			    while (addresses.hasMoreElements()) {
			        InetAddress addr = addresses.nextElement();
			
			        if (addr instanceof Inet4Address) { // 只关心 IPv4 地址
			            if(nif.getName().equals("wlan0")) {
			            	ip = addr.getHostAddress();
			            }
			        }
			    }
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	private DocumentConvert documentConvert = new DocumentConvert();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		path = UpLoadServlet.targetPath;
		suffix = path.split("\\.")[path.split("\\.").length-1];
		
		if(path.equals(null)) {
			try {
				throw new PathException("文件未上传，请先上传文件");
			} catch (PathException e) {
				e.printStackTrace();
			}
		}
	
		String methodString = request.getParameter("method");
		try {
			Method method = getClass().getDeclaredMethod(methodString, HttpServletRequest.class,HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request, response);
		}  catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected void previewOfSD(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String targetFileName = "F:\\Eclipse\\apache-tomcat-8.5.40\\webapps\\output\\target.html";//用于做转换的目的地址
		String result = documentConvert.convertBySuffix(suffix, path, targetFileName);
		targetFileName = "http://" + ip + ":8080/output/target.html";//用于进行显示的地址
		if(result != null) {
			request.setAttribute("targetFileName",targetFileName);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		return;
	}
	
	protected void previewOfHD(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String targetFileName = "F:\\Eclipse\\apache-tomcat-8.5.40\\webapps\\output\\targetOfHD.html";
		String result = documentConvert.convertBySuffix(suffix, path, targetFileName);
		targetFileName = "http://" + ip + ":8080/output/targetOfHD.html";
		if(result != null) {
			request.setAttribute("targetFileNameOfHD",targetFileName);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		return;
	}
	
	protected void convert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String targetOfSuffix = request.getParameter("suffix");//获得要转换的文件的后缀名
		String targetFileName = "F:\\Eclipse\\apache-tomcat-8.5.40\\webapps\\output\\target." + targetOfSuffix;
		try {
			String result = documentConvert.convertBySuffix(suffix, targetOfSuffix, path, targetFileName);
			targetFileName = "http://" + ip + ":8080/output/target." + targetOfSuffix;
			if(result != null) {
				request.setAttribute("convertIP",targetFileName);
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
		} catch (SuffixException e) {
			e.printStackTrace();
		}
	}
}
