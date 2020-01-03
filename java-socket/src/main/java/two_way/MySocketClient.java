package two_way;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * <b><code>one_way.MySocketClient</code></b>
 * <p/>
 * 双工通信：socket的客户端
 * <p/>
 * <b>Creation Time:</b> 2019/12/9 18:05.
 *
 * @author cgx
 * @since KnowledgeRecords
 */
public class MySocketClient {

    public static final String SERVERHOST = "127.0.0.1";

    public static final int PORT = 19998;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        System.out.println("this is socket client");

        Socket socket = new Socket(SERVERHOST, PORT);

        //向服务端发送数据
        OutputStream outputStream = socket.getOutputStream();
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();

        outputStream.write(message.getBytes("utf-8"));
        outputStream.flush();
        //终止发送数据，客户端现在只接收数据
        socket.shutdownOutput();

        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        StringBuilder stringBuilder = new StringBuilder();

        while((len = inputStream.read(bytes))!= -1){
            stringBuilder.append(new String(bytes,0,len,"utf-8"));
            System.out.println("客户端正在接受信息");
        }
        System.out.println("来自服务器的回显:"+stringBuilder.toString());

        outputStream.close();
        inputStream.close();
        scanner.close();
        socket.close();


    }

}
