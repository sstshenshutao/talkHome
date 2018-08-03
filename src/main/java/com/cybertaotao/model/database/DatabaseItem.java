package com.cybertaotao.model.database;

import java.util.Properties;

public class DatabaseItem {
	private Properties accountpwd;
	private Properties md5;
	private Properties userInfo;
	private String accountName;
	/**
	 * @param accountpwd
	 * @param md5
	 * @param userInfo
	 */
	public DatabaseItem(Properties accountpwd, Properties md5, Properties userInfo,String accountName) {
		super();
		this.accountName= accountName;
		this.accountpwd = accountpwd;
		this.md5 = md5;
		this.userInfo = userInfo;
	}
	
	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return the accountpwd
	 */
	public Properties getAccountpwd() {
		return accountpwd;
	}
	/**
	 * @return the md5
	 */
	public Properties getMd5() {
		return md5;
	}
	/**
	 * @return the userInfo
	 */
	public Properties getUserInfo() {
		return userInfo;
	}
	/**
	 * @param accountpwd the accountpwd to set
	 */
	public void setAccountpwd(Properties accountpwd) {
		this.accountpwd = accountpwd;
	}
	/**
	 * @param md5 the md5 to set
	 */
	public void setMd5(Properties md5) {
		this.md5 = md5;
	}
	/**
	 * @param userInfo the userInfo to set
	 */
	public void setUserInfo(Properties userInfo) {
		this.userInfo = userInfo;
	}
	
}
