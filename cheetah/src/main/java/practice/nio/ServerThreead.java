package practice.nio;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by pandechuan on 2018/11/25.
 * 服务端线程
 */


public class ServerThreead implements Runnable {
    int taskSize = 5;
    // 创建一个线程池
    ExecutorService pool = Executors.newFixedThreadPool(taskSize);

    @Override
    public void run() {
        System.out.println("已经启动Server...");
        try {
            /*
             * 1. 获取通道
             */
            ServerSocketChannel ssChannel = ServerSocketChannel.open();
            /*
             * 2. 切换非阻塞模式
             */
            ssChannel.configureBlocking(false);
            /*3. 绑定连接*/
            ssChannel.bind(new InetSocketAddress(9898));
            /*
             * 4. 获取选择器
             */
            Selector selector = Selector.open();
            /*
             * 5. 将通道注册到选择器上, 并且指定“监听接收事件”
             */
            ssChannel.register(selector, SelectionKey.OP_ACCEPT);
            /*
             * 6. 轮询式的获取选择器上已经“准备就绪”的事件
             */
            while (selector.select() > 0) {
                /*
                 * 7. 获取当前选择器中所有注册的“选择键(已就绪的监听事件)”
                 */
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    /*
                     * 8. 获取准备“就绪”的是事件
                     */
                    SelectionKey sk = it.next();
                    /*
                     * 9. 判断具体是什么事件准备就绪
                     */
                    if (sk.isAcceptable()) {
                        /*
                         * 10. 若“接收就绪”，获取客户端连接
                         */
                        SocketChannel sChannel = ssChannel.accept();
                        /*
                         * 11. 切换非阻塞模式
                         *
                         */
                        sChannel.configureBlocking(false);
                        /*
                         * 12. 将该通道注册到选择器上
                         */
                        sChannel.register(selector, SelectionKey.OP_READ);
                    } else if (sk.isReadable()) {
                        /*
                         * 13. 获取当前选择器上“读就绪”状态的通道
                         */
                        SocketChannel sChannel = (SocketChannel) sk.channel();
                        pool.execute(new WriteThread(sChannel));
                    }

                    /*
                     * 15. 取消选择键 SelectionKey
                     */
                    it.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
