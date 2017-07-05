package com.alibaba.middleware.race.sync;



import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Kernel{

	public static  int maxn=8000000;
	public static  int threadNum=8;
	public  FileChannel fc;
	public  MappedByteBuffer raf;
	public  byte shu = 124;
	public  byte U = 85;
	public  byte I = 73;
	public  byte D= 68;
	public  byte line = 10;
	public  byte mao = 58;

	public int batchNum = 24200;
	public byte[] dataSet = new byte[8192*3];
	public UnBlockQueue[] unBlockQueues = new UnBlockQueue[threadNum]; 
	
	public  void deal() throws Exception{
		MessageStore messageStore = new MessageStore();

		for(int i=0;i<threadNum;i++){
			unBlockQueues[i] = new UnBlockQueue();
		}

		SubThread[] st=new SubThread[threadNum];		
		for(int i=0;i<threadNum;i++)
			st[i]=new SubThread(unBlockQueues[i],i,messageStore);		

		for(int i=0;i<threadNum;i++)st[i].start();
		int turn=0,times=0,le=0,a=1000;
		byte tmp;
		for(int i=1;i<=10;i++){       	
        	try {
        		fc =  new RandomAccessFile(Constants.DATA_HOME+"/"+i+".txt", "r").getChannel();
				raf = fc.map(MapMode.READ_ONLY, 0, fc.size());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	       	       		
    		while(true){
    			int pos = unBlockQueues[turn].putIndex();
    			if(raf.remaining()<batchNum){
    				le = raf.remaining();
    				raf.get(unBlockQueues[turn].buffer,pos,le);
    				unBlockQueues[turn].buffer[le]=0;
    				unBlockQueues[turn].in++;
   				
    				turn=(++times)%threadNum;
    				break;
    			}
    			
    			raf.get(unBlockQueues[turn].buffer,pos,batchNum);
    			a=pos+batchNum;
    			while((tmp=raf.get())!=line)unBlockQueues[turn].buffer[a++]=tmp;
    			unBlockQueues[turn].buffer[a++]=line;
    			unBlockQueues[turn].buffer[a++]=0;
    			//System.out.println(new String(unBlockQueues[turn].buffer,pos,1024));
    			unBlockQueues[turn].in++;
    			turn=(++times)%threadNum;
    		}      	
		}
		//最后添一个-1 
		int pos = unBlockQueues[turn].putIndex();
		unBlockQueues[turn].buffer[pos]=-1;
		unBlockQueues[turn].in++;

	}
	
	
}
