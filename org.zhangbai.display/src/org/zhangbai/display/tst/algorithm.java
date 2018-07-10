package org.zhangbai.display.tst;

import java.io.File;  
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;  
import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.FileInputStream;  
import java.io.FileWriter;  

@SuppressWarnings("unused")
public class algorithm {
	public static final double c=3.0e8;
	public static final int k=1;
	public static final double c1=0.35;
	public static int m1=40;
	public static int m=40;
	public static double[] original_data=new double[10000];
	public static double[] filter_data=new double[10000];
	public static double[] distance_data=new double[10000];
	public static double[] inversion_data=new double[10000];
	public static double[] S=new double[10000];
	public static double[] R=new double[10000];
	public static double[] P_1=new double[10000];
	public static void readFile(String pathname) 
	{
		File filename = new File(pathname); 
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);
			String line = "";  
			line = br.readLine(); 
			int i=0;
			while (line != null) {  
				original_data[i]=Double.valueOf(line);
				line = br.readLine();                 
				i++;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch( IOException e)
		{
			e.printStackTrace();
		}


	}
	public static void calculate_common()
	{
		int i=0,j=0;
		double sum=0,sum1=0;
		double back_ground_signal=0;
		int length=original_data.length;
		double[] time_data=new double[length];
		//对比读入时间
		File filename = new File("E:\\time.txt"); 
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);
			String line = "";  
			line = br.readLine(); 
			i=0;
			while (line != null) {  
				time_data[i]=Double.valueOf(line);
				line = br.readLine();                 
				i++;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch( IOException e)
		{
			e.printStackTrace();
		}
		//读入时间结束
		for(i=0;i<length;i++)
			distance_data[i]=time_data[i]*c/2;

		//求背景噪声
		sum=0;
		for(i=8000;i<length;i++)
		{
			sum+=original_data[i];
		}
		back_ground_signal=sum/(length-8000-1);
		for(i=20;i<length-20;i++)
		{
			sum=0;
			sum1=0;
			for(j=-20;j<21;j++)
			{
				sum+=(original_data[i+j]-back_ground_signal);
				sum1+=(distance_data[i+j]);
			}
			filter_data[i]=sum/41.0;
			R[i]=sum1/41.0;
			S[i]=filter_data[i]/c1;
			P_1[i]=S[i]*R[i]*R[i];
		}
	}
	public static void calculate_kelett(double lamada,double theta)
	{
		int i=0,j=0;
		double sum=0,sum1=0;
		double back_ground_signal=0;
		int length=original_data.length;
		double[] time_data=new double[length];
		double Beta_m;
		double Beta_alpha;
		double Sigma_m;
		double Sigma_alpha;
		double[] Sigma_alpha_1=new double[length];;
		double[] Beta_alpha_1=new double[length];
		//实际距离
		/*
		for(i=0;i<length;i++)
			distance_data[i]=(1e-8-5e-5)*i*c;
		 */

		calculate_common();

		double C=1,temp=0;
		//Beta_m=1.54e-6*(532e-9/lamada)*(532e-9/lamada)*(532e-9/lamada)*(532e-9/lamada)*Math.exp(-1*2.4e4*Math.cos(theta)/7000.0);
		Beta_m=calc_beta_m(lamada,6.001e3,theta);
		//temp=-1*Math.pow((2.4e4*Math.cos(theta)-2000)/6000, 2);
		//Beta_alpha=(2.47e-6*Math.exp(-1*2.4e4*Math.cos(theta)/2000)+5.13e-9*Math.exp(temp))*Math.pow(532e-9/lamada,1.3);
		Beta_alpha=calc_beta_alpha(lamada,6.001e3,theta);
		Sigma_m=8.0/3.0*Math.PI*Beta_m;
		Sigma_alpha=50*Beta_alpha;

		for(i=500;i<=9000;i++)
		{	
			sum=0;
			for(j=i;j<=9000;j++)
			{
				sum+=P_1[j];
			}
			Sigma_alpha_1[i]=Math.abs(P_1[i])/(P_1[9000]/(Sigma_m+Sigma_alpha)+2*(6.001e3-R[i])/(9000-i)*sum);
			Beta_alpha_1[i]=Sigma_alpha_1[i]/50;
		}

		/* 写入Txt文件 */  
		File writename = new File(".\\Sigma1064.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
		File writename2 = new File(".\\Beta1064.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
		System.out.println("aijun 3:\n"+  writename.getAbsolutePath());
		System.out.println("aijun 4:\n"+  writename2.getAbsolutePath());
		try {
			writename.createNewFile();
			writename2.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
			BufferedWriter out2 = new BufferedWriter(new FileWriter(writename2));  
			for(i=0;i<length-40;i++)
			{
				out.write(Double.toString(Sigma_alpha_1[i])+"\r\n");
				out2.write(Double.toString(Beta_alpha_1[i])+"\r\n");
			}

			out.flush(); // 把缓存区内容压入文件  
			out.close(); // 最后记得关闭文件 
			out2.flush(); // 把缓存区内容压入文件  
			out2.close(); // 最后记得关闭文件 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 创建新文件  


	}
	/*
	public static void calculate_other(double lamada,double theta)
	{
		int i=0,j=0;
		double sum=0;
		double coefficient_k=1.0;
		int length=original_data.length;
		double C=1,temp=0;
		double[] Beta_m=new double[length-40];
		double[] Beta_alpha=new double[length-40];
		double[] Sigma_m=new double[length-40];
		double[] Sigma_alpha=new double[length-40];
		double[] Sigma_alpha_2=new double[length-40];;
		double[] Beta_alpha_2=new double[length-40];

		calculate_common();
		for(i=500;i<9000;i++)
		{
			Beta_m[i]=calc_beta_m(lamada,R[i],theta);
			Beta_alpha[i]=calc_beta_alpha(lamada,R[i],theta);
			Sigma_m[i]=8.0/3.0*Math.PI*Beta_m[i];
			S[i]=Math.log(P_1[i]);
			sum=0;
			for(j=i;j<9000;j++)
			{
				sum+=Math.exp((S[j]-S[9000])/coefficient_k);
			}
			Sigma_alpha_2[i]=Math.exp((S[i]-S[9000])/coefficient_k)/(1/(Sigma_m[9000]+Sigma_alpha[9000])+2/coefficient_k*(R[9000]-R[i])/(9000-k)*sum);
			Beta_alpha_2[i]=Sigma_alpha_2[i]/50;
		}

        File writename = new File(".\\Sigma355P.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
        File writename2 = new File(".\\Beta355P.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
        try {
			writename.createNewFile();
			writename2.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
			BufferedWriter out2 = new BufferedWriter(new FileWriter(writename2));  
			for(i=0;i<length-40;i++)
			{
				out.write(Double.toString(Sigma_alpha_2[i])+"\r\n");
				out2.write(Double.toString(Beta_alpha_2[i])+"\r\n");
			}

			out.flush(); // 把缓存区内容压入文件  
			out.close(); // 最后记得关闭文件 
			out2.flush(); // 把缓存区内容压入文件  
			out2.close(); // 最后记得关闭文件 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 创建新文件  
	}
	 */	
	public static void calculate_Fernald(double lamada,double theta)
	{
		int i=0,j=0,k=0;
		double sum=0;
		double sum_1=0,sum_2=0,sum_3=0,sum_11=0,sum_12=0,sum_13=0;
		double temp1,temp2,temp3,temp4;
		double S1=50;
		double S2=8.0/3.0*Math.PI;
		int length=original_data.length;
		double C=1,temp=0;
		double[] Beta_m=new double[length];
		double[] Beta_alpha=new double[length];
		double[] Sigma_m=new double[length];
		double[] Sigma_alpha=new double[length];
		double[] Sigma_alpha_3=new double[length];
		double[] Beta_alpha_3=new double[length];
		double[] test=new double[length];
		calculate_common();
		R[9000]=6.001e3;
		for(i=500;i<=9000;i++)
		{
			Beta_m[i]=calc_beta_m(lamada,R[i],theta);
			Beta_alpha[i]=calc_beta_alpha(lamada,R[i],theta);
			Sigma_m[i]=8.0/3.0*Math.PI*Beta_m[i];
			Sigma_alpha[i]=50*Beta_alpha[i];
		}

		for(i=500;i<9000;i++)
		{
			sum_1=0;
			sum_11=0;
			sum_3=0;
			sum_13=0;

			for(j=i;j<=9000;j++)
			{
				sum_1=sum_1+Sigma_m[j];
				sum_11=sum_11+Beta_m[j];
			}
			//计算分子
			temp1=P_1[i]*Math.exp(2*(S1/S2-1.0)*(R[9000]-R[i])/(9000.0-i)*sum_1);
			temp2=P_1[i]*Math.exp(2*(S1-S2)*(R[9000]-R[i])/(9000.0-i)*sum_11);
			for(j=i;j<9000;j++)
			{
				sum_2=0;
				sum_12=0;
				for(k=j;k<9000;k++)
				{
					sum_2=sum_2+Sigma_m[k];
					sum_12=sum_12+Beta_m[k];
				}
				test[j]=P_1[j]*Math.exp(2*(S1/S2-1)*(6.001e3-R[j])/(9000.0-j)*sum_2);
				sum_3=sum_3+P_1[j]*Math.exp(2*(S1/S2-1)*(6.001e3-R[j])/(9000.0-j)*sum_2);
				sum_13=sum_13+P_1[j]*Math.exp(2*(S1-S2)*(6.001e3-R[j])/(9000.0-j)*sum_12);
			}
			//计算分母
			temp3=P_1[9000]/(Sigma_alpha[9000]+Sigma_m[9000]*S1/S2)+2*(R[9000]-R[i])/(9000.0-i)*sum_3;
			temp4=P_1[9000]/(Beta_alpha[9000]+Beta_m[9000])+2*S1*(R[9000]-R[i])/(9000.0-i)*sum_13;
			Sigma_alpha_3[i]=-1*S1/S2*Sigma_m[i]+temp1/temp3;
			Beta_alpha_3[i]=-1*Beta_m[i]+temp2/temp4;
		}

		/* 写入Txt文件 */  
		File writename = new File(".\\Fernald_alpha.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
		File writename2 = new File(".\\Fernald_beta.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
		System.out.println("aijun 1:\n"+  writename.getAbsolutePath());
		System.out.println("aijun 2:\n"+  writename2.getAbsolutePath());
		try {
			writename.createNewFile();
			writename2.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
			BufferedWriter out2 = new BufferedWriter(new FileWriter(writename2));  
			for(i=0;i<length-40;i++)
			{
				out.write(Double.toString(Sigma_alpha_3[i])+"\r\n");
				out2.write(Double.toString(Beta_alpha_3[i])+"\r\n");
			}

			out.flush(); // 把缓存区内容压入文件  
			out.close(); // 最后记得关闭文件 
			out2.flush(); // 把缓存区内容压入文件  
			out2.close(); // 最后记得关闭文件 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 创建新文件  
	}	


	public static double calc_beta_m(double lamada,double  coefficient,double theta)
	{
		double Beta_m;
		Beta_m=1.54e-6*(532e-9/lamada)*(532e-9/lamada)*(532e-9/lamada)*(532e-9/lamada)*Math.exp(-1*coefficient*Math.cos(theta)/7000.0);
		return Beta_m;
	}
	public static double calc_beta_alpha(double lamada,double  coefficient,double theta)
	{
		double Beta_alpha,temp;
		double C=1;
		temp=-1*Math.pow((coefficient*Math.cos(theta)-20000.0)/6000.0, 2);
		Beta_alpha=(2.47e-6*Math.exp(-1*coefficient*Math.cos(theta)/2000.0)+5.13e-9*Math.exp(temp))*Math.pow(532e-9/lamada,1.3);
		return Beta_alpha;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
		String s=new String("E:\\test.txt");
		readFile(s) ;
		calculate_kelett(1064e-9,0);
		//		calculate_Fernald(1064e-9,0);

		long end = System.currentTimeMillis();
		System.out.println("aijun 3: " + (end-start));
	}

}
