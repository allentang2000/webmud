import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.thread.BoundedThreadPool;

/**
 * @author Evans Hsu (evanshsu@gmail.com)
 */
public class RunMud {
	public static void main(String[] args) throws Exception {
		try{
			Server server = new Server();
			BoundedThreadPool threadPool = new BoundedThreadPool();
			threadPool.setMaxThreads(100);
			server.setThreadPool(threadPool);
	
			Connector connector = new SelectChannelConnector();
			connector.setPort(Integer.parseInt(args[1]));
			server.setConnectors(new Connector[] { connector });
	
			WebAppContext context = new WebAppContext();
			context.setContextPath("/" + args[2]);
			context.setWar(args[0]);
			server.addHandler(context);
			
			if (args.length >= 4) {
				for(int i = 3; i < args.length; i++) {
					context = new WebAppContext();
					context.setContextPath("/" + args[i]);
					context.setWar(args[0]);
					server.addHandler(context);
				}
			}
			
			context = new WebAppContext();
			context.setContextPath("/");
			context.setWar(args[0]);
			server.addHandler(context);
			
			server.setStopAtShutdown(true);
			server.setSendServerVersion(true);
	
			server.start();
			server.join();
		}
		catch (Exception e) {
			System.out.println("USAGE: java RunMud [war file] [port] [app name ... ] ");
		}
	}
}
