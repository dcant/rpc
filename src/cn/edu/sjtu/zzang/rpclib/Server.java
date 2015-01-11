package cn.edu.sjtu.zzang.rpclib;

import cn.edu.sjtu.zzang.rpclib.Invocater;

public interface Server {
	public void start();
	public void stop();
	public void register(Object impl);
	public void call(Invocater iv);
	public boolean isRunning();
	public int getPort();
}