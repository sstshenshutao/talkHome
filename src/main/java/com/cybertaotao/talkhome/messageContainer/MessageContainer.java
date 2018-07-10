package com.cybertaotao.talkhome.messageContainer;

import java.util.ArrayList;

public interface MessageContainer {
	public ArrayList<String> getAllMessage();
	public String getMessage(long index);
	public boolean saveAllMessage(ArrayList<String> messages);
	public boolean saveMessage(ArrayList<String> messages, long index);
}
