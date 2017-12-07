package proxy;

import dao.Dao;
import org.apache.commons.io.IOUtils;
import spider.AmazonMovieSpider;
import spider.MovieEntity;
import spider.MysqlPipline;
import us.codecraft.webmagic.*;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProxyPipline implements Pipeline {
    public ProxyPipline()
    {
        super();
    }
    @Override
    public void process(ResultItems resultItems, Task task) {

        List<String> ips=(List<String>) resultItems.get("ips");
        List<String> ports=(List<String>) resultItems.get("ports");
        if(ips!=null && ports!=null) {
           for(int i=0;i<ips.size();i++)
           {
               System.out.println("第"+i+"个ip");
              checkIPAndCrawl(ips.get(i),ports.get(i));
           }

        }

    }
    public void checkIPAndCrawl(String ip,String port)
    {
        try {
            final URL url = new URL("http://www.baidu.com");
            // 创建代理服务器
            InetSocketAddress addr = null;
            addr = new InetSocketAddress(ip, Integer.parseInt(port));
            java.net.Proxy proxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, addr); // http 代理
            URLConnection conn = url.openConnection(proxy);
            InputStream in = conn.getInputStream();
            String s = IOUtils.toString(in);
            //System.out.println(s);
            if (s.indexOf("百度") > 0) {
                Dao.getInstance().insertProxy(ip,Integer.parseInt(port));
            }
        }catch(IOException e)
        {
            //e.printStackTrace();
        }
    }
}
