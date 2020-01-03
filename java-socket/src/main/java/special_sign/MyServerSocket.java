package special_sign;

import com.sun.security.ntlm.NTLMException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(inputStream);
        while(scanner.hasNextLine()){
            System.out.println("get info from client: "+ scanner.nextLine());
        }

        inputStream.close();
        socket.close();
    }

}
