package com.alibaba.middleware.race.sync;



public class MessageStore {

	public int threadNum = 8;
	public static int maxn = 8000000;
	public static byte[][] data ;
	public int turn = 0;
	public static volatile int signal = 0;
	public  byte shu = 124;
	public  byte U = 85;
	public  byte I = 73;
	public  byte D= 68;
	public  byte line = 10;
	public  byte mao = 58;
	//public static HashMap<Integer,byte[]> dataMap = new HashMap<>();
	public static HashMap4 dataMap = new HashMap4();
	private byte[] empty = new byte[35];
	public MessageStore() {
		data = new byte[maxn+1][];
	}
	
	public boolean merge(int tid,byte[][] bs,int a,int[][] idval,byte[][] bytes){
		if(tid==signal%threadNum){
			for(int b=0;b<a;b++){
				
				if(bs[b][0]==U&&idval[b][0]!=idval[b][1]){					
					if(idval[b][0]<=maxn){
						if(idval[b][1]<=maxn){
							//都小
							data[idval[b][1]]=data[idval[b][0]];
							data[idval[b][0]]=empty;
						}else{
							//前小后大
							dataMap.put(idval[b][1], data[idval[b][0]]);
							data[idval[b][0]]=empty;
						}
					}else{						
						if(idval[b][1]<=maxn){
							//前大后小
							data[idval[b][1]]=dataMap.remove(idval[b][0]);
						}else{
							//都大
							dataMap.put(idval[b][1], dataMap.remove(idval[b][0]));
						}											
					}   	

			}else if(bs[b][0]==I){
				if(idval[b][0]>maxn){
					dataMap.put(idval[b][0], bytes[b]);
				}else{
					data[idval[b][0]]=bytes[b];
				}
			}else if(bs[b][0]==D){
				if(idval[b][0]<=maxn) data[idval[b][0]]=empty;
				else dataMap.remove(idval[b][0]);
			}					
		}
			
			signal++;
			return true;
		}
		return false;
	}
	
}



