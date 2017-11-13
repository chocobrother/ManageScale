package uclab.algorithms;

import java.awt.Label;
import java.util.ArrayList;

import javax.swing.JLabel;
import uclab.common.TextFileWrite;
import uclab.common.LogPrint;

/**
 * Created by sun on 2017-02-04.
 */
public class  ECG_SignalRunnable implements Runnable {
	private final String TAG = "HEART_RATE_THREAD";
	private final int GoodSignal = 1, BadSignal = 2;
	long startTime, currentTime, signaTestTime;
	long checkTime = 0;
	boolean signalClean = false;
	boolean cleanSignalFlag = false;
	boolean elapseTimeFlag = false;
	boolean weightFlag = false;
        
        public static double simbak;
        
        public static String simbaksu;
        
        boolean threadFlag = true;
        
	String afDetection = "No";
	public ArrayList<Double> full_signal, clean_signal;
	int[] heartRateArray;
	int hrArrayIdx = 0;
	int fs_min = 70;
        int cal_fs = 0;

	// add text
	JLabel heartRate;
	JLabel sqi;

	peakDetection peak;
	// hrThread hr;//
	// ECG_Manage_Thread dataMange;

	ArrayList<Double> avgHr = new ArrayList<Double>();
	double[] a, b;
	ArrayList<Double> A = new ArrayList<Double>();
	ArrayList<Double> B = new ArrayList<Double>();
        ArrayList<Double> rriCal = new ArrayList<Double>();
        int cleanSignalIdx;
        
	int fileNumber = 0, fileNumber1 = 0;  

        TextFileWrite<Double> textFile, filteredFile;
        ArrayList<Integer> clean_fs = new ArrayList<>();
        
	public ECG_SignalRunnable(JLabel heartRate, JLabel sqi) {
//                textFile = new TextFileWrite<Double>("rawSignalFileForder", "rawEcg");
//                filteredFile = new TextFileWrite<Double>("filteredFileForder", "filteredEcg");
                
		this.heartRate = heartRate;
		this.sqi = sqi;
                this.cleanSignalIdx = 0;
                
		full_signal = new ArrayList<Double>();
		clean_signal = new ArrayList<Double>();
		heartRateArray = new int[2];

		a = new double[7];
		b = new double[7];

		a[0] = 0.0614;
		a[1] = 0.0;
		a[2] = -0.1841;
		a[3] = 0.0;
		a[4] = 0.1841;
		a[5] = 0.0;
		a[6] = -0.0614;

		b[0] = 1.0;
		b[1] = -2.3648;
		b[2] = 2.9556;
		b[3] = -2.4505;
		b[4] = 1.4848;
		b[5] = -0.5514;
		b[6] = 0.1108;

		for (int n = 0; n < a.length; n++)
			A.add(a[n]);
		for (int n = 0; n < b.length; n++)
			B.add(b[n]);
	}

	public void setSignalAdd(double value) {
		full_signal.add(value);

		if (cleanSignalFlag) {
			clean_signal.add(value);
		}
	}

	public void start() {
		startTime = System.currentTimeMillis();
		signaTestTime = System.currentTimeMillis();
	}

	public double getAvgHr() {
		double tmp = 0;
		for (int n = 0; n < avgHr.size(); n++)
			tmp += avgHr.get(n);

		tmp /= avgHr.size();

		return tmp;
	}

	public String afDetection() {	
//		ArrayList<Double> data = new ArrayList<>();
//
//                for(int n = cleanSignalIdx; n < clean_signal.size(); n++){
//                    data.add(clean_signal.get(n));
//                }
//		
//		ArrayList<Double> filtTmp = Filtfilt.doFiltfilt(A, B, data);
//                                
//		double[] ecg_h = new double[filtTmp.size()];               
//
//		for(int n = 0; n < filtTmp.size(); n++){
//			ecg_h[n] = filtTmp.get(n);
//		}
//		
//		ecg_h = cus_math.max_division(ecg_h);
//                
//                //계산한 fs를 적용하기로 함 잘못된 fs를 거르기 위해서 1~size-1까지의 fs의 평균치를 사용함
//                int fs = 0;
//                for(int n = 1; n < clean_fs.size()-1; n++){
//                    System.out.println("detection fs:"+clean_fs.get(n));
//                    fs += clean_fs.get(n);
//                }
//                System.out.println("fs len:"+clean_fs.size());
//                       System.out.println("fs len:"+clean_fs.size()+","+fs);
//                fs = (fs/(clean_fs.size()-2));
//                
//                System.out.println("ecg signal detection fs :"+fs);
//		peakDetection peak = new peakDetection(ecg_h, fs);
//
//		peak.calculation();
//
//		ArrayList<Integer> tmp = peak.getRpeak();
//
//		double[] rr = new double[tmp.size() - 1];
//		double[] rri = new double[rr.length];
//		// diff
//		for (int n = 0; n < rr.length; n++) {
//			rr[n] = tmp.get(n + 1) - tmp.get(n);
//		}
//
//		for (int n = 0; n < rr.length; n++) {
//			rri[n] = rr[n] / fs;
//		}

//		// rmssd
//		double dd = 0;
//		for (int n = 0; n < rri.length - 1; n++) {
//			dd += Math.pow(rri[n + 1] - rri[n], 2);
//		}
//
//		double rmssd = Math.sqrt(dd / rri.length);
//
//                LogPrint.println("ECG_Signal", "rmssd: "+rmssd);
//
//		if (rmssd > 0.13) {
//			afDetection = "yes";
//		} else {
//			afDetection = "no";
//		}

                rriCalculation();
                double dd = 0;
		for (int n = 0; n < rriCal.size() - 1; n++) {
			dd += Math.pow(rriCal.get(n+1) - rriCal.get(n), 2);
		}

		double rmssd = Math.sqrt(dd / rriCal.size());

                LogPrint.println("ECG_Signal", "rmssd: "+rmssd);

		if (rmssd > 0.15) {
			afDetection = "yes";
		} else {
			afDetection = "no";
		}
		return afDetection;
	}

	public double hearRate(ArrayList<Integer> data, int fs) {
		int[] rr = cus_math.RR_interval(data);
		int hr = 0;

		for (int n = 0; n < rr.length; n++) {	
                    //(fs*1min)/rr
                    hr += (fs * 60) / rr[n];
		}
		hr /= rr.length;

		return hr;
	}

        
        
        public boolean rriCalculation(){
                 System.out.println("size comp first : "+cleanSignalIdx+","+clean_signal.size());
		if (clean_signal.size() > (fs_min * 2) && cleanSignalIdx+400 < clean_signal.size()) {
			ArrayList<Double> data = new ArrayList<>();
			int length = clean_signal.size();

			for (int n = cleanSignalIdx; n < length; n++) {
				data.add(clean_signal.get(n));
			}

			int fs = 99;
		
			ArrayList<Double> filtTmp = Filtfilt.doFiltfilt(A, B, data);
			double[] ecg_h = new double[filtTmp.size()];                       

			for(int n = 0; n < filtTmp.size(); n++){
				ecg_h[n] = filtTmp.get(n);
			}
			
			ecg_h = cus_math.max_division(ecg_h);
			peakDetection peak = new peakDetection(ecg_h, fs);
			peak.calculation();

                        LogPrint.println("ECG_Signal", "isSignal_cal_fs:"+fs);
			ArrayList<Integer> tmp = peak.getRpeak();
                        
                        System.out.println("rpeak length:"+tmp.size());
                        if(tmp.size() == 0){
                            System.out.println("rpeak length:"+tmp.size()+"rriCalculation , 심전도 데이터가 부족한 것으로 보임");
                            return false;
                        }
                        double[] rr = new double[tmp.size() - 1];
                        double[] rri = new double[rr.length];
                        // diff
                        for (int n = 0; n < rr.length; n++) {
                                rr[n] = tmp.get(n + 1) - tmp.get(n);
                        }

                        for (int n = 0; n < rr.length; n++) {
                                rriCal.add(rr[n] / fs);
                                System.out.println(rr[n]/fs);
                        }
                        
                        clean_signal.add(-0.3);
                        clean_signal.add(-0.3);
                        clean_signal.add(-0.3);
                        cleanSignalIdx = clean_signal.size()-1;
                        System.out.println("size comp : "+cleanSignalIdx+","+clean_signal.size());                        
                }
                return true;
        }

    
        
        
	public boolean isSignalClean() {
		// Log.e(TAG, "full_signal: "+full_signal.size());

		if (full_signal.size() > (fs_min * 2)) {
			ArrayList<Double> data = new ArrayList<>();
			int length = full_signal.size();

			for (int n = 0; n < length; n++) {
				data.add(full_signal.get(n));
			}

			int fs = data.size() / 2;
		
			ArrayList<Double> filtTmp = Filtfilt.doFiltfilt(A, B, data);
			double[] ecg_h = new double[filtTmp.size()];                       

			for(int n = 0; n < filtTmp.size(); n++){
				ecg_h[n] = filtTmp.get(n);
			}
			
			ecg_h = cus_math.max_division(ecg_h);
			peakDetection peak = new peakDetection(ecg_h, fs);
			peak.calculation();

                        LogPrint.println("ECG_Signal", "isSignal_cal_fs:"+fs);
			ArrayList<Integer> tmp = peak.getRpeak();

                        //주환 수정
                        if(heartRateArray[0]!=0 && heartRateArray[1]!=0){
                        simbak = (heartRateArray[0] + heartRateArray[1])/2;
                        }
                       
                        simbaksu = Double.toString(simbak);

			double hr = 0;
			if (tmp.size() > 1) {				
				hr = hearRate(tmp, fs);
				heartRate.setText("" + (int) hr);
				heartRateArray[hrArrayIdx++] = (int) hr;

				if (hrArrayIdx == 2) {
					hrArrayIdx = 0;
				}

				double tmp1 = heartRateArray[0] - heartRateArray[1];
                                LogPrint.println("ECG_SIGNAL","Heart Rate " + hr +",heartRateArray[0]:"+heartRateArray[0]+",heartRateArray[1]"+heartRateArray[1]);    				
				if (hr > 150 || hr < 50) {                        
					return false;
				}

				if (Math.abs(tmp1) > 30) {
					// bad signal
					return false;
				} else {
					// good signal
					avgHr.add(hr);
                                        clean_fs.add(fs);
					return true;
				}
			}
		}
		return false;
	}
	
	public void setElapseTimeFlag(boolean flag){
		elapseTimeFlag = flag;
	}
	
	public boolean getElapseTimeFlag(){
		return elapseTimeFlag;
	}
	
	public boolean getCleanSignalFlag(){
		return cleanSignalFlag;
	}

        public void threadStop(){
            LogPrint.println("ECG_SIGNAL","ecgsignal thread 종료");    		            

            this.threadFlag = false;
        }
                    
	@Override
	public void run() {
		boolean heartRateFlag = false;
                boolean removeSignalFlag = false;
                
		while (threadFlag) {
			currentTime = System.currentTimeMillis();
			if (currentTime - startTime >= 2000) {
                                cal_fs = full_signal.size()/2;
                                LogPrint.println("ECG_Signal", "cal_fs:"+full_signal.size()/2);
                                
				if (isSignalClean()) {
					checkTime += 2000;
					cleanSignalFlag = true;
                                        removeSignalFlag = true;
				} else {                                    
                                    if(clean_signal.size() > 400 && removeSignalFlag){
                                        int index = clean_signal.size()-1;
                                        for(int n =0; n < 99*2; n++){
                                            clean_signal.remove(index--);
                                        }
                                        removeSignalFlag = false;
                                    }
                                    rriCalculation();
                                    //이전 신호에 대해서 RRI 계산
                                    cleanSignalFlag = false;
                                    checkTime = 0;                                        
				}

				full_signal.clear();
				startTime = currentTime;
			}
		}
	}
}