/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uclab.ui;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import uclab.algorithms.ECG_SignalRunnable;
import uclab.common.LogPrint;

/**
 *
 * @author train
 */
public class UiUpdateRunnable implements Runnable{
    JFrame window;
    int x;
    ECG_SignalRunnable ecgSignal;
    XYSeries series;
    ValueAxis range;
    
    JLabel sqiLabel, weightLabel;
    private XYLineAndShapeRenderer renderer;
    private ImageIcon badSignal;
    private ImageIcon goodSignal;
    FileWriter ecgTextFile;   
    
    private BlockingQueue<Integer> blockingQueueEcg;
    private BlockingQueue<Integer> bq_weight;
    
    private int[] autoScale;
    private int scaleCurrentIdx;
    
    private boolean flag = true;    
    public boolean stop_add_ecg = false;
    public boolean flag2 = true;
    
    public static String weightText = "0";
                
    public UiUpdateRunnable(JFrame window, XYSeries series, ValueAxis range, JLabel sqiLabel, JLabel weightLabel, XYLineAndShapeRenderer renderer, 
            ECG_SignalRunnable ecgSignal, BlockingQueue<Integer> blockingQueueEcg, BlockingQueue<Integer> bq_weight){
        
        //set Image size 
        try{
            BufferedImage img = ImageIO.read(new File("./res/thumbs_down.png"));
            Image dimg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            badSignal = new ImageIcon(dimg);
          
            img = ImageIO.read(new File("./res/thumbs_up.png"));
            dimg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            goodSignal = new ImageIcon(dimg);
            
        }catch(IOException e){
            e.printStackTrace();
        }
        
        this.window= window;
        this.ecgSignal = ecgSignal;
        this.series = series;
        this.sqiLabel = sqiLabel;
        this.weightLabel = weightLabel;
        this.renderer = renderer;
        this.blockingQueueEcg = blockingQueueEcg;
        this.bq_weight = bq_weight;
        this.x = 0;
        this.range = range;
        this.autoScale = new int[500];  /// length가 2초 정도의 데이터가 저장될 수 있을 정도의 사이즈
        this.scaleCurrentIdx = 0;
        this.stop_add_ecg = false;
        flag2 = true;
    }

    public void threadStop(){
        System.out.println("UiUpdateThread 시리얼 통신 종료");
        
        this.flag = false;
    }
        
    @Override
    public void run() {            
         //체중과 ecg로 나눠서 그리도록
         
         while (flag) {
            try {
                Thread.sleep(5);
                
                if(ecgSignal.getCleanSignalFlag()){
                        renderer.setSeriesPaint(0, Color.GREEN);
                        sqiLabel.setText("Good Signal");
                        sqiLabel.setIcon(goodSignal);
                }else{
                    sqiLabel.setText("");
//                        renderer.setSeriesPaint(0, Color.RED);
//                        sqiLabel.setText("Bad Signal");
//                        sqiLabel.setIcon(badSignal);
                }
                    
                //range 구하기
                if(scaleCurrentIdx == autoScale.length){
                    //min
                    flag2 = false;
                    int minY = autoScale[0];
                    int maxY = autoScale[0];
                    
                   for(int n = 0; n < autoScale.length; n++){
                       if(minY > autoScale[n])
                           minY = autoScale[n];
                       
                       if(maxY < autoScale[n])
                           maxY = autoScale[n];
                   }
                   if(ecgSignal.getCleanSignalFlag())
                      range.setRange(minY-1, maxY+1);
                   scaleCurrentIdx = 0;
                }
                
                //queue가 채워져 않으면
                if(!blockingQueueEcg.isEmpty() && !stop_add_ecg){
                    double value = (double)blockingQueueEcg.take();
                    value =  value * 5 / 1023;
                    ecgSignal.setSignalAdd(value);
//series.remove(x)
                    //clean Signal일 경우 심전도를 그리기
                    if(ecgSignal.getCleanSignalFlag())
                        series.add(x++, value);    
                    autoScale[scaleCurrentIdx++] = (int)value;
                }
                
                if(!bq_weight.isEmpty()){
                    //체중표시 
                    int weightValue = bq_weight.take();
                    weightLabel.setText(weightValue+"KG");
                    weightText = weightValue+"";
                }
            } catch (NumberFormatException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }   
}
