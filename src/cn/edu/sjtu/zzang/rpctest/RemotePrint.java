package cn.edu.sjtu.zzang.rpctest;

public class RemotePrint implements Print {

	@Override
	public String println(String str) {
		// TODO Auto-generated method stub
		System.out.println(str);
		return ("Server: " + str);
	}

}