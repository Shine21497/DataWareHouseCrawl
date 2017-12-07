package urls.bigfileutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: okey
 * Date: 14-4-1
 * Time: 下午6:03
 * To change this template use File | Settings | File Templates.
 */
public class BuildData {
    public static void main(String[] args) throws Exception {
        File file = new File("F:\\temp\\splite\\result7");
        FileInputStream fis = null;
        try {
            ReadFile readFile = new ReadFile();
            fis = new FileInputStream(file);
            long available = file.length();
            System.out.println(available);
            int maxThreadNum = 80;
            // 线程粗略开始位置
            long i = available / maxThreadNum;
            for (int j = 0; j < maxThreadNum; j++) {
                // 计算精确开始位置
                long startNum = j == 0 ? 0 : readFile.getStartNum(file, i * j);
                long endNum = j + 1 < maxThreadNum ? readFile.getStartNum(file, i * (j + 1)) : -2;
                // 具体监听实现
                Myreadfilelistener listeners = new Myreadfilelistener("utf-8");
                new ReadFileThread(listeners, startNum, endNum, file.getPath()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

