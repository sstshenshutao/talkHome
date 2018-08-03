package com.cybertaotao.talkhome.login;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cybertaotao.model.database.DatabaseHandler;
import com.cybertaotao.model.database.UserInfo;
import com.cybertaotao.model.distributePort.DistributePort;
import com.cybertaotao.talkhome.filter.AlphabetFilter;
import com.cybertaotao.talkhome.filter.Filter;
import com.cybertaotao.talkhome.filter.LengthFilter;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Filter<String> filter;
	private DatabaseHandler db= new DatabaseHandler();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		setPostTestFilter();
	}
	private void setPostTestFilter() {
		// TODO Auto-generated method stub
		this.filter = new AlphabetFilter();
		this.filter.appendFilter(new LengthFilter(16));
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
		printhtml(out);
	}

	private void printhtml(PrintWriter out) {
		// TODO Auto-generated method stub
		out.print("<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<meta charset=\"utf-8\">\n" + "<title>注册</title>\n"
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
				+ "<form action=\"register\" method=\"post\">\n" + "  <div id=\"login\">\n"
				+ "    <div id=\"login_title\">注&nbsp;册&nbsp;账&nbsp;号</div>\n"
				+ "    <div class=\"line\"><span id=\"msg\"></span></div>\n" + "    \n"
				+ "    <div class=\"line\">账号密码请在15字以内\n" + "    </div>\n" + "    <div class=\"line\">只允许使用数字和字母\n"
				+ "    </div>\n" + "\n" + "    <div class=\"line\">账号&nbsp;\n"
				+ "      <input type=\"text\" placeholder=\"\" id=\"username\" name=\"account\"/>\n" + "    </div>\n"
				+ "    <div class=\"line\">密码&nbsp;\n"
				+ "      <input type=\"password\" placeholder=\"\" id=\"username\" name=\"password\"/>\n"
				+ "    </div>\n" + "    <div class=\"line\">重复密码&nbsp;\n"
				+ "      <input type=\"password\" placeholder=\"\" id=\"username\" name=\"repassword\"/>\n"
				+ "    </div>\n" + "    <div class=\"line\">邀请码&nbsp;\n"
				+ "      <input type=\"password\" value=\"cybertaotaoVPN\" id=\"username\" name=\"invitecode\"/>\n"
				+ "    </div>\n" + "    <div class=\"line\">邮箱&nbsp;\n"
				+ "      <input type=\"password\" placeholder=\"\" id=\"username\" name=\"email\"/>\n" + "    </div>\n"
				+ "    <div>\n"
				+ "      <input type=\"hidden\" id=\"checkcode\" value=\"need!generate\" name=\"checkcode\"/>\n"
				+ "    </div>\n" + "    <input type=\"submit\" id=\"log_submit\" type=\"button\" value=\"注册账号\">\n"
				+ "</div>\n" + "\n"
				+ "    <div class=\"line\"><a href=\"https://cybertaotao.com/bbs/\">进入论坛</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"https://cybertaotao.com/download/\">下载软件</a></div>\n"
				+ "\n"  + "</form>\n" + "<div id=\"wrong\">海阔凭鱼跃&nbsp;&nbsp;天高任鸟飞&nbsp;&nbsp;</div>\n"
				+ "\n" + "</body>\n" + "</html>");
		out.flush();
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
		setPostTestFilter();
		//检测长度大小和是否复合要求
		request.setCharacterEncoding("UTF-8");
		String acc= request.getParameter("account").trim();
		String pwd = request.getParameter("password").trim();
		String repwd =request.getParameter("repassword").trim();
		String invc= request.getParameter("invitecode").trim();
		String email= request.getParameter("email").trim();
		
		
		
		boolean wrong= false;
		if (!wrong &&acc.length()==0) wrong=checkwrong("请输入账号",out);
		if (!wrong &&pwd.length()==0) wrong=checkwrong("请输入密码",out);
		if (!wrong &&repwd.length()==0) wrong=checkwrong("请重复输入密码",out);
		if (!wrong &&invc.length()==0) wrong=checkwrong("请输入邀请码",out);
		if (!wrong &&email.length()==0) wrong=checkwrong("请输入email",out);
		
		
		if (!wrong &&!invc.equals("cybertaotaoVPN") ) wrong=checkwrong("错误邀请码",out) ;
		if (!wrong &&!pwd.equals(repwd)) wrong=checkwrong("两次密码不一致",out) ;
		if (!wrong &&this.filter.apply(acc).equals("")) wrong=checkwrong("账号过长或非法字符",out) ;
		if (!wrong &&this.filter.apply(pwd).equals("")) wrong=checkwrong("密码过长或非法字符",out) ;
		if (!wrong && email.length()>=50) wrong=checkwrong("email过长或非法字符",out) ;
		//检查是否有重名
		File	 accountFile = new File("./userdata/"+acc);
		if (!wrong &&accountFile.exists()) wrong=checkwrong("账号已经存在",out) ;
		//创建新用户
		if (!wrong) {
			UserInfo newUserInfo = new UserInfo();
			newUserInfo.account=acc;
			newUserInfo.timelimit="2018.07.31";
			String[] portpasswd = new DistributePort().manageOnePortPasswd(pwd);
			if (portpasswd==null) {checkwrong("分配失败，请重新注册！", out); return;}
			newUserInfo.port = portpasswd[0];
			newUserInfo.portpassword = portpasswd[1];
			newUserInfo.volumn = "unlimited";
			newUserInfo.email = email;
			newUserInfo.invitecode = invc;
			//写入
			db.createNewUser(acc, pwd, newUserInfo);
			checkOK("注册成功："+acc,out);
		}
		
		
		
//		doGet(request, response);
	}

	private boolean checkwrong(String string,PrintWriter out) {
		// TODO Auto-generated method stub
		out.print(
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"
						+ "<html>" + "<head>"
						+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" + "<title>" + "注册成功"
						+ "</title>" + "</head>" + "<body>" + "<h2>" + string + "</h2>" + "</body>"
						+ "</html>");
		out.flush();
		return true;
	}
	private void checkOK(String string,PrintWriter out) {
		out.print(
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"
						+ "<html>" + "<head>"
						+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" + "<title>" + "注册成功"
						+ "</title>" + "</head>" + "<body>" + "<h2>" + string + "</h2>" + 
						"<div class=\"line\"><a href=\"https://cybertaotao.com/login/\">返回登陆</a>&nbsp;&nbsp;&nbsp;&nbsp;</div>"+"</body>"
						+ "</html>");
		
		out.flush();
	}
}
