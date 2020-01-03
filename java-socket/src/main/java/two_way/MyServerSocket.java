package two_way;

import com.sun.security.ntlm.NTLMException;
import com.sun.security.ntlm.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <b><code>one_way.MyServerSocket</code></b>
 * <p/>
 * 双向通信: socket的服务端
 * <p/>
 * <b>Creation Time:</b> 2019/12/9 17:28.
 *
 * @author cgx
 * @since KnowledgeRecords
 */
public class MyServerSocket {

    public static final int PORT = 19998;

    public static void main(String[] args) throws NTLMException, IOException {
        System.out.println("this is socket server");
        ServerSocket serverSocket = new ServerSocket(PORT);
        //建立连接
        Socket socket = serverSocket.accept();

        //获得客户端传来的数据
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        StringBuilder sb = new StringBuilder();
        int len = 0;

        while((len = inputStream.read(bytes)) != -1){
            sb.append(new String(bytes,0,len,"utf-8"));
            System.out.println("服务端接收数据中....");
        }
        System.out.println("服务端收到了: " + sb.toString());

        socket.shutdownInput();
        //接到数据后，回传数据
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(sb.toString().getBytes("utf-8"));

        socket.shutdownOutput();
        socket.close();
        serverSocket.close();

    }

}
