package com.cybertaotao.talkhome.filter;

public class LengthFilter extends AbstractFilter<String> implements Filter<String> {
	private int len=5000;
	public LengthFilter() {

	}

	/**
	 * @param length  not greater than length
	 */
	public LengthFilter(int length) {
		this.len= length;
	}

	@Override
	public String filter(String message) {
		// TODO Auto-generated method stub
		if (message.length() > len) {
			return "";
		}
		return message;
	}

}
