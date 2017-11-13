/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import Common.ResourceValue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

/**
 *
 * @author train
 */
public class NetworkHttpGetRunnable implements Runnable{
    private String url;
    private ArrayList<NameValuePair> params;   
    private String result = "";
    
    public NetworkHttpGetRunnable(String url){  
        this.url = url;
    }
    
     public String getResult(){
        return result;
    }
    
    @Override
    public void run() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        HttpClient client = new DefaultHttpClient();
        HttpParams pa = client.getParams();
        HttpConnectionParams.setConnectionTimeout(pa, 5000);
       
        try{
            HttpGet hg = new HttpGet(url);
            
            hg.setHeader(ResourceValue.SESSION_ID, NetworkHandler.sessionID);
                    
           System.out.println("executing request " + hg.getURI());
            
            HttpResponse response = client.execute(hg);
            
            HttpEntity entity = response.getEntity();
            
            System.out.println("----------------------------------------");
	    // 응답 결과
	    System.out.println(response.getStatusLine());
		if (entity != null) {
		    System.out.println("Response content length: "
				+ entity.getContentLength());
		    BufferedReader rd = new BufferedReader(new InputStreamReader(
		                   response.getEntity().getContent()));
 
		    // line 에 조회 기록이 있어야 한다.
                    String line = "";
		    while ((line = rd.readLine()) != null) {
				System.out.println(line);
                                result += line;
				}
			}
			hg.abort();
			System.out.println("----------------------------------------");
			client.getConnectionManager().shutdown();
                            
        }catch(ClientProtocolException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            client.getConnectionManager().shutdown();
        }
        
    }
}
