package com.cybertaotao.talkhome;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cybertaotao.model.ipParser.SplitAddress;
import com.cybertaotao.talkhome.filter.Filter;
import com.cybertaotao.talkhome.filter.HtmlFilter;
import com.cybertaotao.talkhome.filter.LengthFilter;
import com.cybertaotao.talkhome.messageContainer.FileMessageContainer;
import com.cybertaotao.talkhome.messageContainer.MessageContainer;

/**
 * Servlet implementation class TalkHome
 */
@WebServlet("/TalkHome")
public class TalkHome extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1407173452980744886L;
	private MessageContainer mc;
	private String title = "TaoTao -- a website to talk some secret things";
	private String welcome = "You are now in TaoTao";
	private String allMess = "All Message";
	private Filter<String> filter;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TalkHome() {
		super();
		this.mc = new FileMessageContainer("messages.txt");
		setPostTestFilter();
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
		// printWriter
		// PrintWriter out = response.getWriter();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
		// printJsp
		JSP jsp = printJSP();
		ArrayList<String> head = jsp.head;
		ArrayList<String> end = jsp.end;
		printArraylist(head, out);
		printContext(request, response, out);// print all of the context in this method
		printArraylist(end, out);

		out.close();
	}

	private void printContext(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		// TODO Auto-generated method stub
		// getip
		String ip = getRequestIP(request);
		String location = SplitAddress.getParserIp(ip);
		out.print("<h2>"+"We don't record Ip and location. "
				+ "But you need to know: it's very easy to get your IP and location, "
				+ "if you don't use VPN! Your IP:" + ip + location );
		out.print("try VPN right now for free this Monate!"+ "</h2>");
		out.print("<h2>"+"我们不记录IP地址，"
				+ "但是你需要知道：如果你不使用VPN，网站获取你的ip地址和位置信息是很容易的！"
				 + ip + location );
		out.print("现在试用VPN，本月不限流量完全免费！"+ "</h2>");
		printArraylist(mc.getAllMessage(), out, "");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		setPostTestFilter();
		String message = this.filter.apply(request.getParameter("postmessage"));
		
		if (message.trim().length() != 0) {
			ArrayList<String> ms = new ArrayList<>();
			ms.addAll(dealwithBr(message));
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

	private void setPostTestFilter() {
		// TODO Auto-generated method stub
		this.filter = new HtmlFilter();
		this.filter.appendFilter(new LengthFilter(5000));
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
//		jsp.end.addAll(hiddenEle());
		jsp.end.add("</body>");
		jsp.end.add("</html>");
		return jsp;
	}
//	private ArrayList<String> hiddenEle() {
//		ArrayList<String> ret = new ArrayList<>();
//		ret.add("<p hidden></p>");
//	}
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
				out.println("<div>" + name + x + "</div>");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private static String getRequestIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	private static List<String> dealwithBr(String meString) {
		if (meString.indexOf("<br>") > 0) {
			return Arrays.asList(meString.split("<br>"));
		}else {
			List<String> a= new ArrayList<>();
			a.add(meString);
			return a;
		}
		
	}

}
