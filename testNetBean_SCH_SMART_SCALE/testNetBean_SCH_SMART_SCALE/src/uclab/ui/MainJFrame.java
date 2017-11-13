/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uclab.ui;

import Common.ResourceValue;
import com.fazecast.jSerialComm.SerialPort;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import java.util.concurrent.LinkedBlockingQueue;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import uclab.algorithms.ECG_SignalRunnable;
import uclab.common.LogPrint;

public class MainJFrame extends javax.swing.JFrame implements ActionListener{

    private XYSeries series;
    private JFreeChart chart;
    private XYLineAndShapeRenderer graphRenderer;
    private ValueAxis graphRange;
    
    private ECG_SignalRunnable ecgSignalRunnable;
    private ElapseTimeRunnable elapseTimeRunnable;
    private UiUpdateRunnable  uiUpdateRunnable;
    private SerialCommunicationRunnable serialCommRunnable;
             
    private Thread ecgThread, elapseThread, uiUpdateThread, serialCommThread;    
    
    private SerialPort chosenPort = null;
   
    private BlockingQueue<Integer> blockingQueueEcg;
    private BlockingQueue<Integer> blocingQueueWeight;    
    
    enum ButtonState {START, PAUSE, STOP, RESTART};
    private ButtonState startButtonState;   
    
    private SerialPort[] arraySerailPort;

    public MainJFrame() {
        initComponents();
   
        blockingQueueEcg =  new LinkedBlockingQueue<Integer>(1024);     
        blocingQueueWeight = new LinkedBlockingQueue<Integer>(10);        
        
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
             System.out.println("mainframe closing");

            if(uiUpdateThread != null){
                uiUpdateRunnable.threadStop();
                serialCommRunnable.threadStop();
                elapseTimeRunnable.threadStop();
                ecgSignalRunnable.threadStop();        
            }
            if(chosenPort != null)
                chosenPort.closePort();    
            e.getWindow().dispose();
            }         
        });
    }            
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        munuBar = new javax.swing.JMenuItem();
        mainPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        sqiLabel = new javax.swing.JLabel();
        measurePanel = new javax.swing.JPanel();
        elapseTimeLabel = new javax.swing.JLabel();
        weightLabel = new javax.swing.JLabel();
        heartRateLabel = new javax.swing.JLabel();
        buttonPanel = new javax.swing.JPanel();
        button = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        graphPanel = new javax.swing.JPanel();
        readyLabel = new javax.swing.JLabel();
        afDiagnosisLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        toolMenu = new javax.swing.JMenu();
        portNameList = new javax.swing.JMenu();

        munuBar.setBackground(new java.awt.Color(255, 255, 255));
        munuBar.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setPreferredSize(new java.awt.Dimension(1350, 1000));

        titleLabel.setBackground(new java.awt.Color(255, 255, 255));
        titleLabel.setFont(new java.awt.Font("210 맨발의청춘 B", 0, 30)); // NOI18N
        try{
            BufferedImage img = ImageIO.read(new File(ResourceValue.PC_ICON_SRC));
            Image dimg = img.getScaledInstance(200, 180, Image.SCALE_SMOOTH);
            ImageIcon titleImage = new ImageIcon(dimg);
            titleLabel.setIcon(titleImage);
        }catch(IOException e){

        }
        titleLabel.setText("Smart Scale for Detection Arrhythmia");

        sqiLabel.setFont(new java.awt.Font("굴림", 0, 30)); // NOI18N
        sqiLabel.setIcon(null);
        sqiLabel.setText("Good SIGNAL");
        sqiLabel.hide();

        measurePanel.setBackground(new java.awt.Color(255, 255, 255));

        elapseTimeLabel.setFont(new java.awt.Font("210 맨발의청춘 B", 0, 30)); // NOI18N
        elapseTimeLabel.setIcon(new javax.swing.ImageIcon("./res/elapsetime_image.png")); // NOI18N
        elapseTimeLabel.setText("0s");

        weightLabel.setFont(new java.awt.Font("210 맨발의청춘 B", 0, 30)); // NOI18N
        weightLabel.setIcon(new javax.swing.ImageIcon("./res/weightimage.png")); // NOI18N
        weightLabel.setText("0KG");

        heartRateLabel.setFont(new java.awt.Font("210 맨발의청춘 B", 0, 30)); // NOI18N
        heartRateLabel.setIcon(new javax.swing.ImageIcon("./res/heartrate_image.png")); // NOI18N
        heartRateLabel.setText("bpm");

        buttonPanel.setBackground(new java.awt.Color(255, 255, 255));

        button.setBackground(new java.awt.Color(255, 255, 255));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setFont(new java.awt.Font("210 맨발의청춘 B", 0, 30)); // NOI18N
        try{
            BufferedImage img = ImageIO.read(new File("./res/button_1.png"));
            Image dimg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            ImageIcon startImage = new ImageIcon(dimg);
            button.setIcon(startImage);
        }catch(IOException e){

        }
        button.setBorderPainted(false);
        button.setText(" START");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });

        jPanel4.setVisible(false);
        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setBorder(null);
        try{
            BufferedImage img = ImageIO.read(new File("./res/pause.png"));
            Image dimg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            ImageIcon startImage = new ImageIcon(dimg);
            jButton3.setIcon(startImage);
        }catch(IOException e){

        }
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        try{
            BufferedImage img = ImageIO.read(new File("./res/stop.png"));
            Image dimg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            ImageIcon startImage = new ImageIcon(dimg);
            jButton4.setIcon(startImage);
        }catch(IOException e){

        }
        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setBorder(null);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 43, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(button, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout measurePanelLayout = new javax.swing.GroupLayout(measurePanel);
        measurePanel.setLayout(measurePanelLayout);
        measurePanelLayout.setHorizontalGroup(
            measurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(measurePanelLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(measurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(measurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(elapseTimeLabel)
                        .addComponent(weightLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(measurePanelLayout.createSequentialGroup()
                        .addComponent(heartRateLabel)
                        .addContainerGap())))
            .addGroup(measurePanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        measurePanelLayout.setVerticalGroup(
            measurePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(measurePanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addComponent(weightLabel)
                .addGap(55, 55, 55)
                .addComponent(heartRateLabel)
                .addGap(55, 55, 55)
                .addComponent(elapseTimeLabel)
                .addContainerGap())
        );

        graphPanel.setBackground(new java.awt.Color(255, 255, 255));

        readyLabel.setIcon(new javax.swing.ImageIcon("./res/Ready.png")); // NOI18N

        javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphPanel);
        graphPanel.setLayout(graphPanelLayout);
        graphPanelLayout.setHorizontalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, graphPanelLayout.createSequentialGroup()
                .addContainerGap(85, Short.MAX_VALUE)
                .addComponent(readyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 758, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        graphPanelLayout.setVerticalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, graphPanelLayout.createSequentialGroup()
                .addContainerGap(98, Short.MAX_VALUE)
                .addComponent(readyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        afDiagnosisLabel.setVisible(false);
        afDiagnosisLabel.setFont(new java.awt.Font("210 맨발의청춘 B", 0, 30));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(graphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(titleLabel))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(sqiLabel)
                        .addGap(124, 124, 124)
                        .addComponent(afDiagnosisLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addComponent(measurePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(graphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sqiLabel)
                            .addComponent(afDiagnosisLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(measurePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 55, Short.MAX_VALUE))
        );

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));

        toolMenu.setBackground(new java.awt.Color(255, 255, 255));
        toolMenu.setText("Tool");

        portNameList.setBackground(new java.awt.Color(255, 255, 255));
        portNameList.setText("Port");
        toolMenu.add(portNameList);

        jMenuBar1.add(toolMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1185, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
        );

        //그래프 초기화

        series = new XYSeries("");
        XYDataset dataset = new XYSeriesCollection(series);

        chart = ChartFactory.createXYLineChart("", " ", "Volts", dataset);
        XYPlot plot = chart.getXYPlot();
        graphRenderer = new XYLineAndShapeRenderer(true, false);

        graphRange = plot.getRangeAxis();
        graphRange.setRange(0,1000);
        graphRange.setVisible(false);
        ValueAxis domain = plot.getDomainAxis();
        domain.setVisible(false);

        //legent remove
        LegendTitle legend = chart.getLegend();
        legend.setVisible(false);

        graphRenderer.setSeriesPaint(0, Color.RED);
        graphRenderer.setSeriesStroke(0, new BasicStroke(3.0f));
        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(false);
        plot.setBackgroundPaint(Color.WHITE);

        plot.setRenderer(graphRenderer);
        ValueAxis axis = plot.getDomainAxis();
        axis.setFixedAutoRange(600);

        arraySerailPort = SerialPort.getCommPorts();

        //시리얼포트 메뉴에 초기화
        for(int n = 0; n < arraySerailPort.length; n++){
            JMenuItem newItem = new JMenuItem(arraySerailPort[n].getDescriptivePortName());
            newItem.addActionListener(this);
            portNameList.add(newItem);
        }

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /*
        Start 버튼을 누를면 수행 : 
    */
    private void buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonActionPerformed
        // TODO add your handling code here:
        //jButton1 -> start
        if(button.getText().equals(ResourceValue.START_BUTTON_TEXT)){            
            if (chosenPort.openPort()) {                
                startButtonState = ButtonState.PAUSE;
                
                button.setVisible(false);
                jPanel4.setVisible(true);
                             
                readyLabel.setVisible(false);
                sqiLabel.setVisible(true);
                afDiagnosisLabel.setVisible(true);

                afDiagnosisLabel.setIcon(new javax.swing.ImageIcon(ResourceValue.LOADING_IMAGE_SRC)); 

                ecgSignalRunnable = new ECG_SignalRunnable(heartRateLabel, sqiLabel);
                                
                uiUpdateRunnable = new UiUpdateRunnable(this, series, graphRange, sqiLabel, weightLabel, graphRenderer, ecgSignalRunnable, blockingQueueEcg, blocingQueueWeight);
                uiUpdateThread = new Thread(uiUpdateRunnable);    
                
                elapseTimeRunnable = new ElapseTimeRunnable(ecgSignalRunnable, elapseTimeLabel, button, afDiagnosisLabel, chosenPort, uiUpdateRunnable); //elapseTIme, afdetection

                ecgThread = new Thread(ecgSignalRunnable);
                elapseThread = new Thread(elapseTimeRunnable);       

                serialCommRunnable = new SerialCommunicationRunnable(chosenPort, blockingQueueEcg, blocingQueueWeight);
                serialCommThread = new Thread(serialCommRunnable);                       
                                                             
                afDiagnosisLabel.setText("체중 측정 중...");
                    
                //아래의 코드는 숨겨야함
                ChartPanel cp= new ChartPanel(chart);                                                       
      
                javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphPanel);
                graphPanel.setLayout(graphPanelLayout);
                graphPanelLayout.setHorizontalGroup(
                    graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, graphPanelLayout.createSequentialGroup()
                    .addContainerGap(85, Short.MAX_VALUE)
                    .addComponent(cp, javax.swing.GroupLayout.PREFERRED_SIZE, 758, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
                );
                graphPanelLayout.setVerticalGroup(
                    graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, graphPanelLayout.createSequentialGroup()
                    .addContainerGap(74, Short.MAX_VALUE)
                    .addComponent(cp, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
                );          

                ecgThread.start();
                elapseThread.start();
                serialCommThread.start();
                uiUpdateThread.start();                                     
            }
        }
    }//GEN-LAST:event_buttonActionPerformed

    //일시정지/다시시작 버튼을 누를 경우 :
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(startButtonState == ButtonState.PAUSE){            
            if(!serialCommRunnable.getThreadWaitState()){
                startButtonState = ButtonState.RESTART;
                //wait함
                serialCommRunnable.setThreadWaitState(true);
                elapseTimeRunnable.setWaitFlag(true);
            }
        }else if(startButtonState == ButtonState.RESTART){       
            startButtonState = ButtonState.PAUSE;
            serialCommRunnable.setThreadWaitState(false);            
            serialCommRunnable.threadNotify();           
            
            elapseTimeRunnable.setWaitFlag(false);
            elapseTimeRunnable.threadNotify();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    //정지 버튼을 눌렀을 경우
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:       
        startButtonState = ButtonState.STOP;

        jPanel4.setVisible(false);
        button.setVisible(true);
           
        readyLabel.setVisible(true);
        sqiLabel.setVisible(false);
        afDiagnosisLabel.setVisible(false);
           
        uiUpdateRunnable.threadStop();
        ecgSignalRunnable.threadStop();
        serialCommRunnable.threadStop();
        elapseTimeRunnable.threadStop();
           
        chosenPort.closePort();    
           
        series.clear();           
    }//GEN-LAST:event_jButton4ActionPerformed
    
    //시리얼 포트를 초기화한다.
    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem item = (JMenuItem)e.getSource();
               
        for(int n =0; n < arraySerailPort.length; n++){
            if(arraySerailPort[n].getDescriptivePortName().equals(item.getText())){       
                chosenPort = arraySerailPort[n];
            }
        }                
        
        if(chosenPort != null){
            chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
            chosenPort.setBaudRate(115200);        
        }else{
            System.out.println("시리얼 포트가 선택되지 않음");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel afDiagnosisLabel;
    private javax.swing.JButton button;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JLabel elapseTimeLabel;
    private javax.swing.JPanel graphPanel;
    private javax.swing.JLabel heartRateLabel;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel measurePanel;
    private javax.swing.JMenuItem munuBar;
    private javax.swing.JMenu portNameList;
    private javax.swing.JLabel readyLabel;
    private javax.swing.JLabel sqiLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JMenu toolMenu;
    private javax.swing.JLabel weightLabel;
    // End of variables declaration//GEN-END:variables
}
