package practice.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

/**
 * Created by pandechuan on 2018/11/25.
 * 客户端线程
 */

public class ClientThreead implements Runnable {

    @Override
    public void run() {
        System.out.println("已经启动Client...");
        /* 1. 获取通道 */
        SocketChannel sChannel = null;
        try {
            sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        /* 2. 切换非阻塞模式 */
        try {
            sChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* 3. 分配指定大小的缓冲区 */
        ByteBuffer buf = ByteBuffer.allocate(1024);
        /* 4. 发送数据给服务端 */

        Scanner scan = new Scanner(System.in);
        //while (scan.hasNext()) {
        String str = scan.next();

        str = Thread.currentThread().getName() + UUID.randomUUID();
        buf.put((new Date().toString() + "----->" + str).getBytes());
        buf.flip();
        try {
            sChannel.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buf.clear();
        // }


        /* 5. 关闭通道 */
        try {
            sChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scan.close();
        }
    }

}
