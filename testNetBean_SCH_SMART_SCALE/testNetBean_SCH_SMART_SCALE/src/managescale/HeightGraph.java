/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managescale;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.WindowConstants;
import static managescale.SearchFrame.Ecompare;
import static managescale.SearchFrame.End;
import static managescale.SearchFrame.Same;
import static managescale.SearchFrame.Scompare;
import static managescale.SearchFrame.Start;
import network.NetworkHandler;
import org.apache.http.ParseException;
import org.jfree.chart.ChartFactory;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.jfree.ui.TextAnchor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author Juhwan
 */
public class HeightGraph extends javax.swing.JFrame {
          public static String Start;
        
        public static String End;
        
        Date date1;
        
        Date date2;
        
        Date ConvertStart;
           
        Date ConvertEnd; 
        
        public static int Scompare;
                      
        public static int Ecompare;
                        
        public static int Same;
        
        public static String s[];
        
        public static int h[];
        
        public static int w[];
        
        public static int simbak1;
        
        public static DefaultCategoryDataset dataset;
        
        public static boolean flag = true;
    /**
     * Creates new form Graph
     */
    public HeightGraph(String title) {
        super(title);
        initComponents();
 
          Calendar calendar = Calendar.getInstance();
        Date curDate = (Date)calendar.getTime();
        calendar.add(Calendar.YEAR, -50);
        Date minDate = (Date)calendar.getTime();
        calendar.add(Calendar.YEAR, 100);
        Date maxDate = (Date)calendar.getTime();
        SpinnerDateModel datemodel = new SpinnerDateModel(curDate,minDate,maxDate,Calendar.YEAR);
       
        jSpinner1.setModel(datemodel);
        jSpinner1.setEditor(new JSpinner.DateEditor(jSpinner1,"yyyy년MM월dd일"));
        
        
        SpinnerDateModel datemodel1 = new SpinnerDateModel(curDate,minDate,maxDate,Calendar.YEAR);
        jSpinner2.setModel(datemodel1);
        jSpinner2.setEditor(new JSpinner.DateEditor(jSpinner1,"yyyy년MM월dd일"));
        

        
        ////////////////////////////////////////////////////
        
        
//        DefaultCategoryDataset dataset = createDataset();
//        
//    
//
//        JFreeChart lineChart = ChartFactory.createLineChart("Weinght Change","Month","Weight",dataset);
//        
//        ChartPanel chartPanel = new ChartPanel( lineChart );
//      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
////      setContentPane( chartPanel );
//        
//        PanelChart.setLayout(new java.awt.BorderLayout());
//        PanelChart.add(chartPanel,BorderLayout.CENTER);
//        PanelChart.validate();
      
    }
    
     private DefaultCategoryDataset createDataset()
   {
       
      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
      
      String series1 = "kg";
      
      String series2 = "HeartRate";
      
      
      for(int i = 0; i< w.length; i++){
          
          for(int j = 0;j<s.length;j++){
              
              if(s[j]!=null && w[i]!=0){
                  
              dataset.addValue( w[j] , series1 , s[j] );
              
              dataset.addValue( h[j] , series2, s[j]);
              
              }
          }
      }
      
//      dataset.addValue( weight , "kg" , date );
      

      return dataset;
   }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        PanelChart = new java.awt.Panel();
        jSpinner1 = new javax.swing.JSpinner();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jSpinner2 = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.setFont(new java.awt.Font("굴림", 0, 24)); // NOI18N
        jTextField1.setText("       몸무게 변화");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        PanelChart.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout PanelChartLayout = new javax.swing.GroupLayout(PanelChart);
        PanelChart.setLayout(PanelChartLayout);
        PanelChartLayout.setHorizontalGroup(
            PanelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 525, Short.MAX_VALUE)
        );
        PanelChartLayout.setVerticalGroup(
            PanelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 346, Short.MAX_VALUE)
        );

        jTextField2.setText("~~");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton1.setText("조 회");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(161, 161, 161)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(23, 23, 23)
                            .addComponent(PanelChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton1))
                                .addGap(0, 12, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jSpinner1))))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinner2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
          String sp1 = jSpinner1.getValue().toString();
         
       System.out.println("sp : " + sp1);
      
       String sp2 = jSpinner2.getValue().toString();
       
        System.out.println("s2p : " + sp2);
       
       SimpleDateFormat f1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",Locale.ENGLISH);
       
       SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd",Locale.KOREA);     
       
      
       
       try{
           
            date1 = f1.parse(sp1);
            date2 = f1.parse(sp2);
           
           Start = f2.format(date1);
           
           End = f2.format(date2);
           
           SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
           
           ConvertStart = transFormat.parse(Start);
           
           ConvertEnd = transFormat.parse(End);

       }catch(ParseException e){
           e.printStackTrace();
       } catch (java.text.ParseException ex) {
            Logger.getLogger(SearchFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
 
       String result = NetworkHandler.search_check(); 
        
        
         try{
            JSONObject json = new JSONObject(result);

            JSONArray jArr = json.getJSONArray("wList");
            
            System.out.println("JARRRRRR : " + jArr);
            
            String[] name = {"createDate","weight"};

           int size = jArr.length();
           
           s = new String[size];
                       
           h = new int[size];
           
           w = new int[size];
           
            for(int i = 0; i< jArr.length(); i++){
                 
                json = jArr.getJSONObject(i); 

                String createDate = json.getString("createDate").toString();
               
                String weight = json.getString("weight").toString();

                String subdate = createDate.substring(0,10);
                
                String simbak = json.getString("fatMass").toString();
                
                System.out.println("그래프 심박수 : " + simbak);
//                s[i] = subdate;
// 
                int weight1 = Integer.parseInt(weight);
                
                if(simbak.equals("null")){
                    simbak1 = 0;
                }else{
                    simbak1 = Integer.parseInt(simbak);
                }
//                
//                w[i] = weight1;
                
//               dataset = createDataset(w[i],s[i]);
              
   
                SimpleDateFormat f3 = new SimpleDateFormat("yyyy-MM-dd",Locale.KOREA);      
      
                
                try{
           
                      Date ddd = f3.parse(createDate);
                      System.out.println(" cssss : " + ConvertStart + "//////////" + ConvertEnd + " dddd" + ddd );
                     
                       Scompare = ConvertStart.compareTo(ddd);
                        System.out.println("ccccccccccc : " + Scompare);
                       Ecompare = ConvertEnd.compareTo(ddd);
                        System.out.println("ssssssssss: " + Ecompare);
                       Same = ConvertStart.compareTo(ConvertEnd);
                      
                      System.out.println("Start ::" + ConvertStart + "End : " + ConvertEnd + " Subdate : " + ddd);
                      
                     if( Scompare <= 0 && Ecompare >=0 ){
                            System.out.println(" correct");
                         s[i] = subdate;
 
                         h[i] = simbak1;
                         
                         w[i] = weight1;

                     } else if (Same == 0 && Scompare == 0 && Ecompare == 0){
                          s[i] = subdate;
 
                          h[i] = simbak1;
                          
                          w[i] = weight1;
                           
                     } else if(Same > 0){
                           
                         
                     } 
                        
                    }catch(ParseException e){
                        e.printStackTrace();
                    } catch (java.text.ParseException ex) {
                         Logger.getLogger(SearchFrame.class.getName()).log(Level.SEVERE, null, ex);
                     }

            }

            
       }catch(JSONException e){
           e.printStackTrace();

       }
                  
                for(int i = 0; i < w.length; i++){
                    System.out.println("w : " + w[i]);
                }
         
                  for(int j = 0; j < s.length; j++){
                    System.out.println("s : " + s[j]);
                }
         
         
                JFreeChart lineChart = ChartFactory.createLineChart("Weight Change","Month","Weight",createDataset(),
         PlotOrientation.VERTICAL,
         true,true,false);
        
                ChartPanel chartPanel = new ChartPanel( lineChart );
            
                chartPanel.setPreferredSize( new java.awt.Dimension( 1000 , 367 ) );

                ChartPanel panel = new ChartPanel(lineChart);
//                  setContentPane(panel);
//              JScrollPane jScrollPane = new JScrollPane(PanelChart);
//                jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                PanelChart.setLayout(new java.awt.BorderLayout());
                PanelChart.add(chartPanel,BorderLayout.CENTER);
                PanelChart.validate();
         
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
           
       

       
      
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                HeightGraph chart = new HeightGraph("change");
                chart.pack( );
                //chart.setSize(600, 400);
                RefineryUtilities.centerFrameOnScreen( chart );
                chart.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                chart.setVisible( true );
            }
        });
    }
    
 



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Panel PanelChart;
    private javax.swing.JButton jButton1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
