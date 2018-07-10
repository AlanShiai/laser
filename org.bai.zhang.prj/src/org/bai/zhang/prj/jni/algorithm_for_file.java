package org.bai.zhang.prj.jni;
import java.io.File;  
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;  
import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.FileInputStream;  
import java.io.FileWriter;  
    
@SuppressWarnings("unused")
public class algorithm_for_file {
	public static final double c=3.0e8;
	public static final int k=1;
	public static final double c1=0.35;
	public static int m1=40;
	public static int m=40;
	public static int length=10000;;
	
	public static double[] original_data_355=new double[10000];
	public static double[] filter_data_355=new double[10000];
	public static double[] distance_data_355=new double[10000];
	public static double[] inversion_data_355=new double[10000];
	public static double[] S_355=new double[10000];
	public static double[] R_355=new double[10000];
	public static double[] P_1_355=new double[10000];
	public static double[] Sigma_alpha_1_355=new double[10000];
	public static double[] Beta_alpha_1_355=new double[10000];
	public static double[] Sigma_alpha_3_355=new double[10000];
	public static double[] Beta_alpha_3_355=new double[10000];
	
	
	public static double[] original_data_532p=new double[10000];
	public static double[] filter_data_532p=new double[10000];
	public static double[] distance_data_532p=new double[10000];
	public static double[] inversion_data_532p=new double[10000];
	public static double[] S_532p=new double[10000];
	public static double[] R_532p=new double[10000];
	public static double[] P_1_532p=new double[10000];
	public static double[] Sigma_alpha_1_532p=new double[10000];
	public static double[] Beta_alpha_1_532p=new double[10000];
	public static double[] Sigma_alpha_3_532p=new double[10000];
	public static double[] Beta_alpha_3_532p=new double[10000];
	
	public static double[] original_data_532s=new double[10000];
	public static double[] filter_data_532s=new double[10000];
	public static double[] distance_data_532s=new double[10000];
	public static double[] inversion_data_532s=new double[10000];
	public static double[] S_532s=new double[10000];
	public static double[] R_532s=new double[10000];
	public static double[] P_1_532s=new double[10000];
	public static double[] Sigma_alpha_1_532s=new double[10000];
	public static double[] Beta_alpha_1_532s=new double[10000];
	public static double[] Sigma_alpha_3_532s=new double[10000];
	public static double[] Beta_alpha_3_532s=new double[10000];
	
	public static double[] original_data_1064=new double[10000];
	public static double[] filter_data_1064=new double[10000];
	public static double[] distance_data_1064=new double[10000];
	public static double[] inversion_data_1064=new double[10000];
	public static double[] S_1064=new double[10000];
	public static double[] R_1064=new double[10000];
	public static double[] P_1_1064=new double[10000];
	public static double[] Sigma_alpha_1_1064=new double[10000];;
	public static double[] Beta_alpha_1_1064=new double[10000];
	public static double[] Sigma_alpha_3_1064=new double[10000];
	public static double[] Beta_alpha_3_1064=new double[10000];
	
	
	
	public static void readFile(String pathname) 
	{
		File filename = new File(pathname); 
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);
			String line = "";  
            line = br.readLine(); 
            int i=0;
            String[] temp=new String[4];
            while (line != null) { 
            	temp=line.split("\t");
            	original_data_355[i]=Double.valueOf(temp[0]);
            	original_data_532p[i]=Double.valueOf(temp[1]);
            	original_data_532s[i]=Double.valueOf(temp[2]);
            	original_data_1064[i]=Double.valueOf(temp[3]);
                line = br.readLine();                 
                i++;
            }
            br.close();
            reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch( IOException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void calculate_common(int start_index,int end_index)
	{
		int i=0,j=0;
		double sum=0,sum1=0;
		double back_ground_signal=0;
		int length=original_data_355.length;
		//double[] time_data=new double[length];
		//对比读入时间,最后必须修改成实际距离
		
		//读入时间结束
		for(i=0;i<length;i++)
		{
			distance_data_355[i] =8e-9*i*c/2;
			distance_data_532p[i]=8e-9*i*c/2;
			distance_data_532s[i]=8e-9*i*c/2;
			distance_data_1064[i]=8e-9*i*c/2;
		}

		//355求背景噪声
		sum=0;
		for(i=start_index;i<=end_index;i++)
		{
			sum+=original_data_355[i];
		}
		back_ground_signal=sum/(end_index-start_index+1);
		for(i=20;i<length-20;i++)
		{
			sum=0;
			sum1=0;
			for(j=-20;j<21;j++)
			{
				sum+=(original_data_355[i+j]-back_ground_signal);
				sum1+=(distance_data_355[i+j]);
			}
			filter_data_355[i]=sum/41.0;
			R_355[i]=sum1/41.0;
			S_355[i]=filter_data_355[i]/c1;
			P_1_355[i]=S_355[i]*R_355[i]*R_355[i];
		}
		
		//532p求背景噪声
		sum=0;
		for(i=start_index;i<=end_index;i++)
		{
			sum+=original_data_532p[i];
		}
		back_ground_signal=sum/(end_index-start_index+1);
		for(i=20;i<length-20;i++)
		{
			sum=0;
			sum1=0;
			for(j=-20;j<21;j++)
			{
				sum+=(original_data_532p[i+j]-back_ground_signal);
				sum1+=(distance_data_532p[i+j]);
			}
			filter_data_532p[i]=sum/41.0;
			R_532p[i]=sum1/41.0;
			S_532p[i]=filter_data_532p[i]/c1;
			P_1_532p[i]=S_532p[i]*R_532p[i]*R_532p[i];
		}		
		
		//532s求背景噪声
		sum=0;
		for(i=start_index;i<=end_index;i++)
		{
			sum+=original_data_532s[i];
		}
		back_ground_signal=sum/(end_index-start_index+1);
		for(i=20;i<length-20;i++)
		{
			sum=0;
			sum1=0;
			for(j=-20;j<21;j++)
			{
				sum+=(original_data_532s[i+j]-back_ground_signal);
				sum1+=(distance_data_532s[i+j]);
			}
			filter_data_532s[i]=sum/41.0;
			R_532s[i]=sum1/41.0;
			S_532s[i]=filter_data_532s[i]/c1;
			P_1_532s[i]=S_532s[i]*R_532s[i]*R_532s[i];
		}
		
		
		
		//1064求背景噪声
		sum=0;
		for(i=start_index;i<=end_index;i++)
		{
			sum+=original_data_1064[i];
		}
		back_ground_signal=sum/(end_index-start_index+1);
		for(i=20;i<length-20;i++)
		{
			sum=0;
			sum1=0;
			for(j=-20;j<21;j++)
			{
				sum+=(original_data_1064[i+j]-back_ground_signal);
				sum1+=(distance_data_1064[i+j]);
			}
			filter_data_1064[i]=sum/41.0;
			R_1064[i]=sum1/41.0;
			S_1064[i]=filter_data_1064[i]/c1;
			P_1_1064[i]=S_1064[i]*R_1064[i]*R_1064[i];
		}
		
	}
	public static void calculate_kelett(double theta)
	{
		int i=0,j=0;
		double sum=0,sum1=0;
		double back_ground_signal=0;
		int length=original_data_355.length;
		double lamada;
		double[] Beta_m=new double[4];
		double[] Beta_alpha=new double[4];
		double[] Sigma_m=new double[4];
		double[] Sigma_alpha=new double[4];
		//参考高度，原来设置为6001，现在设置为10公里
		double R_reference=R_355[9000];
		
		//实际距离
		/*
		for(i=0;i<length;i++)
			distance_data[i]=(1e-8-5e-5)*i*c;
		*/

		
		calculate_common(8000,9999);
			
		double C=1,temp=0;

		Beta_m=calc_beta_m(R_reference,theta);

		Beta_alpha=calc_beta_alpha(R_reference,theta);
		for(i=0;i<4;i++)
		{
			Sigma_m[i]=8.0/3.0*Math.PI*Beta_m[i];
			Sigma_alpha[i]=50*Beta_alpha[i];
		}
		
		//355波长计算
		lamada=355e-9;
		for(i=0;i<=9000;i++)
		{	
			sum=0;
			for(j=i;j<=9000;j++)
			{
				sum+=P_1_355[j];
			}
			
			Sigma_alpha_1_355[i]=Math.abs(P_1_355[i])/(P_1_355[9000]/(Sigma_m[0]+Sigma_alpha[0])+2*(R_reference-R_355[i])/(9000-i)*sum);
			Beta_alpha_1_355[i]=Sigma_alpha_1_355[i]/50;
		}
		
		   /* 写入Txt文件 */  
        File writename = new File(".\\Sigma355.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
        System.out.println(writename.getAbsolutePath());
        File writename2 = new File(".\\Beta355.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
        try {
			writename.createNewFile();
			writename2.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
			BufferedWriter out2 = new BufferedWriter(new FileWriter(writename2));  
			for(i=0;i<length-40;i++)
			{
				out.write(Double.toString(Sigma_alpha_1_355[i])+"\r\n");
				out2.write(Double.toString(Beta_alpha_1_355[i])+"\r\n");
			}
			
			out.flush(); // 把缓存区内容压入文件  
			out.close(); // 最后记得关闭文件 
			out2.flush(); // 把缓存区内容压入文件  
			out2.close(); // 最后记得关闭文件 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 创建新文件  
       
        
        
    	//532p波长计算
        lamada=532e-9;
		for(i=0;i<=9000;i++)
		{	
			sum=0;
			for(j=i;j<=9000;j++)
			{
				sum+=P_1_532p[j];
			}
			Sigma_alpha_1_532p[i]=Math.abs(P_1_532p[i])/(P_1_532p[9000]/(Sigma_m[1]+Sigma_alpha[1])+2*(R_reference-R_532p[i])/(9000-i)*sum);
			Beta_alpha_1_532p[i]=Sigma_alpha_1_532p[i]/50;
		}
		
		   /* 写入Txt文件 */  
        writename = new File(".\\Sigma532p.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
        writename2 = new File(".\\Beta532p.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
        try {
			writename.createNewFile();
			writename2.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
			BufferedWriter out2 = new BufferedWriter(new FileWriter(writename2));  
			for(i=0;i<length-40;i++)
			{
				out.write(Double.toString(Sigma_alpha_1_532p[i])+"\r\n");
				out2.write(Double.toString(Beta_alpha_1_532p[i])+"\r\n");
			}
			
			out.flush(); // 把缓存区内容压入文件  
			out.close(); // 最后记得关闭文件 
			out2.flush(); // 把缓存区内容压入文件  
			out2.close(); // 最后记得关闭文件 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 创建新文件       
        
        
        
        
		//532s波长计算
        lamada=532e-9;
		for(i=0;i<=9000;i++)
		{	
			sum=0;
			for(j=i;j<=9000;j++)
			{
				sum+=P_1_532s[j];
			}
			Sigma_alpha_1_532s[i]=Math.abs(P_1_532s[i])/(P_1_532s[9000]/(Sigma_m[2]+Sigma_alpha[2])+2*(R_reference-R_532s[i])/(9000-i)*sum);
			Beta_alpha_1_532s[i]=Sigma_alpha_1_532s[i]/50;
		}
		
		   /* 写入Txt文件 */  
        writename = new File(".\\Sigma532s.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
        writename2 = new File(".\\Beta532s.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
        try {
			writename.createNewFile();
			writename2.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
			BufferedWriter out2 = new BufferedWriter(new FileWriter(writename2));  
			for(i=0;i<length-40;i++)
			{
				out.write(Double.toString(Sigma_alpha_1_532s[i])+"\r\n");
				out2.write(Double.toString(Beta_alpha_1_532s[i])+"\r\n");
			}
			
			out.flush(); // 把缓存区内容压入文件  
			out.close(); // 最后记得关闭文件 
			out2.flush(); // 把缓存区内容压入文件  
			out2.close(); // 最后记得关闭文件 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 创建新文件  
        
        
        
        
        
		//1064波长计算
        lamada=1064e-9;
		for(i=0;i<=9000;i++)
		{	
			sum=0;
			for(j=i;j<=9000;j++)
			{
				sum+=P_1_1064[j];
			}
			Sigma_alpha_1_1064[i]=Math.abs(P_1_1064[i])/(P_1_1064[9000]/(Sigma_m[3]+Sigma_alpha[3])+2*(R_reference-R_1064[i])/(9000-i)*sum);
			Beta_alpha_1_1064[i]=Sigma_alpha_1_1064[i]/50;
		}
		
		   /* 写入Txt文件 */  
        writename = new File(".\\Sigma1064.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
        writename2 = new File(".\\Beta1064.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
        try {
			writename.createNewFile();
			writename2.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
			BufferedWriter out2 = new BufferedWriter(new FileWriter(writename2));  
			for(i=0;i<length-40;i++)
			{
				out.write(Double.toString(Sigma_alpha_1_1064[i])+"\r\n");
				out2.write(Double.toString(Beta_alpha_1_1064[i])+"\r\n");
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
	
	public static void calculate_Fernald(double theta)
	{
		int i=0,j=0,k=0;
		double sum=0;
		double sum_1=0,sum_2=0,sum_3=0,sum_11=0,sum_12=0,sum_13=0;
		double temp1,temp2,temp3,temp4;
		double S1=50;
		double S2=8.0/3.0*Math.PI;
		double lamada;
		double C=1,temp=0;
		double[] Beta_m=new double[length];
		double[] Beta_alpha=new double[length];
		double[] Sigma_m=new double[length];
		double[] Sigma_alpha=new double[length];		
		double[] test=new double[length];
		//原有参考距离6001，现在为10000
		double R_reference=R_355[9000];
		calculate_common(8000,9999);
		
		
		
		
		 
		//需要根据实际采样修改
		//355波长
		lamada=355e-9;
		
		
		//R_355[9000]=6.001e3;
		for(i=0;i<=9000;i++)
		{
			Beta_m[i]=calc_beta_m(lamada,R_355[i],theta);
			Beta_alpha[i]=calc_beta_alpha(lamada,R_355[i],theta);
			Sigma_m[i]=8.0/3.0*Math.PI*Beta_m[i];
			Sigma_alpha[i]=50*Beta_alpha[i];
		}
		
		for(i=0;i<9000;i++)
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
			temp1=P_1_355[i]*Math.exp(2*(S1/S2-1.0)*(R_reference-R_355[i])/(9000.0-i)*sum_1);
			temp2=P_1_355[i]*Math.exp(2*(S1-S2)*(R_reference-R_355[i])/(9000.0-i)*sum_11);
			for(j=i;j<9000;j++)
			{
				sum_2=0;
				sum_12=0;
				for(k=j;k<9000;k++)
				{
					sum_2=sum_2+Sigma_m[k];
					sum_12=sum_12+Beta_m[k];
				}
				test[j]=P_1_355[j]*Math.exp(2*(S1/S2-1)*(R_reference-R_355[j])/(9000.0-j)*sum_2);
				sum_3=sum_3+P_1_355[j]*Math.exp(2*(S1/S2-1)*(R_reference-R_355[j])/(9000.0-j)*sum_2);
				sum_13=sum_13+P_1_355[j]*Math.exp(2*(S1-S2)*(R_reference-R_355[j])/(9000.0-j)*sum_12);
			}
			//计算分母
			temp3=P_1_355[9000]/(Sigma_alpha[9000]+Sigma_m[9000]*S1/S2)+2*(R_reference-R_355[i])/(9000.0-i)*sum_3;
			temp4=P_1_355[9000]/(Beta_alpha[9000]+Beta_m[9000])+2*S1*(R_reference-R_355[i])/(9000.0-i)*sum_13;
			Sigma_alpha_3_355[i]=-1*S1/S2*Sigma_m[i]+temp1/temp3;
			Beta_alpha_3_355[i]=-1*Beta_m[i]+temp2/temp4;
		}
		
		 /* 写入Txt文件 */  
	     File writename = new File(".\\Fernald_alpha_355.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
	     File writename2 = new File(".\\Fernald_beta_355.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
	     try {
				writename.createNewFile();
				writename2.createNewFile();
				BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
				BufferedWriter out2 = new BufferedWriter(new FileWriter(writename2));  
				for(i=0;i<length-40;i++)
				{
					out.write(Double.toString(Sigma_alpha_3_355[i])+"\r\n");
					out2.write(Double.toString(Beta_alpha_3_355[i])+"\r\n");
				}
				
				out.flush(); // 把缓存区内容压入文件  
				out.close(); // 最后记得关闭文件 
				out2.flush(); // 把缓存区内容压入文件  
				out2.close(); // 最后记得关闭文件 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} // 创建新文件  	     
     		
		
		
		
		//需要根据实际采样修改
		//532p波长
		lamada=532e-9;
		R_532p[9000]=6.001e3;
		for(i=0;i<=9000;i++)
		{
			Beta_m[i]=calc_beta_m(lamada,R_532p[i],theta);
			Beta_alpha[i]=calc_beta_alpha(lamada,R_532p[i],theta);
			Sigma_m[i]=8.0/3.0*Math.PI*Beta_m[i];
			Sigma_alpha[i]=50*Beta_alpha[i];
		}
		
		for(i=0;i<9000;i++)
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
			temp1=P_1_532p[i]*Math.exp(2*(S1/S2-1.0)*(R_reference-R_532p[i])/(9000.0-i)*sum_1);
			temp2=P_1_532p[i]*Math.exp(2*(S1-S2)*(R_reference-R_532p[i])/(9000.0-i)*sum_11);
			for(j=i;j<9000;j++)
			{
				sum_2=0;
				sum_12=0;
				for(k=j;k<9000;k++)
				{
					sum_2=sum_2+Sigma_m[k];
					sum_12=sum_12+Beta_m[k];
				}
				test[j]=P_1_532p[j]*Math.exp(2*(S1/S2-1)*(R_reference-R_532p[j])/(9000.0-j)*sum_2);
				sum_3=sum_3+P_1_532p[j]*Math.exp(2*(S1/S2-1)*(R_reference-R_532p[j])/(9000.0-j)*sum_2);
				sum_13=sum_13+P_1_532p[j]*Math.exp(2*(S1-S2)*(R_reference-R_532p[j])/(9000.0-j)*sum_12);
			}
			//计算分母
			temp3=P_1_532p[9000]/(Sigma_alpha[9000]+Sigma_m[9000]*S1/S2)+2*(R_reference-R_532p[i])/(9000.0-i)*sum_3;
			temp4=P_1_532p[9000]/(Beta_alpha[9000]+Beta_m[9000])+2*S1*(R_reference-R_532p[i])/(9000.0-i)*sum_13;
			Sigma_alpha_3_532p[i]=-1*S1/S2*Sigma_m[i]+temp1/temp3;
			Beta_alpha_3_532p[i]=-1*Beta_m[i]+temp2/temp4;
		}
		
		 /* 写入Txt文件 */  
	     writename = new File(".\\Fernald_alpha_532p.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
	     writename2 = new File(".\\Fernald_beta_532p.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
	     try {
				writename.createNewFile();
				writename2.createNewFile();
				BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
				BufferedWriter out2 = new BufferedWriter(new FileWriter(writename2));  
				for(i=0;i<length-40;i++)
				{
					out.write(Double.toString(Sigma_alpha_3_532p[i])+"\r\n");
					out2.write(Double.toString(Beta_alpha_3_532p[i])+"\r\n");
				}
				
				out.flush(); // 把缓存区内容压入文件  
				out.close(); // 最后记得关闭文件 
				out2.flush(); // 把缓存区内容压入文件  
				out2.close(); // 最后记得关闭文件 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		} // 创建新文件  
	     

			//需要根据实际采样修改
			//532s波长
			lamada=532e-9;
			R_532s[9000]=6.001e3;
			for(i=0;i<=9000;i++)
			{
				Beta_m[i]=calc_beta_m(lamada,R_532s[i],theta);
				Beta_alpha[i]=calc_beta_alpha(lamada,R_532s[i],theta);
				Sigma_m[i]=8.0/3.0*Math.PI*Beta_m[i];
				Sigma_alpha[i]=50*Beta_alpha[i];
			}
			
			for(i=0;i<9000;i++)
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
				temp1=P_1_532s[i]*Math.exp(2*(S1/S2-1.0)*(R_reference-R_532s[i])/(9000.0-i)*sum_1);
				temp2=P_1_532s[i]*Math.exp(2*(S1-S2)*(R_reference-R_532s[i])/(9000.0-i)*sum_11);
				for(j=i;j<9000;j++)
				{
					sum_2=0;
					sum_12=0;
					for(k=j;k<9000;k++)
					{
						sum_2=sum_2+Sigma_m[k];
						sum_12=sum_12+Beta_m[k];
					}
					test[j]=P_1_532s[j]*Math.exp(2*(S1/S2-1)*(R_reference-R_532s[j])/(9000.0-j)*sum_2);
					sum_3=sum_3+P_1_532s[j]*Math.exp(2*(S1/S2-1)*(R_reference-R_532s[j])/(9000.0-j)*sum_2);
					sum_13=sum_13+P_1_532s[j]*Math.exp(2*(S1-S2)*(R_reference-R_532s[j])/(9000.0-j)*sum_12);
				}
				//计算分母
				temp3=P_1_532s[9000]/(Sigma_alpha[9000]+Sigma_m[9000]*S1/S2)+2*(R_reference-R_532s[i])/(9000.0-i)*sum_3;
				temp4=P_1_532s[9000]/(Beta_alpha[9000]+Beta_m[9000])+2*S1*(R_reference-R_532s[i])/(9000.0-i)*sum_13;
				Sigma_alpha_3_532s[i]=-1*S1/S2*Sigma_m[i]+temp1/temp3;
				Beta_alpha_3_532s[i]=-1*Beta_m[i]+temp2/temp4;
			}
			
			 /* 写入Txt文件 */  
		     writename = new File(".\\Fernald_alpha_532s.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
		     writename2 = new File(".\\Fernald_beta_532s.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
		     try {
					writename.createNewFile();
					writename2.createNewFile();
					BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
					BufferedWriter out2 = new BufferedWriter(new FileWriter(writename2));  
					for(i=0;i<length-40;i++)
					{
						out.write(Double.toString(Sigma_alpha_3_532s[i])+"\r\n");
						out2.write(Double.toString(Beta_alpha_3_532s[i])+"\r\n");
					}
					
					out.flush(); // 把缓存区内容压入文件  
					out.close(); // 最后记得关闭文件 
					out2.flush(); // 把缓存区内容压入文件  
					out2.close(); // 最后记得关闭文件 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} // 创建新文件  	     
	
		     
		     
		     
				//需要根据实际采样修改
				//1064波长
				lamada=1064e-9;
				R_1064[9000]=6.001e3;
				for(i=0;i<=9000;i++)
				{
					Beta_m[i]=calc_beta_m(lamada,R_1064[i],theta);
					Beta_alpha[i]=calc_beta_alpha(lamada,R_1064[i],theta);
					Sigma_m[i]=8.0/3.0*Math.PI*Beta_m[i];
					Sigma_alpha[i]=50*Beta_alpha[i];
				}
				
				for(i=0;i<9000;i++)
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
					temp1=P_1_1064[i]*Math.exp(2*(S1/S2-1.0)*(R_reference-R_1064[i])/(9000.0-i)*sum_1);
					temp2=P_1_1064[i]*Math.exp(2*(S1-S2)*(R_reference-R_1064[i])/(9000.0-i)*sum_11);
					for(j=i;j<9000;j++)
					{
						sum_2=0;
						sum_12=0;
						for(k=j;k<9000;k++)
						{
							sum_2=sum_2+Sigma_m[k];
							sum_12=sum_12+Beta_m[k];
						}
						test[j]=P_1_1064[j]*Math.exp(2*(S1/S2-1)*(R_reference-R_1064[j])/(9000.0-j)*sum_2);
						sum_3=sum_3+P_1_1064[j]*Math.exp(2*(S1/S2-1)*(R_reference-R_1064[j])/(9000.0-j)*sum_2);
						sum_13=sum_13+P_1_1064[j]*Math.exp(2*(S1-S2)*(R_reference-R_1064[j])/(9000.0-j)*sum_12);
					}
					//计算分母
					temp3=P_1_1064[9000]/(Sigma_alpha[9000]+Sigma_m[9000]*S1/S2)+2*(R_reference-R_1064[i])/(9000.0-i)*sum_3;
					temp4=P_1_1064[9000]/(Beta_alpha[9000]+Beta_m[9000])+2*S1*(R_reference-R_1064[i])/(9000.0-i)*sum_13;
					Sigma_alpha_3_1064[i]=-1*S1/S2*Sigma_m[i]+temp1/temp3;
					Beta_alpha_3_1064[i]=-1*Beta_m[i]+temp2/temp4;
				}
				
				 /* 写入Txt文件 */  
			     writename = new File(".\\Fernald_alpha_1064.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
			     writename2 = new File(".\\Fernald_beta_1064.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
			     try {
						writename.createNewFile();
						writename2.createNewFile();
						BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
						BufferedWriter out2 = new BufferedWriter(new FileWriter(writename2));  
						for(i=0;i<length-40;i++)
						{
							out.write(Double.toString(Sigma_alpha_3_1064[i])+"\r\n");
							out2.write(Double.toString(Beta_alpha_3_1064[i])+"\r\n");
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
	
	public static void calculate_boundary_layer_height(double theta,int deta)
	{
		int i=0,j=0;
		double sum=0,sum1=0;
		double back_ground_signal=0;
		double Beta_m;
		double Beta_alpha;
		double Sigma_m;
		double Sigma_alpha;
		double lamada;
		double[] height_data=new double[3000];
		//实际距离需要在calculate_common中修改

		calculate_common(8000,9999);	
		
		
		//355波长
		lamada=355e-9;
		for(i=0;i<3000;i++)
		{	
			height_data[i]=(P_1_355[i+deta]-P_1_355[i])/deta;
		}
		
		   /* 写入Txt文件 */  
        File writename = new File(".\\height_Difference_355.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
        try {
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
			for(i=0;i<3000;i++)
			{
				out.write(Double.toString(height_data[i])+"\r\n");
			}
			
			out.flush(); // 把缓存区内容压入文件  
			out.close(); // 最后记得关闭文件 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 创建新文件  	
        
		//532p波长
		lamada=532e-9;
		for(i=0;i<3000;i++)
		{	
			height_data[i]=(P_1_532p[i+deta]-P_1_532p[i])/deta;
		}
		
		   /* 写入Txt文件 */  
        writename = new File(".\\height_Difference_532p.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
        try {
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
			for(i=0;i<3000;i++)
			{
				out.write(Double.toString(height_data[i])+"\r\n");
			}
			
			out.flush(); // 把缓存区内容压入文件  
			out.close(); // 最后记得关闭文件 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 创建新文件  	
        
		//532s波长
		lamada=532e-9;
		for(i=0;i<3000;i++)
		{	
			height_data[i]=(P_1_532s[i+deta]-P_1_532s[i])/deta;
		}
		
		   /* 写入Txt文件 */  
        writename = new File(".\\height_Difference_532s.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
        try {
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
			for(i=0;i<3000;i++)
			{
				out.write(Double.toString(height_data[i])+"\r\n");
			}
			
			out.flush(); // 把缓存区内容压入文件  
			out.close(); // 最后记得关闭文件 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 创建新文件  	
        
		//1064波长
		lamada=1064e-9;
		for(i=0;i<3000;i++)
		{	
			height_data[i]=(P_1_1064[i+deta]-P_1_1064[i])/deta;
		}
		
		   /* 写入Txt文件 */  
        writename = new File(".\\height_Difference_1064.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
        try {
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
			for(i=0;i<3000;i++)
			{
				out.write(Double.toString(height_data[i])+"\r\n");
			}
			
			out.flush(); // 把缓存区内容压入文件  
			out.close(); // 最后记得关闭文件 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 创建新文件  	
        
        
	}
	
	public static void calculate_depolarization_ratio(String file_name,double lamada,double theta,int deta)
	{
		int i=0,j=0;
		double sum=0,sum1=0;
		double back_ground_signal=0;
		//double[] time_data=new double[length];
		double Beta_m;
		double Beta_alpha;
		double Sigma_m;
		double Sigma_alpha;
		double[] height_data=new double[3000];
		double[] p_data=new double[length];
		double[] s_data=new double[length];
		double[] P_1_p=new double[length];
		double[] P_1_s=new double[length];
		double[] depolarization_ratio=new double[5000];
		//实际距离需要在calculate_common中修改

		
		//readFile(P_file_name) ;
		
		readFile(file_name) ;
		calculate_common(9000,9999);
		
		for(i=0;i<length;i++)
		{
			p_data[i]=filter_data_532p[i];
			P_1_p[i]=P_1_532p[i];
		}
		//readFile(S_file_name) ;
		
		//calculate_common(9000,9999);
		
		for(i=0;i<length;i++)
		{
			s_data[i]=filter_data_532s[i];
			P_1_s[i]=P_1_532s[i];
		}
		
		
		for(i=0;i<5000;i++)
		{	
			depolarization_ratio[i]=P_1_p[i]/P_1_s[i];
		}
		
		   /* 写入Txt文件 */  
        File writename = new File(".\\depolarization_ratio.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
        try {
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
			for(i=0;i<3000;i++)
			{
				out.write(Double.toString(height_data[i])+"\r\n");
			}
			
			out.flush(); // 把缓存区内容压入文件  
			out.close(); // 最后记得关闭文件 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 创建新文件  		
	}
	
	
	public static double[] calc_beta_m(double  coefficient,double theta)
	{
		double[] Beta_m=new double[4];
		double lamada=355e-9;
		Beta_m[0]=1.54e-6*(532e-9/lamada)*(532e-9/lamada)*(532e-9/lamada)*(532e-9/lamada)*Math.exp(-1*coefficient*Math.cos(theta)/7000.0);
		lamada=532e-9;
		Beta_m[1]=1.54e-6*(532e-9/lamada)*(532e-9/lamada)*(532e-9/lamada)*(532e-9/lamada)*Math.exp(-1*coefficient*Math.cos(theta)/7000.0);
		lamada=532e-9;
		Beta_m[2]=1.54e-6*(532e-9/lamada)*(532e-9/lamada)*(532e-9/lamada)*(532e-9/lamada)*Math.exp(-1*coefficient*Math.cos(theta)/7000.0);
		lamada=1064e-9;
		Beta_m[3]=1.54e-6*(532e-9/lamada)*(532e-9/lamada)*(532e-9/lamada)*(532e-9/lamada)*Math.exp(-1*coefficient*Math.cos(theta)/7000.0);
		return Beta_m;
	}
	

	public static double calc_beta_m(double lamada,double  coefficient,double theta)
	{
		double Beta_m;
		Beta_m=1.54e-6*(532e-9/lamada)*(532e-9/lamada)*(532e-9/lamada)*(532e-9/lamada)*Math.exp(-1*coefficient*Math.cos(theta)/7000.0);
		return Beta_m;
	}
	
	public static double[] calc_beta_alpha(double  coefficient,double theta)
	{
		double[] Beta_alpha=new double[4];
		double temp;
		double lamada;
		double C=1;
		temp=-1*Math.pow((coefficient*Math.cos(theta)-20000.0)/6000.0, 2);
		
		lamada=355e-9;
		Beta_alpha[0]=(2.47e-6*Math.exp(-1*coefficient*Math.cos(theta)/2000.0)+5.13e-9*Math.exp(temp))*Math.pow(532e-9/lamada,1.3);
		lamada=532e-9;
		Beta_alpha[1]=(2.47e-6*Math.exp(-1*coefficient*Math.cos(theta)/2000.0)+5.13e-9*Math.exp(temp))*Math.pow(532e-9/lamada,1.3);
		lamada=532e-9;
		Beta_alpha[2]=(2.47e-6*Math.exp(-1*coefficient*Math.cos(theta)/2000.0)+5.13e-9*Math.exp(temp))*Math.pow(532e-9/lamada,1.3);
		lamada=1064e-9;
		Beta_alpha[3]=(2.47e-6*Math.exp(-1*coefficient*Math.cos(theta)/2000.0)+5.13e-9*Math.exp(temp))*Math.pow(532e-9/lamada,1.3);
		
		return Beta_alpha;
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
		
//		short[] result=new short[40000];
//        System.loadLibrary("DataCollectLib");  
//        NativeClass nativeCode = new NativeClass();  
//        nativeCode.openDevice(); 
//        
//        String s=new String(".\\block.txt");
//        File file = new File(s);
//        File blockDir = new File(file.getParent(), "measureData");
//        blockDir.mkdir();
//        int i = 10;
//        while ( i -- > 0) {
//        	nativeCode.collectBlockTriggered();
//        	if (file.exists()) {
//        		File renameFile = new File(blockDir, System.currentTimeMillis() + "");
//        		System.out.println("rename : " + renameFile.getAbsolutePath());
//        		file.renameTo(renameFile);
//        	}
//        }
        //nativeCode.collectStreamingImmediate();
        //nativeCode.collectStreamingTriggered();
        //nativeCode.collectRapidBlock();
        //result=nativeCode.collectBlockImmediateArray();
        //result=nativeCode.collectRapidBlockArray();
        //新测试
        //nativeCode.collectStreamingImmediateArray();不推荐使用，VM容易出错
 
//        nativeCode.closeDevice();
        
		String s=new String(".\\block.txt");
		readFile(s) ;
		calculate_kelett(0);
		System.out.println("finish");
		//calculate_Fernald(0);
	}
	
	public static void calculate_kelett(String file) {
		readFile(file);
		calculate_kelett(0);
		System.out.println("finish");
	}

	
}
