package com.cybertaotao.talkhome.login;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cybertaotao.model.database.DatabaseHandler;
import com.cybertaotao.model.database.UserInfo;
import com.cybertaotao.repository.Identidy;
import com.cybertaotao.repository.RandomPasswd;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 768041959820036229L;
	private Identidy identify;
	private DatabaseHandler db;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		this.identify = new Identidy();
		this.db = new DatabaseHandler();
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

		// account + checkcode

		// get Cookie
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			boolean login = false;
			for (Cookie c : cookies) {
				if (c.getName().equals("LoginAccess")) {
					String account = new Identidy().checkMd5(c.getValue());
					if (account != null) {
						printUserInfo(out, account);
						login = true;
					}
				}
			}
			if (!login) {
				boolean checkcode = false;
				for (Cookie c : cookies) {
					if (c.getName().equals("checkcode")) {
						printOriginal(out, c.getValue());
						checkcode = true;
					}
				}
				if (!checkcode) {
					String tmp = generateCheckcode();
					Cookie checkcodeCookie = new Cookie("checkcode", tmp);
					checkcodeCookie.setMaxAge(-1);
					response.addCookie(checkcodeCookie);
					printOriginal(out, tmp);
				}
			}
		} else {
			String tmp = generateCheckcode();
			Cookie checkcodeCookie = new Cookie("checkcode", tmp);
			checkcodeCookie.setMaxAge(-1);
			response.addCookie(checkcodeCookie);
			printOriginal(out, tmp);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8));
		String checkcode = null;
		try {
			// checkcode protect roBot
			Cookie[] cookies = request.getCookies();
			if (cookies == null)
				return;
			if ((checkcode = request.getParameter("checkcode")) == null)
				return;
			boolean cc = false;
			for (Cookie c : cookies) {
				if (c.getName().equals("checkcode")) {
					if (c.getValue().equals(checkcode))
						cc = true;
				}
			}
			if (!cc)
				return;
			// check account and password
			String acc = request.getParameter("account").trim();
			String passwd = request.getParameter("password");
			if (db.searchUserItem(acc) && identify.checkPasswd(acc, passwd)) {
				// set cookie
				String md5String = db.getUserMd5();
				Cookie cookie = new Cookie("LoginAccess", md5String);
				cookie.setMaxAge(30 * 24 * 3600);
				response.addCookie(cookie);
				printUserInfo(out, acc);

			} else {
				printWrong(out, checkcode);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return;
		}

		// doGet(request, response);
	}

	private void printUserInfo(PrintWriter out, String account) {
		if (!this.db.searchUserItem(account)) return;
		UserInfo userInfo =this.db.getUserInfos();
		out.print("<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<meta charset=\"utf-8\">\n" + "<title>用户信息</title>\n"
				+ "    <link href=\"testlogin.css\" rel=\"stylesheet\" type=\"text/css\"/>\n" + "<style>\n"
				+ "    #login {\n" + "    width: 290px;\n" + "    height: auto;\n" + "    overflow: hidden;\n"
				+ "    border: solid 1px #CCCCCC;\n" + "}\n" + "#login_title {\n" + "    width: 100%;\n"
				+ "    height: 40px;\n" + "    line-height: 40px;\n" + "    background-color: #F60;\n"
				+ "    text-align: center;\n" + "}\n" + ".line {\n" + "    width: 250px;\n" + "    height: 30px;\n"
				+ "    line-height: 30px;\n" + "    margin-left: 20px;\n" + "    text-align: center;\n"
				+ "    font-family: 楷体;\n" + "}\n" + ".line input {\n" + "    width: 150px;\n" + "}\n" + ".line a {\n"
				+ "    font-size: 14px;\n" + "    color: black;\n" + "}\n" + ".line span {\n" + "    color: #F00;\n"
				+ "}\n" + "#log_submit {\n" + "    display: block;\n" + "    width: 200px;\n" + "    height: 30px;\n"
				+ "    margin-left: 45px;\n" + "    margin-top: 15px;\n" + "    margin-bottom: 5px;\n" + "}\n"
				+ "#wrong {\n" + "    width: 100%;\n" + "    height: 40px;\n" + "    line-height: 40px;\n"
				+ "    color: #F60;\n" + "    margin-left: 45px;\n" + "    margin-top: 15px;\n"
				+ "    margin-bottom: 5px;\n" + "}\n" + "</style>\n" + "</head>\n" + "\n" + "\n" + "<body>\n"
				+ "<form action=\"Login\" method=\"post\">\n" + "  <div id=\"login\">\n"
				+ "    <div id=\"login_title\">用&nbsp;户&nbsp;信&nbsp;息</div>\n"
				+ "    <div class=\"line\"><span id=\"msg\"></span></div>\n" + "    ");
		out.print("<div class=\"line\">账号:&nbsp;&nbsp;" + userInfo.account + "\n" + "    </div>\n"
				+ "    <div class=\"line\">会员期限:&nbsp;&nbsp;" + userInfo.timelimit + "\n" + "    </div>\n"
				+ "    <div class=\"line\">端口号(port):\n" + "    </div>\n" + "    <div class=\"line\">"
				+ userInfo.port + "\n" + "    </div>\n" + "    <div class=\"line\">端口密码(port_password):\n"
				+ "    </div>\n" + "    <div class=\"line\">" + userInfo.portpassword + "\n" + "    </div>"
				+ "    <div class=\"line\">剩余流量:\n" + "    </div>\n" + "    <div class=\"line\">"
				+ userInfo.volumn + "\n" + "    </div>");
		out.print("\n" + "    <div class=\"line\">原密码&nbsp;\n"
				+ "      <input type=\"password\" placeholder=\"\" id=\"username\" name=\"originpassword\"/>\n"
				+ "    </div>\n" + "    <div class=\"line\">新密码&nbsp;\n"
				+ "      <input type=\"password\" placeholder=\"\" id=\"username\" name=\"newpassword\"/>\n"
				+ "    </div>\n" + "    <div class=\"line\">重复新密码&nbsp;\n"
				+ "      <input type=\"password\" placeholder=\"\" id=\"username\" name=\"renewpassword\"/>\n"
				+ "    </div>\n" + "    <input type=\"submit\" id=\"log_submit\" type=\"button\" value=\"修改密码\">\n"
				+ "</div>\n" + "\n"
				+ "    <div class=\"line\"><a href=\"https://cybertaotao.com/bbs/\">进入论坛</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"https://cybertaotao.com/download/\">下载软件</a></div>\n"
				+ "\n"
				+ "<div class=\"line\"><a href=\"https://cybertaotao.com/exit/\">退出账号</a>&nbsp;&nbsp;&nbsp;&nbsp;</div>"
				+ "</form>\n" + "<div id=\"wrong\">海阔凭鱼跃&nbsp;&nbsp;天高任鸟飞&nbsp;&nbsp;</div>\n" + "\n" + "</body>\n"
				+ "</html>");
		out.flush();
	}

	private void printWrong(PrintWriter out, String checkcode) {
		out.print("<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<meta charset=\"utf-8\">\n" + "<title>登录</title>\n"
				+ "    <link href=\"testlogin.css\" rel=\"stylesheet\" type=\"text/css\"/>\n" + "<style>\n"
				+ "    #login {\n" + "    width: 290px;\n" + "    height: auto;\n" + "    overflow: hidden;\n"
				+ "    border: solid 1px #CCCCCC;\n" + "}\n" + "#login_title {\n" + "    width: 100%;\n"
				+ "    height: 40px;\n" + "    line-height: 40px;\n" + "    background-color: #F60;\n"
				+ "    text-align: center;\n" + "}\n" + ".line {\n" + "    width: 250px;\n" + "    height: 30px;\n"
				+ "    line-height: 30px;\n" + "    margin-left: 20px;\n" + "    text-align: center;\n"
				+ "    font-family: 楷体;\n" + "}\n" + ".line input {\n" + "    width: 150px;\n" + "}\n" + ".line a {\n"
				+ "    font-size: 14px;\n" + "    color: black;\n" + "}\n" + ".line span {\n" + "    color: #F00;\n"
				+ "}\n" + "#log_submit {\n" + "    display: block;\n" + "    width: 200px;\n" + "    height: 30px;\n"
				+ "    margin-left: 45px;\n" + "    margin-top: 15px;\n" + "    margin-bottom: 5px;\n" + "}\n"
				+ "#wrong {\n" + "    width: 100%;\n" + "    height: 40px;\n" + "    line-height: 40px;\n"
				+ "    color: #F60;\n" + "    text-align: center;\n" + "}\n" + "</style>\n" + "</head>\n" + "\n" + "\n"
				+ "<body>\n" + "<form action=\"Login\" method=\"post\">\n" + "  <div id=\"login\">\n"
				+ "    <div id=\"login_title\">登&nbsp;录</div>\n"
				+ "    <div class=\"line\"><span id=\"msg\"></span></div>\n"
				+ "    <div class=\"line\">账号&nbsp;&nbsp;\n"
				+ "      <input type=\"text\" placeholder=\"账号\" id=\"username\" name=\"account\"/>\n" + "    </div>\n"
				+ "    <div class=\"line\">密码&nbsp;&nbsp;\n"
				+ "      <input type=\"password\" placeholder=\"密码\" id=\"password\" name=\"password\"/>\n"
				+ "    </div>");
		out.print("<div id=\"wrong\">账号或密码错误，请重新输入&nbsp;&nbsp;</div>" + "<div>\n"
				+ "      <input type=\"hidden\" id=\"checkcode\" value=\"" + checkcode + "\" name=\"checkcode\"/>\n"
				+ "    </div>\n" + "    <input type=\"submit\" id=\"log_submit\" type=\"button\" value=\"登录\">\n"
				+ "      <input id =\"log_submit\" type=\"button\" value=\"注册\" onclick=\"location.href='https://cybertaotao.com/register/'\">\n"
				+ "      <input  id=\"log_submit\" type=\"button\" value=\"匿名\" onclick=\"location.href='https://cybertaotao.com/bbs/'\">\n"
				+ "\n"
				+ "    <div class=\"line\"><a href=\"https://cybertaotao.com/forgetid/\">找回账号密码</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"https://cybertaotao.com/register/\">注册账号</a></div>\n"
				+ "\n" + "  </div>\n" + "</form>\n"
				+ "<div id=\"log_submit\">海阔凭鱼跃&nbsp;&nbsp;天高任鸟飞&nbsp;&nbsp;</div>\n" + "\n" + "</body>\n" + "</html>");
		out.flush();
	}

	private void printOriginal(PrintWriter out, String checkcode) {
		out.print("<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<meta charset=\"utf-8\">\n" + "<title>登录</title>\n"
				+ "    <link href=\"testlogin.css\" rel=\"stylesheet\" type=\"text/css\"/>\n" + "<style>\n"
				+ "    #login {\n" + "    width: 290px;\n" + "    height: auto;\n" + "    overflow: hidden;\n"
				+ "    border: solid 1px #CCCCCC;\n" + "}\n" + "#login_title {\n" + "    width: 100%;\n"
				+ "    height: 40px;\n" + "    line-height: 40px;\n" + "    background-color: #F60;\n"
				+ "    text-align: center;\n" + "}\n" + ".line {\n" + "    width: 250px;\n" + "    height: 30px;\n"
				+ "    line-height: 30px;\n" + "    margin-left: 20px;\n" + "    text-align: center;\n"
				+ "    font-family: 楷体;\n" + "}\n" + ".line input {\n" + "    width: 150px;\n" + "}\n" + ".line a {\n"
				+ "    font-size: 14px;\n" + "    color: black;\n" + "}\n" + ".line span {\n" + "    color: #F00;\n"
				+ "}\n" + "#log_submit {\n" + "    display: block;\n" + "    width: 200px;\n" + "    height: 30px;\n"
				+ "    margin-left: 45px;\n" + "    margin-top: 15px;\n" + "    margin-bottom: 5px;\n" + "}\n"
				+ "</style>\n" + "</head>\n" + "\n" + "\n" + "<body>\n" + "<form action=\"Login\" method=\"post\">\n"
				+ "  <div id=\"login\">\n" + "    <div id=\"login_title\">登&nbsp;录</div>\n"
				+ "    <div class=\"line\"><span id=\"msg\"></span></div>\n"
				+ "    <div class=\"line\">账号&nbsp;&nbsp;\n"
				+ "      <input type=\"text\" placeholder=\"账号\" id=\"username\" name=\"account\"/>\n" + "    </div>\n"
				+ "    <div class=\"line\">密码&nbsp;&nbsp;\n"
				+ "      <input type=\"password\" placeholder=\"密码\" id=\"password\" name=\"password\"/>\n"
				+ "    </div>");
		out.print("<div>\n" + "      <input type=\"hidden\" id=\"checkcode\" value=\"" + checkcode
				+ "\" name=\"checkcode\"/>\n" + "    </div>\n"
				+ "    <input type=\"submit\" id=\"log_submit\" type=\"button\" value=\"登录\">\n"
				+ "      <input id =\"log_submit\" type=\"button\" value=\"注册\" onclick=\"location.href='https://cybertaotao.com/register/'\">\n"
				+ "      <input  id=\"log_submit\" type=\"button\" value=\"匿名\" onclick=\"location.href='https://cybertaotao.com/bbs/'\">\n"
				+ "\n"
				+ "    <div class=\"line\"><a href=\"https://cybertaotao.com/forgetid/\">找回账号密码</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"https://cybertaotao.com/register/\">注册账号</a></div>\n"
				+ "\n" + "  </div>\n" + "</form>\n"
				+ "<div id=\"log_submit\">海阔凭鱼跃&nbsp;&nbsp;天高任鸟飞&nbsp;&nbsp;</div>\n" + "\n" + "</body>\n" + "</html>");
		out.flush();
	}

	private String generateCheckcode() {
		String checkcode = new RandomPasswd(10).getPasswd();
		// checkcodes.add(checkcode);
		return checkcode;
	}

}
