package cn.edu.sjtu.zzang.rpctest;

import cn.edu.sjtu.zzang.rpclib.RPC;

public class ClientMain {
	public static void main(String[] args) {
		Print p = RPC.getProxy(Print.class, "127.0.0.1", 1991);
		String str = p.println("hello");
		System.out.println(str);
	}
}