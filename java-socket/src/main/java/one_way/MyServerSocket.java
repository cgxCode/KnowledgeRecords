package one_way;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <b><code>one_way.MyServerSocket</code></b>
 * <p/>
 * 单向通信: socket的服务端
 * <p/>
 * <b>Creation Time:</b> 2019/12/9 17:28.
 *
 * @author cgx
 * @since KnowledgeRecords
 */
public class MyServerSocket {

    /**
     * The Server socket.
     */
    private ServerSocket serverSocket;

    /**
     * The Socket.
     */
    private Socket socket;

    /**
     * The Port.
     */
    private int port;

    /**
     * The Input stream.
     */
    private InputStream inputStream;

    /**
     * The constant MAX_BUFFER_SIZE.
     */
    private static final int MAX_BUFFER_SIZE = 1024;

    /**
     * Gets port.
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets port.
     */
    public void setPort() {
        this.port = port;
    }

    /**
     * Instantiates a new My server socket.
     *
     * @param port the port
     */
    public MyServerSocket(int port) {
        this.port = port;
    }

    /**
     * Run server single.
     *
     * @throws IOException the io exception
     */
    public void runServerSingle() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
        System.out.println("base socket server started. ");
        this.socket = serverSocket.accept();
        this.inputStream = this.socket.getInputStream();
        byte[] readBytes = new byte[MAX_BUFFER_SIZE];
        int msgLen;
        StringBuilder stringBuilder = new StringBuilder();

        while ((msgLen = inputStream.read(readBytes)) != -1) {
            stringBuilder.append(new String(readBytes, 0, msgLen, "utf-8"));
        }

        System.out.println("get message from client: " + stringBuilder);

        inputStream.close();
        socket.close();
        serverSocket.close();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        MyServerSocket myServerSocket = new MyServerSocket(19998);
        myServerSocket.runServerSingle();
    }
}
