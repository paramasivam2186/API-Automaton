package com.pearson.hipchat;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import com.google.gson.JsonObject;
public class httpHelper {
	private final static String USER_AGENT = "Mozilla/5.0";
	public static String sendPOST(String endPoint, Map<String,String> headers, String urlParameters) throws IOException {

		URL obj;
		obj = new URL(endPoint);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("POST");
		con.setConnectTimeout(5000);
		con.setDoOutput(true);

		// add request header
//		con.setRequestProperty("User-Agent", USER_AGENT);
		
		for (Entry<String, String> header : headers.entrySet()) {
			con.setRequestProperty(header.getKey(),header.getValue());	
		}
		
		//send data
		try( DataOutputStream wr = new DataOutputStream( con.getOutputStream())) {
			 wr.writeBytes(urlParameters);
		     wr.flush();
		     wr.close();

			}

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + endPoint);
		System.out.println("Response Code : " + responseCode);

		String responseString = "";
		InputStream stream;
		if(responseCode == 200){
			stream = con.getInputStream();		
		}else{
			stream = con.getErrorStream();			
		}
		if(stream != null){
		BufferedReader in = new BufferedReader(new InputStreamReader(stream));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		responseString = response.toString();
		}		
		con.disconnect();
		return responseString;

	}
}
