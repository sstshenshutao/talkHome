package com.cybertaotao.talkhome.filter;

public class AlphabetFilter extends AbstractFilter<String> implements Filter<String> {

	@Override
	public String filter(String message) {
		// TODO Auto-generated method stub
		for(char b: message.toCharArray()) {
			if (((int)b)<48 || ((int)b)>122 || (((int)b)>57 && ((int)b)<65) || (((int)b)>90 && ((int)b)<97)){
				return "";
			}
		}
		return message;
//		65-90 48-57 97-122
	}
	public static void main(String[] args) {
		AlphabetFilter a = new AlphabetFilter();
		a.appendFilter(new LengthFilter(16));
		System.out.println(a.filter("test"));
	}
	
}
