package practice.nio;

import org.junit.jupiter.api.Test;

import java.awt.image.Kernel;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;

/**
 * Created by pandechuan on 2018/11/25.
 * 利用通道完成文件的复制（非直接缓冲区）
 */
public class NIODemo1 {

    private static final String PNG_1 = "/Users/pandechuan/Desktop/1.png";
    private static final String PNG_2 = "/Users/pandechuan/Desktop/2.png";
    private static final String PNG_3 = "/Users/pandechuan/Desktop/3.png";
    private static final String PNG_4 = "/Users/pandechuan/Desktop/4.png";
    private static final String PNG_5 = "/Users/pandechuan/Desktop/5.png";
    private static final String TXT_1 = "/Users/pandechuan/Desktop/1.txt";
    private static final String TXT_2 = "/Users/pandechuan/Desktop/2.txt";
    /***********************************  文件NIO  *************************************************/
    /**
     * 通道（Channel）：用于源节点与目标节点的连接。在 Java NIO 中负责缓冲区中数据的传输。
     * Channel 本身不存储数据，因此需要配合缓冲区进行传输。
     */
    @Test
    public void test1() {

        long start = System.currentTimeMillis();

        FileInputStream fis = null;
        FileOutputStream fos = null;

        // ①获取通道
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            //1G+的exe文件
            fis = new FileInputStream("/Users/pandechuan/Downloads/ideaIU-2018.2.6.dmg");

            File destFile = new File("/Users/pandechuan/Downloads/mytmp/ideaIU-2018.2.6.dmg");

            fos = new FileOutputStream(destFile);

            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            // ②分配指定大小的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(6000);// 1526ms

            // ③将通道中的数据存入缓冲区中
            while (inChannel.read(buf) != -1) {

                // 切换读取数据的模式
                buf.flip();

                // ④将缓冲区中的数据写入通道中
                outChannel.write(buf);
                // 清空缓冲区
                buf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outChannel != null) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inChannel != null) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("耗费时间为：" + (end - start));

    }

    @Test
    /**
     * 使用直接缓冲区完成文件的复制(内存映射文件) JDK1.7 NIO.2版
     */
    public void test2() throws IOException {
        long start = System.currentTimeMillis();
//        FileChannel inChannel = FileChannel.open(Paths.get("/Users/pandechuan/Desktop/1.png"),
//                StandardOpenOption.READ);

        FileChannel inChannel = FileChannel.open(Paths.get("/Users/pandechuan/Desktop/2.png"),
                StandardOpenOption.READ);


        FileChannel outChannel = FileChannel.open(
                Paths.get("/Users/pandechuan/Desktop/5.png"),
                StandardOpenOption.WRITE,
                StandardOpenOption.READ,
                StandardOpenOption.CREATE);

        // 内存映射文件
        MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0,
                inChannel.size());
        MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0,
                inChannel.size());

        // 直接对缓冲区进行数据的读写操作
        byte[] dst = new byte[inMappedBuf.limit()];
        inMappedBuf.get(dst);
        outMappedBuf.put(dst);

        inChannel.close();
        outChannel.close();

        long end = System.currentTimeMillis();
        System.out.println("耗费时间为：" + (end - start));

    }


    /***********************************  通道数据传输  *************************************************/
    @Test
    /**
     * 通道之间的数据传输(直接缓冲区)
     */
    public void test3() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get(PNG_3),
                StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(
                Paths.get(PNG_4), StandardOpenOption.WRITE,
                StandardOpenOption.READ, StandardOpenOption.CREATE);

        inChannel.transferTo(0, inChannel.size(), outChannel);
//        outChannel.transferFrom(inChannel, 0, inChannel.size());

        inChannel.close();
        outChannel.close();
    }

    /***********************************  分散和聚集  *************************************************/
    @Test
    /**
     *   分散(Scatter)与聚集(Gather)
     * 	 分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
     * 	  聚集写入（Gathering Writes）：将多个缓冲区中的数据聚集到通道中
     */
    public void test4() throws IOException {
        RandomAccessFile raf1 = new RandomAccessFile(TXT_1, "rw");

        // 1. 获取通道
        FileChannel channel1 = raf1.getChannel();

        // 2. 分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        // 3. 分散读取
        ByteBuffer[] bufs = {buf1, buf2};
        channel1.read(bufs);

        for (ByteBuffer byteBuffer : bufs) {
            /**
             * 通过buffer.flip();这个语句，就能把buffer的当前位置更改为buffer缓冲区的第一个位置。
             */
            byteBuffer.flip();
        }

        System.out.println("BUF-1========" + new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println("-----------------");
        System.out.println("BUF-2********" + new String(bufs[1].array(), 0, bufs[1].limit()));
        raf1.close();

        // 4. 聚集写入
        RandomAccessFile raf2 = new RandomAccessFile(TXT_2, "rw");
        FileChannel channel2 = raf2.getChannel();

        channel2.write(bufs);
        raf2.close();
    }

    /***********************************  编码与解码  *************************************************/
    @Test
    /**
     * 通道之间的数据传输(直接缓冲区)
     */
    public void test5() throws IOException {
        Map<String, Charset> map = Charset.availableCharsets();

        map.forEach((k, v) -> {
            System.out.println(k + "=" + v);
        });

    }

    // 字符集
    @Test
    public void test6() throws IOException {
        Charset cs1 = Charset.forName("UTF-8");

        // 获取编码器
        CharsetEncoder ce = cs1.newEncoder();

        // 获取解码器
        CharsetDecoder cd = cs1.newDecoder();

        CharBuffer cBuf = CharBuffer.allocate(1024);
        cBuf.put("一二三四！#");
        //cBuf.flip();

        // 编码
        ByteBuffer bBuf = ce.encode(cBuf);

        for (int i = 0; i < 12; i++) {
            System.out.println(bBuf.get());
        }

        // 解码
//        bBuf.flip();
//        CharBuffer cBuf2 = cd.decode(bBuf);
//        System.out.println(cBuf2.toString());
//
//        System.out.println("------------------------------------------------------");
//        // 乱码
//        Charset cs2 = Charset.forName("GBK");
//        bBuf.flip();
//        CharBuffer cBuf3 = cs2.decode(bBuf);
//        System.out.println(cBuf3.toString());
    }


    public static void main(String[] args) {
        NIODemo1 nioDemo1 = new NIODemo1();
        nioDemo1.test1();
    }

}
