package com.alibaba.middleware.race.sync;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by wanshao on 2017/5/25.
 */
public class ClientDemoInHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(com.alibaba.middleware.race.sync.Client.class);
    private static MappedByteBuffer raf;
    FileChannel fc;
    // 接收server端的消息，并打印出来
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

       //logger.info("com.alibaba.middleware.race.sync.ClientDemoInHandler.channelRead");
        ByteBuf result = (ByteBuf) msg;
//        byte[] result1 = new byte[result.readableBytes()];
//        result.readBytes(result1);
       // System.out.println("com.alibaba.middleware.race.sync.Server said:" + new String(result1));
        //result.release();
        
       // ctx.writeAndFlush("I have received your messages and wait for next messages");
//        if(Client.astat==0){
//        	byte[] result1 = new byte[result.readableBytes()];
//            result.readBytes(result1);
//            result.release();
//            if(result1.length>3||!((new String(result1)).charAt(0)=='q')){
//            	//System.out.println(new String(result1));
//            	Client.bufCom.add(result1);
//            	Client.len+=result1.length;
//            	Client.astat=1;
//            }
//        }else{
//        	int l=result.readableBytes();
//        	byte[] result1 = new byte[l];
//            result.readBytes(result1);
//            result.release();
//           // System.out.println(new String(result1));
//        	Client.bufCom.add(result1);
//        	Client.len+=l;
//        	if(Client.bufCom.size()%100==0)
//        		logger.info("cd: "+ Client.len);
//            FileWriter fw = new FileWriter("E:/shx/aaaaa.txt");ew String(result1)
//            fw.write(n);
//            fw.close();
      //  	Client.astat=1;
       // }
       
        byte[] result1 = new byte[result.readableBytes()];
       
        result.readBytes(result1);
//        
//        raf = fc.map(MapMode.READ_WRITE, 0L, result1.length);
//        for(int i=0;i<result1.length;i++){        			   			
//			raf.put(result1[i]);
//		}
//        raf.force();
        Thread.sleep(180000);
        result.release();
        logger.info("end");
        System.exit(0); 
        	//Client.compositeByteBuf.ad
        	
        	
        	//Client.fw.write(new String(result1));
        	//Client.fw.close();
            //System.out.println("msg1");
        	//Client.astat=0;
       
//        else if(Client.astat==1&&result1.length<3){
//        	//System.out.println("msg2 "+Client.count);
////        	Client.fcWrite = new RandomAccessFile(Constants.RESULT_HOME+"/"+Constants.RESULT_FILE_NAME,"rw").getChannel();
////        	Client.raf = Client.fcWrite.map(MapMode.READ_WRITE, 0, Client.count);
////    		for(int i = 0;i<Client.count;i++)
////    			Client.raf.put(Client.bb[i]);
//    		
//    		//Client.fw.close();
//    		
//        }
//        else if(result1.length<3){
//        	Thread.sleep(100);
//            String msg1 = "1";
//            ByteBuf encoded = ctx.alloc().buffer(4 * msg1.length());
//            encoded.writeBytes(msg1.getBytes());
//            ctx.write(encoded);
//            ctx.flush();
//            //System.out.println("msg3");
//        }
  }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	 
         
         fc=new RandomAccessFile(Constants.RESULT_HOME+"/"+Constants.RESULT_FILE_NAME,"rw").getChannel();
 		
         
       //  raf = new RandomAccessFile(Constants.RESULT_HOME+"/"+Constants.RESULT_FILE_NAME,"rw");
          // FileChannel fc=new RandomAccessFile(Constants.RESULT_HOME+"/"+Constants.RESULT_FILE_NAME,"rw").getChannel();
        logger.info("com.alibaba.middleware.race.sync.ClientDemoInHandler.channelActive");
        String msg = "I am prepared to receive messages,Please send send send send send send";
        ByteBuf encoded = ctx.alloc().buffer(4 * msg.length());
        encoded.writeBytes(msg.getBytes());
        ctx.write(encoded);
        ctx.flush();
    }
}
