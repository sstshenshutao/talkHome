package com.cybertaotao.talkhome.messageContainer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;



public class FileMessageContainer implements MessageContainer {
	private ArrayList<String> messages;
	private String filename;
	public FileMessageContainer(String filename) {
		this.filename= filename;
		init();
	}
	private void init() {
		// TODO Auto-generated method stub
		File f = new File(filename);
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ArrayList<String> initial = new ArrayList<>();
			initial.add("initial finished!");
			this.saveAllMessage(initial);
		}
		System.out.println(f.getAbsolutePath());
	}
	@Override
	public ArrayList<String> getAllMessage() {
		// TODO Auto-generated method stub
		readfile(filename);
		return this.messages;
	}

	@Override
	public String getMessage(long index) {
		// TODO Auto-generated method stub
		readfile(filename);
		return this.messages.get((int)index);
	}

	@Override
	public boolean saveAllMessage(ArrayList<String> messages) {
		// TODO Auto-generated method stub
		return this.savefile(filename, messages);
	}

	@Override
	public boolean saveMessage(ArrayList<String> messages, long index) {
		// TODO Auto-generated method stub
		
		return true;
	}

	private void readfile(String filename) {
		this.messages= new ArrayList<>();
		FileReader fr;
		// int a=0;
		try {
			fr = new FileReader(filename);
			BufferedReader in = new BufferedReader(fr);
			String line;
			while ((line = in.readLine()) != null) {
				this.messages.add(line);
			}
			in.close();
			fr.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private boolean savefile(String filename, ArrayList<String> saveMessages) {
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(   
					new FileOutputStream(filename, true)));   
			saveMessages.forEach(x->{
				try {
					out.newLine();
					out.write(x);
				}catch (Exception e) {
					e.printStackTrace();
				}
			});
			out.close();
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
	}
}
