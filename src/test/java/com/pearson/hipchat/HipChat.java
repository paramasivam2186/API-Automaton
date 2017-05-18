package com.pearson.hipchat;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonObject;

//import um.testng.test.utilities.framework.ConfigProp;
//import um.testng.test.utilities.framework.HipChatHelper;
//import um.testng.test.utilities.framework.enums.MessageFormat;

//import um.testng.test.utilities.framework.HTTPHelper;
//import um.testng.test.utilities.framework.enums.MessageFormat;
//import com.google.common.collect.ArrayListMultimap;
//import com.google.common.collect.Multimap;
public class HipChat {

	private String authKey;

	public HipChat(String authKey) {
		this.authKey = authKey;
	}
	public static void main(String[] args) {

//		String apiKey = ConfigProp.getPropertyValue("APIKey");
		HipChat hipchat = new HipChat("Q8RSfFXVMTGa8pVQ4drHVa3xCLq4FhXBTwMYXXmC");
		hipchat.messageUser("CSG_Automation_Testing_Status", true, hipchat_enum.TEXT);
	
	}
	
	public void messageUser(String message, boolean notify,
			hipchat_enum format) {		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "Bearer " + this.authKey);
		headers.put("Content-Type", "application/json");
		JsonObject msgObj = new JsonObject();
		msgObj.addProperty("message", message);
		msgObj.addProperty("notify", String.valueOf(notify));
		msgObj.addProperty("message_format", format.getValue());
		msgObj.addProperty("color", "green");
		
		String msgBody = msgObj.toString();
		
		System.out.println(msgBody);
		try {
			String endPoint = "http://api.hipchat.com/v2/room/CSG_Automation_Status/notification";
//			String[] arrayUsers = users.split(";");
//			for (String user : arrayUsers) {
//				String endPoint = "http://api.hipchat.com/v2/user/" + user + "/message";
			String responseString = httpHelper.sendPOST(endPoint, headers, msgBody);
			System.out.println(responseString);
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
