package com.cybertaotao.talkhome.download;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Download
 */
@WebServlet("/Download")
public class Download extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Download() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
		String filename = request.getParameter("systemtype");
		if (filename == null) {
			printPage(out);
		} else {
			String newfilename = "download/" + filename;
			File f = new File(newfilename);
			if (f.exists() && (filename.equals("windows.zip") || filename.equals("mac.zip")
					|| filename.equals("android.apk"))) {
				response.setContentType("application/zip");
				response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					fis = new FileInputStream(newfilename);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
				} catch (IOException ex) {
//					System.out.println(ex.toString());
				} finally {
					if (bis != null) {
						bis.close();
					}
					if (fis != null) {
						fis.close();
					}
				}

			} else {
				printWrong(out);
			}

		}
	}

	private void printWrong(PrintWriter out) {
		// TODO Auto-generated method stub
		out.print("<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<meta charset=\"utf-8\"></head>\n"
				+ "<title>下载</title>\n" + "<body> <div>错误参数&nbsp;</div>\n" + "</body>\n" + "</html>");
		out.flush();
	}

	private void printPage(PrintWriter out) {
		// TODO Auto-generated method stub
		out.print("<!DOCTYPE html>\n" + 
				"<html>\n" + 
				"<head>\n" + 
				"<meta charset=\"utf-8\">\n" + 
				"<title>下载</title>\n" + 
				"    <link href=\"testlogin.css\" rel=\"stylesheet\" type=\"text/css\"/>\n" + 
				"<style>\n" + 
				"    #login {\n" + 
				"    width: 700px;\n" + 
				"    height: auto;\n" + 
				"    overflow: hidden;\n" + 
				"    border: solid 1px #CCCCCC;\n" + 
				"}\n" + 
				"#login_title {\n" + 
				"    width: 100%;\n" + 
				"    height: 40px;\n" + 
				"    line-height: 40px;\n" + 
				"    background-color: #F60;\n" + 
				"    text-align: center;\n" + 
				"}\n" + 
				".line {\n" + 
				"    width: 700px;\n" + 
				"    height: 30px;\n" + 
				"    line-height: 30px;\n" + 
				"    margin-left: 20px;\n" + 
				"    text-align: center;\n" + 
				"    font-family: 楷体;\n" + 
				"}\n" + 
				".line input {\n" + 
				"    width: 150px;\n" + 
				"}\n" + 
				".line a {\n" + 
				"    font-size: 14px;\n" + 
				"    color: black;\n" + 
				"}\n" + 
				".line span {\n" + 
				"    color: #F00;\n" + 
				"}\n" + 
				"#log_submit {\n" + 
				"    display: block;\n" + 
				"    width: 200px;\n" + 
				"    height: 30px;\n" + 
				"    margin-left: 45px;\n" + 
				"    margin-top: 15px;\n" + 
				"    margin-bottom: 5px;\n" + 
				"}\n" + 
				"#wrong {\n" + 
				"    width: 700px;\n" + 
				"    height: 40px;\n" + 
				"    line-height: 40px;\n" + 
				"    color: #F60;\n" + 
				"    margin-left: 45px;\n" + 
				"    margin-top: 15px;\n" + 
				"    margin-bottom: 5px;\n" + 
				"    text-align: center;\n" + 
				"}\n" + 
				"</style>\n" + 
				"</head>\n" + 
				"\n" + 
				"\n" + 
				"<body>\n" + 
				"  <div id=\"login\">\n" + 
				"    <div id=\"login_title\">选&nbsp;择&nbsp;版&nbsp;本</div>\n" + 
				"    <div class=\"line\"><span id=\"msg\"></span></div>\n" + 
				"    <a class=\"line\" href=\"https://cybertaotao.com/download/Download?systemtype=windows.zip\">windows版本</a><br>\n" + 
				"    <a class=\"line\" href=\"https://cybertaotao.com/download/Download?systemtype=mac.zip\">mac版本</a><br>\n" + 
				"    <a class=\"line\" href=\"https://cybertaotao.com/download/Download?systemtype=android.apk\">android版本</a><br>\n" + 
				"\n" + 
				"</div>\n" + 
				"<div id=\"wrong\">请按照如下要求设置：&nbsp;</div>\n" + 
				"<div id=\"wrong\">1.打开服务器设置&nbsp;</div>\n" + 
				"<img src=\"https://upload.cc/i1/2018/07/29/HNJvWU.jpg\" width=\"700\">\n" + 
				"<div id=\"wrong\">2.加密方式设置aes-256-gcm &nbsp;</div>\n" + 
				"    <img src=\"https://upload.cc/i1/2018/07/29/Fvwjrp.jpg\" width=\"700\">\n" + 
				"    <div id=\"wrong\">3.设置软件生效&nbsp;</div>\n" + 
				"    <img src=\"https://upload.cc/i1/2018/08/01/dL9FSB.jpg\" width=\"700\">\n" + 
				"    <div class=\"line\"><a href=\"https://cybertaotao.com/bbs/\">进入论坛</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"https://cybertaotao.com/download/\">下载软件</a></div>\n" + 
				"<div class=\"line\"><a href=\"https://cybertaotao.com/exit/\">退出账号</a>&nbsp;&nbsp;&nbsp;&nbsp;</div>\n" + 
				"  \n" + 
				"\n" + 
				"<div id=\"wrong\">海阔凭鱼跃&nbsp;&nbsp;天高任鸟飞&nbsp;&nbsp;</div>\n" + 
				"\n" + 
				"</body>\n" + 
				"</html>");
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
