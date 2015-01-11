package cn.edu.sjtu.zzang.rpclib;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import cn.edu.sjtu.zzang.rpclib.Invocater;

public class RpcClient {
	private String ip;
	private int port;
	private Socket socket;
	private ObjectInputStream oin;
	private ObjectOutputStream oout;
	
	public RpcClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	public void initialize() {
		try {
			this.socket = new Socket(ip, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			oout = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void call(Invocater iv) throws ClassNotFoundException, IOException {
		initialize();
		oout.writeObject(iv);
		oout.flush();
		oin = new ObjectInputStream(socket.getInputStream());
		Invocater res = (Invocater)oin.readObject();
		iv.setResult(res.getResult());
	}
}