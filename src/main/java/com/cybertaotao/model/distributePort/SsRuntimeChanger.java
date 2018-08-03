package com.cybertaotao.model.distributePort;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SsRuntimeChanger {
	public boolean deletePorts(String port) {
		StringBuffer sb = new StringBuffer();
		sb.append("remove: {\"server_port\":").append(port).append("}");
		return sendOrder(sb.toString());
	}

	public boolean addPort(String port, String passwd) {
		StringBuffer sb = new StringBuffer();
		sb.append("add: {\"server_port\":").append(port).append(", \"password\":\"").append(passwd).append("\"}");
		return sendOrder(sb.toString());
	}

	public boolean getPorts() {
		return true;
	}

	private boolean sendOrder(String order) {
		boolean ret = false;
		String[] args = new String[] { "python", "./a.py", order };
		Process pr = null;
		BufferedReader in = null;
		try {
			pr = Runtime.getRuntime().exec(args);
			in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = in.readLine();
			if (line.equals("ok")) {
				ret = true;
			} else {
				ret = false;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			ret = false;
		} finally {
			try {
				in.close();
				pr.waitFor();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				ret = false;
			}

		}
		return ret;

	}

}
