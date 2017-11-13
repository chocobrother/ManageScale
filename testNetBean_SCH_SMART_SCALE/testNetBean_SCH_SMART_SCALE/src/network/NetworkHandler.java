/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import Common.ResourceValue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author train
 */
public class NetworkHandler {
    static final String TAG = "NetworkHandler";
            
    public static String sessionID;
    
    public static String join(String userId, String pwd, String name, String phone, String nickName, String sex, String birth, String weight, String height){
        ArrayList<NameValuePair> params = new ArrayList<>();
        
        params.add(new BasicNameValuePair("userId", userId));
        params.add(new BasicNameValuePair("password", pwd));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("phone", phone));
        params.add(new BasicNameValuePair("nickName", nickName));
        params.add(new BasicNameValuePair("sex", sex));
        params.add(new BasicNameValuePair("birth", birth));
        params.add(new BasicNameValuePair("weight", weight));
        params.add(new BasicNameValuePair("height", height));
        
        NetworkHttpPostRunnable network = new NetworkHttpPostRunnable("http://welltec.blue-core.com:10000/openapi/member/joinByClient", params);        
        Thread thread = new Thread(network);
        thread.start();  
        
        String resultStr = "";           
        try{
            thread.join();
            resultStr = ResponseJSonCodeCheck.processing(network.getResult());
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        
        if(resultStr.equals("OK")){
            return "OK";
        }       
        return "NO";       
    }
    
    public static String login(String id, String pwd){
        ArrayList<NameValuePair> params = new ArrayList<>();     
//        params.add(new BasicNameValuePair("userId", "ngcps2@naver.com"));
//        params.add(new BasicNameValuePair("password", "rlawls123!"));
        params.add(new BasicNameValuePair("userId", id));
        params.add(new BasicNameValuePair("password", pwd));
        NetworkHttpPostRunnable network = new NetworkHttpPostLogin("http://welltec.blue-core.com:10000/openapi/member/login", params);
        Thread thread = new Thread(network);
        thread.start();
        
        String resultStr = "";           
        try{
            thread.join();
            network.saveSessionID();
            resultStr = ResponseJSonCodeCheck.processing(network.getResult());
        }catch(InterruptedException e){
            e.printStackTrace();
        }        
      
        return resultStr;
    }
    
    
    public static String Height(){
  
         NetworkHttpGetRunnable network = new NetworkHttpGetRunnable("http://welltec.blue-core.com:10000/openapi/member/memberInfo");
        Thread thread = new Thread(network);
        thread.start();
        
        String resultStr = "";        
        try{
            thread.join();
              resultStr = ResponseJSonCodeCheck.processing(network.getResult());
        }catch(InterruptedException e){
            e.printStackTrace();
        }
               
       return resultStr;
        
    }
    
    
    
    
    
    public static String id_check(String id){
        ArrayList<NameValuePair> params = new ArrayList<>();     
        params.add(new BasicNameValuePair("userId", id));
        NetworkHttpPostRunnable network = new NetworkHttpPostRunnable("http://welltec.blue-core.com:10000/openapi/member/checkId", params);
        Thread thread = new Thread(network);
        thread.start();
        
        String resultStr = "";           
        try{
            thread.join();
            resultStr = ResponseJSonCodeCheck.processing(network.getResult());
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        
        if(resultStr.equals("OK")){
            return "OK";
        }       
        return "NO";
    }
    
    public static String nickName_check(String nickname){
        ArrayList<NameValuePair> params = new ArrayList<>();     
        params.add(new BasicNameValuePair("userNickName", nickname));
        NetworkHttpPostRunnable network = new NetworkHttpPostRunnable("http://welltec.blue-core.com:10000/openapi/member/checkNickName", params);
        Thread thread = new Thread(network);
        thread.start();
        
        String resultStr = "";        
        try{
            thread.join();
            resultStr = ResponseJSonCodeCheck.processing(network.getResult());
        }catch(InterruptedException e){
            e.printStackTrace();
        }
               
        if(resultStr.equals("OK")){
            return "OK";
        }       
        return "NO";
    }
    
    // 주환 수정 시작 
      public static String search_check(){          
        NetworkHttpGetRunnable network = new NetworkHttpGetRunnable("http://welltec.blue-core.com:10000/openapi/p2/weight");
        Thread thread = new Thread(network);
        thread.start();
        
        String resultStr = "";        
        try{
            thread.join();
              resultStr = ResponseJSonCodeCheck.processing(network.getResult());
        }catch(InterruptedException e){
            e.printStackTrace();
        }
               
       return resultStr;
    }
    //주환 수정 끝 
    public static String dataAdd(String afresult, String weight, String simbaksu){
        ArrayList<NameValuePair> params = new ArrayList<>();     
        long time = System.currentTimeMillis(); 

	SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm");

	String str = dayTime.format(new Date(time));
	String[] arr = str.split("-|:| "); str = "";        
        for(int n =0; n < arr.length; n++){
            str += arr[n];
        }
        params.add(new BasicNameValuePair("wDate", str));
        params.add(new BasicNameValuePair("weight", weight));
        
//        params.add(new BasicNameValuePair("bmi", ""));
        params.add(new BasicNameValuePair("fatMass", simbaksu));
//        params.add(new BasicNameValuePair("fatPer", ""));
        params.add(new BasicNameValuePair("arrhythmia", afresult));
        
        NetworkHttpPostRunnable network = new NetworkHttpPostMesasureAdd("http://welltec.blue-core.com:10000/openapi/p2/weight", params);
        Thread thread = new Thread(network);
        thread.start();
        
        String resultStr = "";        
        try{
            thread.join();
            resultStr = ResponseJSonCodeCheck.processing(network.getResult());
        }catch(InterruptedException e){
            e.printStackTrace();
        }
               
        if(resultStr.equals("OK")){
            return "OK";
        }       
        return "NO";
    }
}
