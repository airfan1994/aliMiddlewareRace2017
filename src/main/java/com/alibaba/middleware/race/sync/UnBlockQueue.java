package com.alibaba.middleware.race.sync;

import io.netty.internal.tcnative.Buffer;

public class UnBlockQueue {

	public int BUFFER_SIZE = 256;//256段，每段8192*3个字节
	public byte[] buffer;
	public long p1,p2,p3,p4,p5,p6,p7;//cache line padding
	public volatile int in;
	public long p8,p9,p10,p11,p12,p13,p14;//cache line padding
	public volatile int out;
	public long p15,p16,p17,p18,p19,p20,p21;//cache line padding
	public int batchNum = 8192*3;
    public UnBlockQueue() {
        buffer = new byte[BUFFER_SIZE*batchNum];
        in = out = 0;
    }

    
   
    
    public int putIndex(){
    	while((in-out)>=BUFFER_SIZE);
    	return (in&(255))*batchNum;
    }
    
    public int getIndex(){
    	while(out>=in);
    	return (out&(255))*batchNum;
    }
    
    public long preventFromOptimization(){
		return p1+p2+p3+p4+p5+p6+p7+p8+p9+p10+p11+p12+p13+p14+p15+p16+p17+p18+p19+p20+p21;
	}
}
