import edu.csuft.chentao.netty.NettyCollections;
import edu.csuft.chentao.netty.Server;

/**
 * 
 */

/**
 * @author csuft.chentao
 *
 *         2016年12月10日 上午11:01:12
 */
public class Main {
	public static void main(String[] args) {
		// 在子线程中读取数据
		NettyCollections.readMessageFromDatabase();
		Server server = new Server();
		server.runServer(10101);
	}
}