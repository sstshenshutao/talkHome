package com.cybertaotao.model.distributePort;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

public class GJsoner {
	private String shadowsocksConfig;
	private LinkedTreeMap<String, String> tm;
	private Gson gson = new Gson();
	private ShadowsocksConfig config;

	public GJsoner(String filePath) {
		this.shadowsocksConfig = filePath;
	}

	@SuppressWarnings("unchecked")
	public boolean parseJson() {
		try {
			this.config = gson.fromJson(new FileReader(shadowsocksConfig), ShadowsocksConfig.class);
			// System.out.println(this.config);
			this.config.getPort_password();
			this.tm = ((LinkedTreeMap<String, String>) this.config.getPort_password());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;

	}

	/** dont need to care about if the key is existed, existed then change it;
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean addPort(String key, String value) {
		if (parseJson()) {
			this.tm.put(key, value);
			try {
				BufferedWriter bf = new BufferedWriter(new FileWriter(this.shadowsocksConfig));
				bf.write(gson.toJson(this.config));
				bf.flush();
				bf.close();
			} catch (Exception e) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	public HashMap<String, String> getPorts() {

		HashMap<String, String> ret = null;
		if (parseJson()) {
			ret = new HashMap<>();
			Iterator<String> it = tm.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = (String) tm.get(key);
				ret.put(key, value);
			}
		}
		return ret;
	}
	
	/** dont need to care about if the port is existed, no existed return also true
	 * @param port
	 * @return
	 */
	public boolean deletePorts(String port) {
		if (parseJson()) {
			this.tm.remove(port);
			try {
				BufferedWriter bf = new BufferedWriter(new FileWriter(this.shadowsocksConfig));
				bf.write(gson.toJson(this.config));
				bf.flush();
				bf.close();
			} catch (Exception e) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		
	}
}
