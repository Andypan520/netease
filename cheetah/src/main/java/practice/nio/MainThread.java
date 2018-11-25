package practice.nio;

/**
 * Created by pandechuan on 2018/11/25.
 */
public class MainThread {
    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread threadServer = new Thread(new ServerThreead());
        Thread threadClient = new Thread(new ClientThreead());
        // 启动服务器
        threadServer.start();
        //启动客户端
        threadClient.start();
//        new Thread(new ClientThreead()).start();
//        new Thread(new ClientThreead()).start();
//        new Thread(new ClientThreead()).start();

    }

}

