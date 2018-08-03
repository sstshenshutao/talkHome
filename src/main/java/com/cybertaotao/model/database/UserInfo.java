package com.cybertaotao.model.database;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfo {
	public String portpassword = null;
	public String port = null;
	public String invitecode = null;
	public String email = null;
	public String timelimit = null;
	public String volumn = null;
	public String account = null;
	private HashMap<String, String> retMap;

	/**
	 * 
	 */
	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static List<String> getNameList() {
		return Arrays.stream(UserInfo.class.getDeclaredFields()).map(x -> {
			String y = x.toString();
			int a = y.lastIndexOf(".");
			return y.substring(a + 1, y.length());
		}).collect(Collectors.toList());
	}

	public boolean generateNewMap() {
		// retMap
		this.retMap = new HashMap<>();
		Field[] fs = this.getClass().getFields();
		for (Field f : fs) {
			try {
				this.retMap.put(f.getName(), (String) f.get(this));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return false;
			}
		}
		return true;
	}

	public HashMap<String, String> getNewMap() {

		// for()
		// ret.put("portpassword", portpassword);
		// ret.put("port", port);
		// ret.put("invitecode", invitecode);
		// ret.put("email", email);
		// ret.put("timelimit", timelimit);
		// ret.put("account", account);
		// ret.put("volumn", volumn);
		generateNewMap();
		return this.retMap;
	}

	public boolean setInfos(HashMap<String, String> in) {
		Field[] fs = this.getClass().getFields();

		for (Field f : fs) {
			String setValue = in.get(f.getName());
			try {
				f.set(this, setValue);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return false;
			}
		}
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// UserInfo.getNameList().forEach(x->System.out.println(x));
		UserInfo u = new UserInfo();
		// System.out.println(u.generateNewMap());
		u.getNewMap().forEach((x, y) -> System.out.println(x + "|" + y));
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer ret = new StringBuffer();
		this.getNewMap().forEach((x, y) -> {
			ret.append(x);
			ret.append("|");
			ret.append(y);
			ret.append("\n");
		});

		return ret.toString();
	}
}
