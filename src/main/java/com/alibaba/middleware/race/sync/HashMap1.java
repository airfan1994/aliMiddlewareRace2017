package com.alibaba.middleware.race.sync;

public class HashMap1 {
	private int mapsize = 2097152;
	cunchu[] data = new cunchu[mapsize];
	static int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
	public byte[] get(int id){
		cunchu cc = data[hash(id)&2097151];
		while(cc.id!=id) cc=cc.next;
		return cc.data;
	}
	public byte[] remove(int id){
		//可以优化;
		cunchu cc = data[hash(id)&2097151];
		byte[] data1 = cc.data;
		if(cc.id==id) {
			data[id&2097151] = cc.next;
			return data1;
		}
		else{
			while(cc.next.id!=id) cc=cc.next;
			data1 = cc.next.data;
			cunchu cc2=cc.next.next;
			cc.next.next = cc2;
			return data1;
		}
	}
	public void put(int id,byte[] bytes){
		cunchu cc = data[hash(id)&2097151];
		if(cc==null)
		{
			cunchu cc1 = new cunchu(id,bytes);
			data[id&2097151] = cc1;
		}
		else{
			while(cc.next!=null&&cc.id!=id) cc = cc.next;
			if(cc.id==id){
				cc.data=bytes;
			}
			else
			{
				cunchu cc1 = new cunchu(id,bytes);
				cc.next = cc1;
			}
		}
	}
}
class cunchu{
	public int id;
	public byte[] data;
	public cunchu(int id,byte[] data)
	{
		this.id = id;
		this.data = data;
	}
	public cunchu next;
}
