package com.alibaba.middleware.race.sync;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubThread extends Thread{

	public UnBlockQueue UnBlockQueue;
	public MessageStore messageStore;
	public byte[][] data = new byte[600][36];
	public int[][] idval = new int[600][2];
	public byte[][] bs = new byte[600][];
	public  byte shu = 124;
	public  byte U = 85;
	public  byte I = 73;
	public  byte D= 68;
	public  byte line = 10;
	public  byte mao = 58;
	public int b=0;
	public byte[] count = new byte[128];
	public int tid;
	public int threadNum = 8;
	public static int maxn = 8000000;
	public SubThread(UnBlockQueue UnBlockQueue,int id,MessageStore messageStore) {
		this.UnBlockQueue = UnBlockQueue;
		count['n']=0;
		count['a']=1;
		count['0']=2;
		count['1']=3;
		count[':']=4;
		this.tid = id;
		this.messageStore = messageStore;
	}
	@Override
	public void run() {
		
		while(true){
			int pos = UnBlockQueue.getIndex(),a=0;
			if(UnBlockQueue.buffer[pos]!=-1){
				while(UnBlockQueue.buffer[pos]!=0){

						pos+=54;
						while(UnBlockQueue.buffer[pos]!=shu)pos++;
						pos++;
						b=0;
						data[a][b++]=UnBlockQueue.buffer[pos];
						if(data[a][0]==U){
							pos+=9;
							int id1=0,id2=0;
							while(UnBlockQueue.buffer[pos]!=shu){
								id1=id1*10+(UnBlockQueue.buffer[pos]-48);
								pos++;
							}
							idval[a][0]=id1;
							byte[] hehe;
							if(id1<=maxn)hehe = messageStore.data[id1];
							else hehe = messageStore.dataMap.get(id1);
							
							pos++;
							while(UnBlockQueue.buffer[pos]!=shu){
								id2=id2*10+(UnBlockQueue.buffer[pos]-48);
								pos++;
							}
							idval[a][1]=id2;
							if(id1==id2){
								pos+=7;
								data[a][b++] = count[UnBlockQueue.buffer[pos]];
								while(UnBlockQueue.buffer[pos++]!=shu);
								while(UnBlockQueue.buffer[pos++]!=shu);
								while(UnBlockQueue.buffer[pos]!=shu){
									data[a][b++]=UnBlockQueue.buffer[pos];
									pos++;
								}
								//data[a][0]=0;//表示
								data[a][b++]=0;
								//直接加上去？
								System.arraycopy(data[a], 2,hehe, data[a][1]*7, 7);
							}
							pos+=2;
						}else if(data[a][0]==I){
							//insert就不用存储了
							bs[a] = new byte[35];
							pos+=14;
							int id1=0;
							while(UnBlockQueue.buffer[pos]!=shu){
								id1=id1*10+(UnBlockQueue.buffer[pos]-48);
								pos++;
							}
							idval[a][0]=id1;
							//
							//first_name
							pos+=21;
							int c=0;
							while(UnBlockQueue.buffer[pos]!=shu){
								bs[a][c++]=UnBlockQueue.buffer[pos];
								pos++;
							}
							bs[a][c]=0;
							//last_name
							c=7;pos+=20;
							while(UnBlockQueue.buffer[pos]!=shu){
								bs[a][c++]=UnBlockQueue.buffer[pos];
								pos++;
							}
							bs[a][c]=0;
							//sex
							c=14;pos+=14;
							while(UnBlockQueue.buffer[pos]!=shu){
								bs[a][c++]=UnBlockQueue.buffer[pos];
								pos++;
							}
							bs[a][c]=0;
							//score
							c=21;pos+=16;
							while(UnBlockQueue.buffer[pos]!=shu){
								bs[a][c++]=UnBlockQueue.buffer[pos];
								pos++;
							}
							bs[a][c]=0;
							
							//score2
							c=28;pos+=17;
							while(UnBlockQueue.buffer[pos]!=shu){
								bs[a][c++]=UnBlockQueue.buffer[pos];
								pos++;
							}
							bs[a][c]=0;
							pos+=2;
							
						}else if(data[a][0]==D){						
							pos+=9;
							int id1=0;
							while(UnBlockQueue.buffer[pos]!=shu){
								id1=id1*10+(UnBlockQueue.buffer[pos]-48);
								pos++;
							}
							idval[a][0]=id1;
							pos+=95;
							
							while(UnBlockQueue.buffer[pos]!=line){						
								pos++;
							}
							pos++;
										
						}
						a++;
						
					 					
				}
				UnBlockQueue.out++;
				
				/*
				 * 数据合并
				 */
				while(!messageStore.merge(tid, data, a, idval,bs));
				
				
			}else{
				while(MessageStore.signal%threadNum!=tid);
				try {
				
					
					Server.sendMessage();
					break;
					//Server.sendMessage();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
	
				
		}
	}
}
