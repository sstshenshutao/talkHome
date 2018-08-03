package com.cybertaotao.repository;

import com.cybertaotao.model.database.DatabaseHandler;

public class Identidy {
	private DatabaseHandler db = new DatabaseHandler();

	public boolean checkPasswd(String account, String passwd) {
		if (!this.db.searchUserItem(account)) return false;
		String correctpswd = this.db.getAccpasswd();
		return (correctpswd != "") && passwd.equals(correctpswd);
	}
	public String checkMd5(String md5) {
		return this.db.getMd5User(md5);
	}
}
