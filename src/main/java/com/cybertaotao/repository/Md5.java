package com.cybertaotao.repository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
	public static String getMd5(String a) {
		String ret = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(a.getBytes());
			byte tmp[] = md5.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			ret = new String(str);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	public static String pojie(String a ) {
		return getMd5(getMd5(getMd5(a).toLowerCase()).toLowerCase());
	}
	public static void main(String[] args) {
		System.out.println(pojie("zblt"));
		System.out.println(pojie("ZBLT"));
		//6F125DBE0E6D17F6A1F7A81555D491E1
	}
}
