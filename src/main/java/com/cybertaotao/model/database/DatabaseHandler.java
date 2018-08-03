package com.cybertaotao.model.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.cybertaotao.repository.Md5;

/**
 * @author Shutao Shen
 *
 */
public class DatabaseHandler {
	private String shadowsocksConfig = "/etc/shadowsocks.json";
	private String manageAccount = "./manageAccount";
	private String userdata = "./userdata/";
	private String md5 = "./Md5list.txt";
	private String portFile = "./portFile";
	private DatabaseItem thisUser;
	private List<String> userdataItemList;
	
	/**
	 * @return the shadowsocksConfig
	 */
	public String getShadowsocksConfig() {
		return shadowsocksConfig;
	}

	/**
	 * @param shadowsocksConfig
	 *            the shadowsocksConfig to set
	 */
	public void setShadowsocksConfig(String shadowsocksConfig) {
		this.shadowsocksConfig = shadowsocksConfig;
	}

	public DatabaseHandler() {
	}

	public String getMd5User(String md5String) {
		File md5 = new File(this.md5);
		Properties md5Pro = filetoProperty(md5);
		String acc= md5Pro.getProperty(md5String);
		return acc;
	}
	public String getUserMd5() {
		for (Object a : this.thisUser.getMd5().keySet()) {
			if (thisUser.getAccountName().equals(this.thisUser.getMd5().get(a))) {
				return a.toString();
			}
		}
		// dont find : create
		String md5String = Md5.getMd5(this.thisUser.getAccountName() + "shenshutao");
		this.thisUser.getMd5().put(md5String, this.thisUser.getAccountName());
		try {
			FileOutputStream ops = new FileOutputStream(md5);
			this.thisUser.getMd5().store(ops, "Databasehandle@getUserMd5");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		return md5String;
	}

	public UserInfo getUserInfos() {
		UserInfo ret = new UserInfo();
		Properties pps = this.thisUser.getUserInfo();
		Enumeration<?> enum1 = pps.propertyNames();
		HashMap<String, String> h = new HashMap<>();
		while (enum1.hasMoreElements()) {
			String strKey = (String) enum1.nextElement();
			String strValue = pps.getProperty(strKey);
			h.put(strKey, strValue);
		}
		ret.setInfos(h);
		return ret;
	}

	public String getAccpasswd() {
		Object a = this.thisUser.getAccountpwd().get(this.thisUser.getAccountName());
		return (a != null) ? (String) a : "";
	}

	public boolean createNewUser(String account, String password, UserInfo userInfo) {
		if (this.userdataItemList ==null )this.userdataItemList = UserInfo.getNameList();
		File manageAccount = new File(this.manageAccount);
		if (!manageAccount.exists())
			return false;
		Properties manageAccountPro = filetoProperty(manageAccount);
		if (manageAccountPro.getProperty(account) == null) {
			manageAccountPro.put(account, password);
			try {
				FileOutputStream ops = new FileOutputStream(manageAccount);
				manageAccountPro.store(ops, "Databasehandle@createNewUser");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return false;
			}

			File accountFile = new File(this.userdata + account);
			if (!accountFile.exists()) {
				try {
					accountFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					return false;
				}
				accountFile.setWritable(true);
				accountFile.setReadable(true);
			}
			Properties accountPPS = filetoProperty(accountFile);
			HashMap<String, String> usermap = userInfo.getNewMap();
			for (String key : usermap.keySet()) {
				if (this.userdataItemList.contains(key)) {
					accountPPS.put(key, usermap.get(key));
				}
			}
			try {
				FileOutputStream ops = new FileOutputStream(accountFile);
				accountPPS.store(ops, "Databasehandle@createNewUser");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return false;
			}
			return true;
		} else {
			return false;
		}

	}

	public int getCurrentAvailPort(int portStart, int portEnd) {
		File rf = new File(portFile);
		if (!rf.exists()) {
			try {
				rf.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return 0;
			}
			rf.setWritable(true);
			rf.setReadable(true);
			if (!setCurrentAvailPort(portStart))
				return 0;
			return getCurrentAvailPort(portStart, portEnd);
		} else {
			int ret = 0;
			try {
				BufferedReader br = new BufferedReader(new FileReader(portFile));
				ret = Integer.parseInt(br.readLine());
				br.close();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				return 0;
			}
			if (!setCurrentAvailPort(ret + 1))
				return 0;
			return ret;
		}
	}

	public boolean setCurrentAvailPort(int port) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(portFile));
			bf.write(String.valueOf(port) + "\n");
			bf.flush();
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;

	}

	/**
	 * just set
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setUserInfo(String key, String value) {
		if (this.userdataItemList ==null )this.userdataItemList = UserInfo.getNameList();
		// freelanlan=123123123
		if (key.equals("password") && this.thisUser.getAccountpwd().getProperty(key) != null) {
			this.thisUser.getAccountpwd().setProperty(key, value);

			try {
				FileOutputStream ops = new FileOutputStream(manageAccount);
				this.thisUser.getAccountpwd().store(ops, "Databasehandle@setUserInfo");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return false;
			}

			return true;
		}
		// change md5???以后写 好像不太需要

		// change userdata
		if (this.userdataItemList.contains(key)) {
			this.thisUser.getUserInfo().setProperty(key, value);
			try {
				FileOutputStream ops = new FileOutputStream(this.userdata + this.thisUser.getAccountName());
				this.thisUser.getUserInfo().store(ops, "Databasehandle@setUserInfo");
			} catch (Exception e) {
				return false;
			}
			return true;
		}
		return false;
	}

	public boolean searchUserItem(String account) {
		DatabaseItem ret = null;
		File userdata = new File(this.userdata + account);
		if (!userdata.exists())
			return false;
		File manageAccount = new File(this.manageAccount);
		if (!manageAccount.exists())
			return false;
		File md5 = new File(this.md5);
		if (!md5.exists())
			return false;
		Properties manageAccountPro = filetoProperty(manageAccount);
		Properties md5Pro = filetoProperty(md5);
		Properties userdataPro = filetoProperty(userdata);
		ret = (manageAccountPro != null && md5Pro != null && userdataPro != null)
				? new DatabaseItem(manageAccountPro, md5Pro, userdataPro, account)
				: null;
		if (ret != null && ret.getAccountpwd().getProperty(account) != null) {
			this.thisUser = ret;
			return true;
		}
		return false;
	}

	// public static DatabaseItem[] searchUserItems(int port) {
	// return null;
	// }
	//
	// public static DatabaseItem[] searchUserItems(String timeLimit) {
	// return null;
	// }

	private Properties filetoProperty(File filePath) {
		Properties ret = null;
		try {
			ret = new Properties();
			ret.load(new FileInputStream(filePath));
		} catch (Exception e) {
			System.out.println("filetoProperty wrong");
		}
		return ret;
	}

	public static void main(String[] args) {
		// File debug = new File("debug");
		// System.out.println(debug.getAbsolutePath());
		DatabaseHandler db = new DatabaseHandler();
		HashMap<String, String> map = new HashMap<>();
		map.put("portpassword", "DatabaseHandler1");
		map.put("port", "DatabaseHandler1");
		map.put("invitecode", "DatabaseHandler1");
		map.put("email", "DatabaseHandler1");
		map.put("timelimit", "DatabaseHandler1");
		map.put("volumn", "DatabaseHandler1");
		map.put("account", "DatabaseHandler1");
		System.out.println(db.searchUserItem("freelanlan"));
		System.out.println(db.getUserInfos());

	}
}
