package cn.edu.sjtu.zzang.rpclib;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import cn.edu.sjtu.zzang.rpclib.Invocater;

public class Listener extends Thread {
	private ServerSocket socket;
	private Server server;

	public Listener(Server server) {
		this.server = server;
	}

	@Override
	public void run() {

		System.out.println("Running at: " + server.getPort());
		try {
			socket = new ServerSocket(server.getPort());
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		while (server.isRunning()) {
			try {
				Socket client = socket.accept();
				TaskService task = new TaskService(server, client);
				task.run();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		try {
			if (socket != null && !socket.isClosed())
				socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static final class TaskService implements Runnable {
		private Socket sock;
		private Server s;
		
		public TaskService(Server s, Socket sock) {
			this.sock = sock;
			this.s = s;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
			ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
			Invocater invo = (Invocater) ois.readObject();
			s.call(invo);
			ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
			oos.writeObject(invo);
			oos.flush();
			oos.close();
			ois.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}	
	}
}