package cn.edu.sjtu.zzang.rpclib;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class RPC {
	public static <T> T getProxy(final Class<T> clazz,String host,int port) {
		final RpcClient client = new RpcClient(host,port);
		InvocationHandler handler = new InvocationHandler() {

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Invocater invo = new Invocater();
				invo.setInterfaces(clazz.getName());
				invo.setMethod(method.getName());
				invo.setParamsType(method.getParameterTypes());
				invo.setParams(args);
				client.call(invo);
				return invo.getResult();
			}
		};

		T t = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] {clazz}, handler);
		return t;
	}
	
	public static class RpcServer implements Server{
		private int port = 1991;
		private Listener listener; 
		private AtomicBoolean isRunning = new AtomicBoolean(false);
		
		public RpcServer() {}
		public RpcServer(int port) {
			this.port = port;
		}

		/**
		 * @return the port
		 */
		public int getPort() {
			return port;
		}

		/**
		 * @param port the port to set
		 */
		public void setPort(int port) {
			this.port = port;
		}

		private ConcurrentMap<String ,Object> service = new ConcurrentHashMap<String, Object>();
		
		
		@Override
		public void call(Invocater invo) {
			Object obj = service.get(invo.getInterfaces());
			if (obj != null) {
				try {
					Method m = obj.getClass().getMethod(invo.getMethod(), invo.getParamsType());
					Object result = m.invoke(obj, invo.getParams());
					invo.setResult(result);
				} catch (Throwable th) {
					th.printStackTrace();
				}
			} else {
				throw new IllegalArgumentException("class not found!");
			}
		}

		@Override
		public void register(Object impl) {
			try {
				Class<?>[] interfaces = impl.getClass().getInterfaces();
				if (interfaces == null) {
					throw new IllegalArgumentException("Class must implement an interface!");
				}
				
				this.service.put(interfaces[0].getName(), impl);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

		@Override
		public void start() {
			System.out.println("Starting...");
			listener = new Listener(this);
			isRunning.set(true);
			listener.start();
		}

		@Override
		public void stop() {
			isRunning.set(false);
		}

		@Override
		public boolean isRunning() {
			return isRunning.get();
		}
	}
}