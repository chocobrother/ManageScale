/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uclab.ui;

import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import uclab.common.LogPrint;

/**
 *
 * @author train
 */
public class SerialCommunicationRunnable implements Runnable{
    private static InputStream inputStream = null;
    private static byte[] readBuffer = null;
    private static SerialPort chosenPort;
    private  BlockingQueue<Integer> blockingQueueEcg;
    private  BlockingQueue<Integer> bq_weight;
    
    private final int START_VALUE = 59;
    private final int END_VALUE = 60;
    
    private final int ECG_ID = 51;    
    private final int WEIGHT_ID = 70;    
    private final static int RES_VALUE = 81;
    
    private boolean flag = true;
    
    private boolean waitFlag = false;
      
    public SerialCommunicationRunnable(SerialPort chosenPort, BlockingQueue<Integer> blockingQueueEcg, BlockingQueue<Integer> bq_weight){        
        this.chosenPort = chosenPort;
        this.blockingQueueEcg = blockingQueueEcg;
        this.bq_weight = bq_weight;
        
        inputStream = chosenPort.getInputStream();           
    }
    
    public void setThreadWaitState(boolean flag){
        LogPrint.println("serialCommunication","Thread wait 됨");    
        waitFlag = flag;
        return ;
    }
    
    public boolean getThreadWaitState(){
        return waitFlag;
    }    
    
    public void threadNotify(){        
        synchronized(this){
            LogPrint.println("serialCommunication", "notifiy");
            this.notify();
        }
    }
    
    public static boolean getResponsePacket(){
        try {
            inputStream = chosenPort.getInputStream();           
            
            if(inputStream.available() > 0){
                readBuffer = new byte[inputStream.available()];
                inputStream.read(readBuffer);
                
                for(int n = 0; n < (readBuffer.length - 4); n++){                                        
                    if(readBuffer[n] == 59 && readBuffer[n+4] == 60){
                        if(readBuffer[n+1] == RES_VALUE){
                            return true;
                        }
                    }
                }                
            }
        } catch (IOException ex) {
            Logger.getLogger(SerialCommunicationRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void threadStop(){
        LogPrint.println("serialCommunication","시리얼 통신 종료");           
        
        this.flag = false;
    }
    
    @Override
    public void run() {        
        LogPrint.println("serialCommunication","시리얼 통신 스타트");           
        
        while(flag){
            try{                                        
                synchronized(this) {
                    if(waitFlag){
                        try {
                            this.wait();                            
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SerialCommunicationRunnable.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }                                       
                }
                if(inputStream.available() > 0){
//                    readBuffer = new byte[inputStream.available()];
                    readBuffer = new byte[5];
                    inputStream.read(readBuffer);
                                
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SerialCommunicationRunnable.class.getName()).log(Level.SEVERE, null, ex);
                    }                 

                    if(readBuffer.length > 4){
                           if(readBuffer[0] == 59 && readBuffer[4] == 60){
                               //id
                               int value = 100*readBuffer[2] + readBuffer[3];                   
                               try{
                                   if(readBuffer[1] == ECG_ID){
//                                       System.out.println("ecg:"+value);
                                       blockingQueueEcg.put(value);
                                   }else if(readBuffer[1] == WEIGHT_ID){
//                                       System.out.println("weight:"+value);
                                       bq_weight.put(value);
                                   }
                               }catch(InterruptedException e){
                                   e.printStackTrace();
                               }
                           }                  
                    }                           
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }      
        }
        
        blockingQueueEcg.clear();
        bq_weight.clear();
    }
}
