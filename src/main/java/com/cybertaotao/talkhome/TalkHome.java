package com.cybertaotao.talkhome;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cybertaotao.talkhome.messageContainer.FileMessageContainer;
import com.cybertaotao.talkhome.messageContainer.MessageContainer;

/**
 * Servlet implementation class Talkhome
 */
@WebServlet("/Talkhome")
public class TalkHome extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1407173452980744886L;
	private MessageContainer mc;
	private String title = "滔滔";
	private String welcome = "You are now in 滔滔";
	private String allMess = "Message";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TalkHome() {
		super();
		this.mc = new FileMessageContainer("messages.txt");
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		String ip = getRequestIP(request);
		PrintWriter out = response.getWriter();
		JSP jsp = printJSP();
		ArrayList<String> head = jsp.head;
		ArrayList<String> end = jsp.end;

		printArraylist(head, out);

		printArraylist(mc.getAllMessage(), out, ip);

		printArraylist(end, out);

		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String message = request.getParameter("postmessage").trim();
		if (message.length() != 0) {
			ArrayList<String> ms = new ArrayList<>();
			ms.add(message);
			// BufferedReader in = request.getReader();
			// String line;
			// ArrayList<String> ms= new ArrayList<>();
			// while((line = in.readLine()) != null) {
			// ms.add(line);
			// }
			this.mc.saveAllMessage(ms);
		}
		// in.close();
		this.doGet(request, response);
	}

	class JSP {
		ArrayList<String> head = new ArrayList<>();
		ArrayList<String> end = new ArrayList<>();
	}

	private JSP printJSP() {
		JSP jsp = new JSP();
		jsp.head.add(
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
		jsp.head.add("<html>");
		jsp.head.add("<head>");
		jsp.head.add("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		jsp.head.add("<title>" + title + "</title>");
		jsp.head.add("</head>");
		jsp.head.add("<body>");
		jsp.head.add("<h2>" + welcome + "</h2>");
		jsp.head.add("<h3>" + allMess + ":</h3>");
		jsp.end.add("<form action=\"TalkHome\" method=\"post\">");
		jsp.end.add("<hr>");
		jsp.end.add("message<input type=\"text\" name=\"postmessage\"/><br>");
		jsp.end.add("<input type=\"submit\"/>");
		jsp.end.add("</form>");
		jsp.end.add("</body>");
		jsp.end.add("</html>");
		return jsp;
	}

	private void printArraylist(ArrayList<String> m, PrintWriter out) {
		m.forEach(x -> {
			try {
				out.println("<h4>" + x + "</h4>");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private void printArraylist(ArrayList<String> m, PrintWriter out, String name) {
		m.forEach(x -> {
			try {
				out.println("<h5>" + name + ": " + x + "</h5>");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private static String getRequestIP(HttpServletRequest request) {
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip;
	}
}
