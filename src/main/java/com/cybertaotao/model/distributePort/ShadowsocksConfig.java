package com.cybertaotao.model.distributePort;

public class ShadowsocksConfig {
	
	
	    private String server;
	    private Object port_password;
	    private int timeout;
	    private String method;
	    private boolean fast_open;
		/**
		 * @return the server
		 */
		public String getServer() {
			return server;
		}
		/**
		 * @return the port_password
		 */
		public Object getPort_password() {
			return port_password;
		}
		/**
		 * @return the timeout
		 */
		public int getTimeout() {
			return timeout;
		}
		/**
		 * @return the method
		 */
		public String getMethod() {
			return method;
		}
		/**
		 * @return the fast_open
		 */
		public boolean isFast_open() {
			return fast_open;
		}
		/**
		 * @param server the server to set
		 */
		public void setServer(String server) {
			this.server = server;
		}
		/**
		 * @param port_password the port_password to set
		 */
		public void setPort_password(Object port_password) {
			this.port_password = port_password;
		}
		/**
		 * @param timeout the timeout to set
		 */
		public void setTimeout(int timeout) {
			this.timeout = timeout;
		}
		/**
		 * @param method the method to set
		 */
		public void setMethod(String method) {
			this.method = method;
		}
		/**
		 * @param fast_open the fast_open to set
		 */
		public void setFast_open(boolean fast_open) {
			this.fast_open = fast_open;
		}


}
