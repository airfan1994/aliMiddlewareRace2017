package com.alibaba.middleware.race.sync;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by wanshao on 2017/5/25.
 */
public class Client {

    private final static int port = Constants.SERVER_PORT;
    // idle时间
    private static String ip;
    private EventLoopGroup loop = new NioEventLoopGroup();
    public static int astat=0;
    public static int count = 0;
    public static FileWriter fw;
    public static  ArrayList<byte[]> bufCom=new ArrayList<>();
    public static int len=0;
    public static byte[] resultbyte;
    public static Socket socket;
    public static void main(String[] args) throws Exception {
        initProperties();
       
        FileWriter fw = new FileWriter(Constants.RESULT_HOME+"/"+Constants.RESULT_FILE_NAME);
        fw.close();
        // 从args获取server端的ip
        ip = args[0];
        Thread.sleep(3000);
        socket = new Socket(ip, port);
        
        byte[] b=new byte[100000];
		InputStream input = socket.getInputStream();
		FileChannel fc = new RandomAccessFile(Constants.RESULT_HOME+"/"+Constants.RESULT_FILE_NAME,"rw").getChannel();
		MappedByteBuffer raf = fc.map(MapMode.READ_WRITE, 0L, 38334025);

		while(input.available()==0);


		while(true){
			int read = input.read(b) ;
             if(read==-1){
               break;
             }
             
             raf.put(b, 0, read);          
          }


		System.exit(0);

        
    }

    /**
     * 初始化系统属性
     */
    private static void initProperties() {
//    	try {
//        	//ystem.out.println(Constants.RESULT_HOME+"/"+Constants.RESULT_FILE_NAME);
//			fw = new FileWriter(Constants.RESULT_HOME+"/"+Constants.RESULT_FILE_NAME,true);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        System.setProperty("middleware.test.home", Constants.TESTER_HOME);
        System.setProperty("middleware.teamcode", Constants.TEAMCODE);
        System.setProperty("app.logging.level", Constants.LOG_LEVEL);
    }

    /**
     * 连接服务端
     *
     * @param host
     * @param port
     * @throws Exception
     */
    public int  connect(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                   // ch.pipeline().addLast(new IdleStateHandler(1, 0, 0));
                  //  ch.pipeline().addLast(new ClientIdleEventHandler());
                	ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4,false));
                    ch.pipeline().addLast(new ClientDemoInHandler());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
            return 1;
        }
        catch(Exception e)
        {
        	return 0;
        }
        finally {
            workerGroup.shutdownGracefully();
        }
    }


}
