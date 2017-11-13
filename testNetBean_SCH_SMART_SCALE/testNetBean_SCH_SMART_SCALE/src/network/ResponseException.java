/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

/**
 *
 * @author train
 */
public class ResponseException extends Exception{
    private final int ERR_CODE;
  
    public ResponseException(String msg, int errCode){
        super(msg);
        ERR_CODE = errCode;
    }
    
    public int getErrCode(){
        return ERR_CODE;
    }
}
