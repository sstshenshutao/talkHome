package com.cybertaotao.talkhome.filter;

public class HtmlFilter extends AbstractFilter<String> implements Filter<String> {

	@Override
	public String filter(String message) {
		// TODO Auto-generated method stub
		int i = 0;
		int r = 0;
		int k =0;
		while (i != -1) {
			i = message.indexOf("<", k);
			if (i >= 0) {
				r = message.indexOf(">", i);
				if (r != -1) {
					System.out.println("i"+i+"|r"+r+"|k"+k);
					if (!message.substring(i + 1, r).equals("br")) {
						return "Illegal label";
					}
				}
			}
			k=i+1;
		}
		return message;
	}
	public static void main(String[] args) {
		Filter<String> af = new HtmlFilter();
		af.appendFilter(new LengthFilter(5000));
		String a = "                  /88888888888888888888888888\\ <br>                  |88888888888888888888888888/<br>                   |~~____~~~~~~~~~\"\"\"\"\"\"\"\"\"|<br>                  / \\_________/\"\"\"\"\"\"\"\"\"\"\"\"\"\\<br>                 /  |              \\         \\<br>                /   |  88    88     \\         \\<br>               /    |  88    88      \\         \\<br>              /    /                  \\        |<br>             /     |   ________        \\       |<br>             \\     |   \\______/        /       |<br>  /\"\\         \\     \\____________     /        |<br>  | |__________\\_        |  |        /        /<br>/\"\"\"\"\\           \\_------'  '-------/       --<br>\\____/,___________\\                 -------/<br>------*            |                    \\<br>  ||               |                     \\<br>  ||               |                 ^    \\<br>  ||               |                | \\    \\<br>  ||               |                |  \\    \\<br>  ||               |                |   \\    \\<br>  \\|              /                /\"\"\"\\/    /<br>     -------------                |    |    /<br>     |\\--_                        \\____/___/<br>     |   |\\-_                       |<br>     |   |   \\_                     |<br>     |   |     \\                    |<br>     |   |      \\_                  |<br>     |   |        ----___           |<br>     |   |               \\----------|<br>     /   |                     |     ----------\"\"\\<br>/\"\\--\"--_|                     |               |  \\<br>|_______/                      \\______________/    )<br>                                              \\___/<br>";
		System.out.println(af.apply(a));
	}
}
