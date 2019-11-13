package com.zjw.convert.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zjw.convert.service.DocumentConvert;
@WebServlet("/convertServlet")
public class ConvertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	private DocumentConvert documentConvert = new DocumentConvert();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = UpLoadServlet.targetPath;
		String targetFileName = "E:\\output\\target.html";
		
		String result = documentConvert.MSToHtml(path, targetFileName);
		if(result != null) {
			request.setAttribute("targetFileName",targetFileName);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		return;
	}

}
