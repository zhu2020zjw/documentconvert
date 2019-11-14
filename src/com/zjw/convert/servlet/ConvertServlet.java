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

import com.zjw.convert.service.DocumentConvert;
public class ConvertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static String ip;
	
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
		String path = UpLoadServlet.targetPath;//文件上传的地址
		String targetFileName = "F:\\Eclipse\\apache-tomcat-8.5.40\\webapps\\output\\target.html";//用于做转换的目的地址
		
		String result = documentConvert.MSToHtml(path, targetFileName);
		
		targetFileName = "http://" + ip + ":8080/output/target.html";//用于进行显示的地址
		if(result != null) {
			request.setAttribute("targetFileName",targetFileName);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		return;
	}
	
	protected void previewOfHD(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = UpLoadServlet.targetPath;
		String targetFileName = "F:\\Eclipse\\apache-tomcat-8.5.40\\webapps\\output\\targetOfHD.html";
		
		String result = documentConvert.MSToHtml(path, targetFileName);
		
		targetFileName = "http://" + ip + ":8080/output/targetOfHD.html";
		if(result != null) {
			request.setAttribute("targetFileNameOfHD",targetFileName);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		return;
	}

}
