package cn.edu.sjtu.zzang.rpclib;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Invocater implements Serializable {
	private static final long serialVersionUID = -9047704694040981268L;
	
	private String interfaces;
	private String method;
	private Class<?>[] paramsType;
	private Object[] params;
	private Object result;
	
	public String getInterfaces() {
		return this.interfaces;
	}
	
	public void setInterfaces(String interfaces) {
		this.interfaces = interfaces;
	}
	
	public String getMethod() {
		return this.method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	public Class<?>[] getParamsType() {
		return paramsType;
	}
	
	public void setParamsType(Class<?>[] paramsType) {
		this.paramsType = paramsType;
	}
	
	public Object[] getParams() {
		return this.params;
	}
	
	public void setParams(Object[] params) {
		this.params = params;
	}
	
	public Object getResult() {
		return this.result;
	}
	
	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return interfaces + "." + method + "(" + Arrays.toString(params) + ")";
	}
}