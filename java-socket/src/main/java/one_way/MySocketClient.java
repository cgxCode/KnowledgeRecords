package one_way;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * <b><code>one_way.MySocketClient</code></b>
 * <p/>
 * 单向通信：socket的客户端
 * <p/>
 * <b>Creation Time:</b> 2019/12/9 18:05.
 *
 * @author cgx
 * @since KnowledgeRecords
 */
public class MySocketClient {

    /**
     * The Server host.
     */
    private String serverHost;

    /**
     * The Server port.
     */
    private int serverPort;

    /**
     * The Socket.
     */
    private Socket socket;

    /**
     * The Output stream.
     */
    private OutputStream outputStream;

    /**
     * Instantiates a new My socket client.
     *
     * @param serverHost the server host
     * @param serverPort the server port
     */
    public MySocketClient(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    /**
     * Connect server.
     *
     * @throws IOException the io exception
     */
    public void connectServer() throws IOException {
        this.socket = new Socket(this.serverHost, this.serverPort);
        this.outputStream = socket.getOutputStream();
    }

    /**
     * Send single.
     *
     * @param message the message
     * @throws IOException the io exception
     */
    public void sendSingle(String message) throws IOException {
        this.outputStream.write(message.getBytes("utf-8"));
        this.outputStream.close();
        this.socket.close();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        MySocketClient mySocketClient = new MySocketClient("127.0.0.1", 19998);
        mySocketClient.connectServer();
        mySocketClient.sendSingle("Hi from test");
    }

}
