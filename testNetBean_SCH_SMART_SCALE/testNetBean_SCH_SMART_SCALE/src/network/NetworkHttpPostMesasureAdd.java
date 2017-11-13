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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.NameValuePair;

/**
 *
 * @author train
 */
public class NetworkHttpPostMesasureAdd extends NetworkHttpPostRunnable{
    
    String sessionId = "";
    public NetworkHttpPostMesasureAdd(String url, ArrayList<NameValuePair> params) {
        super(url, params);
        System.out.println("measure add : "+NetworkHandler.sessionID);
        hp.setHeader(ResourceValue.SESSION_ID, NetworkHandler.sessionID);
    }    
}
