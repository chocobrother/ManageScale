/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import Common.ResourceValue;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.Header;
import org.apache.http.NameValuePair;

/**
 *
 * @author train
 */
public class NetworkHttpPostLogin extends NetworkHttpPostRunnable{
    
    public NetworkHttpPostLogin(String url, ArrayList<NameValuePair> params) {
        super(url, params);
    }
    
    public void saveSessionID(){
        Header []array = responsePOST.getHeaders(ResourceValue.SESSION_ID); 
        
        System.out.println("str : "+array[0].getValue());
        NetworkHandler.sessionID =  array[0].getValue();
    }
}
