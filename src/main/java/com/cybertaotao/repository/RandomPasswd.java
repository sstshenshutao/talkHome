package com.cybertaotao.repository;

import java.util.Random;

public class RandomPasswd {
	private String passwd = null;

	public RandomPasswd(int passwdLength) {
		init(passwdLength,null);
	}
	public RandomPasswd(int passwdLength, char[] fuhao) {
		init(passwdLength, fuhao);
	}
	private void init(int length, char[] fuhao) {
		// TODO Auto-generated method stub
		byte[] passwd = new byte[length];
		
		Random rd = new Random();
		for (int i = 0 ; i<length;i++) {
			int rnum = fuhao==null? rd.nextInt(2):rd.nextInt(3);
			passwd[i]= (rnum==0)?(byte)(rd.nextInt(10)+48) :
				(rnum==1)? (byte)(rd.nextInt(26)+65) : (byte)fuhao[rd.nextInt(fuhao.length)];
		}
		this.passwd= new String(passwd); 
	}
	
	/**
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}
	public static void main(String[] args) {
		RandomPasswd rpd = new RandomPasswd(15);
		System.out.println(rpd.getPasswd());
	}
	
}
