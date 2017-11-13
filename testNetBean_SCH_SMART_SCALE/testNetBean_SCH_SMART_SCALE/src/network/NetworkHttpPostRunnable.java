/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import Common.ResourceValue;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

/**
 *
 * @author train
 */
public class NetworkHttpPostRunnable implements Runnable{
    private String url;
    private ArrayList<NameValuePair> params;   
    private String result = "";
                
    protected HttpPost hp;  
    protected HttpResponse responsePOST;
    
    public NetworkHttpPostRunnable(String url, ArrayList<NameValuePair> params){
        //일단 파라미터 길이로 판단
        this.url = url;
        this.params = params;
        this.hp = new HttpPost(url);
        this.hp.setHeader(ResourceValue.POC_ID, ResourceValue.POC_ID_VALUE);
        this.hp.setHeader("user-id", "ngcps2@naver.com");
    }
    
    public String getResult(){
        return result;
    }    
   
    public void saveSessionID(){}

    @Override
    public void run() {
        HttpClient client = new DefaultHttpClient();
        HttpParams pa = client.getParams();
        HttpConnectionParams.setConnectionTimeout(pa, 5000);

        try {                       
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            hp.setEntity(ent);

            responsePOST = client.execute(hp);
            HttpEntity resEntity = responsePOST.getEntity();
                         
            InputStream inputstream = resEntity.getContent();
            Scanner scan = new Scanner(inputstream);
            
            while(scan.hasNext()){
                result += scan.nextLine();
            }
            
            inputstream.close();
            scan.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
