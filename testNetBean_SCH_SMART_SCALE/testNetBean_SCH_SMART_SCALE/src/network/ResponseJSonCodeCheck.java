/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import managescale.JoinFrame;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author train
 */
public class ResponseJSonCodeCheck {
    public static String processing(String jsonStr){
        final String TAG = "ResponseJSonCodeCheck";
        String bodyString="";
        try {
            String dataStr = jsonStr;
            
            JSONObject object = new JSONObject(dataStr);
            JSONObject result = object.getJSONObject("result");
            String str = result.getString("code");
   
            System.out.println(TAG+":"+str);
            if(str.equals("200")){               
                if(!object.getString("body").equals("null")){
                    result = object.getJSONObject("body");                                                               
                    bodyString += result.toString();    
                }else
                    bodyString = "OK";
            }else{
                throw new ResponseException(result.getString("message"), Integer.parseInt(str));
            }
        }catch(ResponseException e){
            e.printStackTrace();
        }catch(JSONException e){
            
            e.printStackTrace();
            
               ///////////주환 수정
            JOptionPane.showMessageDialog(null,e.getMessage());
            JoinFrame.juhwan = false;
            //////////////////
        }
        
        return bodyString;
    }
}
