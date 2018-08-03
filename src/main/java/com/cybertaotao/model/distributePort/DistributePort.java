package com.cybertaotao.model.distributePort;



import com.cybertaotao.model.database.DatabaseHandler;
import com.cybertaotao.model.iptables.Iptable;
import com.cybertaotao.repository.RandomPasswd;

public class DistributePort {
	
	private int portStart = 40000;
	private int portEnd = 50000;
	public DistributePort() {

	}
	public String[] manageOnePortPasswd(String passwd) {
		//
		String[] ret = new String[2];
		int port = getAvailablePort();
		ret[0]=String.valueOf(port);
		ret[1]=passwd;
		//manage json
		GJsoner jsoner = new GJsoner(new DatabaseHandler().getShadowsocksConfig());
		if (!jsoner.addPort(ret[0], ret[1])) {
			return null;
		}
		//manage socket
		SsRuntimeChanger sser = new SsRuntimeChanger();
		if (!sser.addPort(ret[0], ret[1])) {
			return null;
		}
		//clear volumn
		new Iptable().clearPortVolumn(port);
		//return 
		return ret;
	}
	public String[] manageOnePortPasswd() {
		//
		String[] ret = new String[2];
		int port = getAvailablePort();
		ret[0]=String.valueOf(port);
		ret[1]=getRandomPortPass();
		//manage json
		GJsoner jsoner = new GJsoner(new DatabaseHandler().getShadowsocksConfig());
		if (!jsoner.addPort(ret[0], ret[1])) {
			return null;
		}
		//manage socket
		SsRuntimeChanger sser = new SsRuntimeChanger();
		if (!sser.addPort(ret[0], ret[1])) {
			return null;
		}
		//clear volumn
		new Iptable().clearPortVolumn(port);
		//return 
		return ret;
	}
	
	private int getAvailablePort() {
		// read file and get the number
		DatabaseHandler db = new DatabaseHandler();
		int avPort =0;
		do {
			avPort= db.getCurrentAvailPort(portStart, portEnd);
		}while(avPort==0);
		
		return avPort;
	}

	private String getRandomPortPass() {
		return new RandomPasswd(15).getPasswd();
	}
//	private boolean refreshList(){
//		
//	}
//	private String readFiletoGetPort() {
//		try {
//			BufferedReader br = new BufferedReader(new FileReader(randomFile));
//			String ret = br.readLine();
//			
//
//
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
//	}
//	private List<Integer> generateRandomList() {
//		ArrayList<Integer> ori = new ArrayList<>();
//		for (int i = portStart; i < portEnd; i++) {
//			ori.add(i);
//		}
//		List<Integer> ret = new ArrayList<>();
//		while(ori.size() > 0) {
//			int r= new Random().nextInt(ori.size());
//			ret.add(ori.get(r));
//			ori.remove(r);
//		}
//		System.out.println(ret.size());
//		return ret;
//	}
//
//	public boolean generateRandomFile(String randomFile) {
//		try {
//			BufferedWriter bf = new BufferedWriter(new FileWriter(randomFile));
//			List<Integer> ret = generateRandomList();
//			StringBuffer sb = new StringBuffer();
//			for(Integer i :ret) {
//				sb.append(i);
//				sb.append("\n");
//			}
//			bf.write(sb.toString());
//			bf.flush();
//			bf.close();
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}

	public static void main(String[] args) {
		System.out.println(new DistributePort().manageOnePortPasswd());
	}
}
