package com.alibaba.middleware.race.sync;

import java.awt.geom.CubicCurve2D;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 服务器类，负责push消息到client Created by wanshao on 2017/5/25.
 */
public class Server {

    // 保存channel
   // private static Map<String, Channel> map = new ConcurrentHashMap<String, Channel>();
    // 接收评测程序的三个参数
    private static String schema;
    private static Map tableNamePkMap;
	private static int startid = 0;
	private static int endid = 0;
	public static int maxn=8000000;
	private static byte[][] dataarray ;


	private static int keycount = 5;
	public static int wancheng = 0;
	public static int messageid = 0; 

	public static ServerSocket serverSocket;
	public static Server server;
	public static String ipstr;
//    public static Map<String, Channel> getMap() {
//        return map;
//    }
//
//    public static void setMap(Map<String, Channel> map) {
//        Server.map = map;
//    }

    public static void main(String[] args) throws Exception {
    	
    	serverSocket = new ServerSocket(5527);
    	
    	setBytes(args);
        initProperties();
        Kernel kernel = new Kernel();
        kernel.deal();
      
        

    }
    public static void sendMessage(){
    	try {
    	
    	
    		//HashMap<Integer, byte[]> map = MessageStore.dataMap;
    		HashMap4 map = MessageStore.dataMap;
    		Socket socket = serverSocket.accept();
    		OutputStream output = socket.getOutputStream();
    		byte[][] datalists = MessageStore.data;
    		byte[] message = new byte[5000000];
    		int count = 0;
    		while(true){
        	    int mn=0;
            	while(datalists[startid]==null||datalists[startid][0]==0) 
            	{
            		startid++;
            		if(startid>=endid){
            			mn=1;
            			break;
            		}
            	}
            	if(mn==1) break;
            	
            	if(count>=4900000){
            		//该发了
            		output.write(message,0,count);
            		count=0;
            	}
            	
            	(""+startid).getBytes(0, 7, message, count);
            	//for(int h = 0;h<str.length;h++)	message[count++] = str[h];
            	count+=7;
            	//一条记录的长度最多50
            	
            	
            	for(int y = 0;y<keycount;y++)
            	{
            		message[count++] = (byte)'\t';
            		int h = y*7;
            		while(datalists[startid][h]!=0)
            		{
            			message[count++] = datalists[startid][h];
            			h++;
            		}
            	}
            	message[count++] = (byte)('\n');
            	startid++;
            	if(startid>=endid)
        			break;
    		}
//    		logger.info("xixi "+count);
//    		
//    		logger.info("xixi2 "+count);
    		
    		output.write(message,0,count);
    		
    		socket.close();
		} catch (Exception e) {
			
		}
    	

		
    }
    
    public static void  setBytes(String[] args1){
    	
    	startid = Integer.parseInt(args1[2]);
    	endid = Integer.parseInt(args1[3]);
    	messageid = startid+1;
    }

    public static int getkeycount()
    {
    	return keycount;
    }
   
    public static byte[][] getdataarray()
    {
    	return dataarray;
    }

    public static long getstart(){
    	return startid;
    }
    public static long getend(){
    	return endid;
    }

    private static void printInput(String[] args) {
        

    }

    /**
     * 初始化系统属性
     */
    private static void initProperties() {
        System.setProperty("middleware.test.home", Constants.TESTER_HOME);
        System.setProperty("middleware.teamcode", Constants.TEAMCODE);
        System.setProperty("app.logging.level", Constants.LOG_LEVEL);
    }


 
}



