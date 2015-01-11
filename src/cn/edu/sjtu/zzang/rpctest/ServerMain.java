package cn.edu.sjtu.zzang.rpctest;

import cn.edu.sjtu.zzang.rpclib.RPC;
import cn.edu.sjtu.zzang.rpclib.Server;

public class ServerMain {
	public static void main(String[] args) {
		Server s = new RPC.RpcServer(1991);
		s.register(new RemotePrint());
		s.start();
	}
}