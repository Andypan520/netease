package common;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by pandechuan on 2018/1/7/007 16:58
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 获取某个目录下的子文件名
     *
     * @param dirName
     * @return
     * @throws IOException
     */
    public static List<String> getSubDirNames(String dirName) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dirName))) {
            return stream.map(String::valueOf)
                    .sorted()
                    .collect(Collectors.toList());
        }
    }

    /**
     * 读取某个文件
     *
     * @param fileName
     * @return
     */
    public static List<String> readAllLines(String fileName) {
        List<String> lines = Lists.newArrayList();
        try {
            lines = Files.readAllLines(Paths.get(fileName));
        } catch (Exception e) {
            logger.error("readAllLines error!");
        }
        return lines;
    }

    /**
     * 从某个目录下按照最大深度查找模糊匹配的文件名
     *
     * @param root
     * @param maxDepth
     * @param containContent
     * @throws IOException
     */
    public static String findVagueFileNameWithDepth(String root, int maxDepth, String containContent) throws IOException {
        Path start = Paths.get(root);
        try (Stream<Path> stream = Files.find(start, maxDepth, (path, attr) ->
                String.valueOf(path).endsWith(containContent))) {
            String joined = stream
                    .sorted()
                    .map(String::valueOf)
                    .collect(Collectors.joining("; "));
            return joined;
        }
    }

    private static void write(String file, String content) throws IOException {
        Path path = Paths.get(file);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(content);
        }
    }

    /**
     *delete the content of text file without deleting itself
     * @param file
     */
    private static void clear(File file){
        try (PrintWriter printWriter = new PrintWriter(file);){
            printWriter.write("");
        }catch (IOException e){
            logger.error("clear file {} error",file.getName(),e);
        }
    }
}
