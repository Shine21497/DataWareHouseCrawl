package proxy;

import org.apache.http.HttpHost;
import spider.AmazonMovieSpider;
import spider.MovieEntity;
import spider.MysqlPipline;
import spider.UserAgents;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ProxySpider implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3)
            .setUserAgent(UserAgents.getuseragent())
            ;
    public ProxySpider()
    {
        super();
    }
    @Override
    public void process(Page page) {
        try {
            Thread.sleep(new Random().nextInt(513));
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        List<String> ips=page.getHtml().xpath("//table[@id='ip_list']/tbody//tr//td[2]/text()").all();
        List<String> ports=page.getHtml().xpath("//table[@id='ip_list']/tbody//tr//td[3]/text()").all();
        page.putField("ips",ips);
        page.putField("ports",ports);





    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        ArrayList<String> urls=new ArrayList<String>();
        for(int i=1;i<=10;i++)
            urls.add("http://www.xicidaili.com/nn/"+i);
        Spider.create(new ProxySpider()).startUrls(urls).addPipeline(new ProxyPipline()).thread(10).run();


    }
    public static void startCrawlIP()
    {
        final ArrayList<String> urls=new ArrayList<String>();
        for(int i=1;i<=10;i++)
            urls.add("http://www.xicidaili.com/nn/"+i);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Spider.create(new ProxySpider()).startUrls(urls).addPipeline(new ProxyPipline()).thread(10).run();
            }
        }).start();

    }
}
