package com.alibaba.middleware.race.sync;

import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by wanshao on 2017/5/25.
 */
public class ClientIdleEventHandler extends ChannelDuplexHandler {

    Logger logger = LoggerFactory.getLogger(ClientIdleEventHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) msg;
            // 维持空闲状态超时间隔作为心跳间隔，server端检查是否要发送批次数据
            if (Client.astat==1&&e.state() == IdleState.READER_IDLE) {
                logger.warn("No message from server, shut down client");

                //System.out.println(len);
              FileWriter fw = new FileWriter(Constants.RESULT_HOME+"/"+Constants.RESULT_FILE_NAME);
              fw.close();
                FileChannel fc=new RandomAccessFile(Constants.RESULT_HOME+"/"+Constants.RESULT_FILE_NAME,"rw").getChannel();
        		MappedByteBuffer raf = fc.map(MapMode.READ_WRITE, 0L, Client.len);
        		//byte[] result1 = new byte[len];
        		System.out.println(Client.bufCom.size());
        		for(int i=0;i<Client.bufCom.size();i++){        			   			
        			raf.put(Client.bufCom.get(i));
        		}
        		//.readBytes(result1);
        		
        		fc.close();
                ctx.close();
                System.out.println("结束: "+System.currentTimeMillis());
            }
        }
    }
}
