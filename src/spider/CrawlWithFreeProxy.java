package spider;

import dao.Dao;
import proxy.HttpClientDownloaderAd;
import proxy.ProxySpider;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class CrawlWithFreeProxy {
    private static File file;
    private static PrintWriter pw;
    static {
        file=new File("F:\\datawarehousehtmls\\log");
        if(!file.exists())
        {
            try {
                file.createNewFile();

            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        try {
            pw=new PrintWriter(new FileOutputStream(file));

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static int line=0;
    public static void main(String[] args) {
        try {
            FileInputStream f = new FileInputStream("F:\\datawarehouseurls\\testurls2");
            final Scanner scanner=new Scanner(f);
            Vector result=Dao.getInstance().selectProxy();
            Proxy[] ips=null;
            while(scanner.hasNext()) {
                if (result.size() > 10) {

                    final SpiderListener listener = new SpiderListener() {
                        @Override
                        public void onSuccess(Request request) {
                            System.out.println("success");
                        }

                        @Override
                        public void onError(Request request) {
                            System.out.println("error");
                            Dao.getInstance().insertUncrawledURL(request.getUrl());
                        }
                    };
                    SpiderListener spiderlistener = new SpiderListener() {
                        @Override
                        public void onSuccess(Request request) {
                            System.out.println("spider success");
                            startAnotherSpider(scanner);
                            myfilelog("success line" + line);

                        }

                        @Override
                        public void onError(Request request) {
                            System.out.println("spider error" + "now line" + line);
                            myfilelog("spider error" + "now line" + line);


                        }
                    };
                    final ArrayList<SpiderListener> listeners = new ArrayList<SpiderListener>();
                    listeners.add(spiderlistener);
                    Dao.getInstance().deleteProxy();
                    ProxySpider.startCrawlIP();
                    ips = new Proxy[result.size() + 1];
                    for (int i = 0; i < result.size(); i++) {
                        Vector lineof = (Vector) result.get(i);
                        ips[i] = new Proxy((String) lineof.get(1), (Integer) lineof.get(2));
                    }
                    ips[result.size()] = new Proxy("115.159.34.252", 8080);
                    final HttpClientDownloaderAd httpClientDownloaderad = new HttpClientDownloaderAd(listener);
                    httpClientDownloaderad.setProxyProvider(SimpleProxyProvider.from(ips));
                    int j = 0;
                    while (j < 5) {
                        myfilelog("start"+j);
                        final ArrayList<String> urls = new ArrayList<String>();
                        for (int i = 0; i < 20; i++) {
                            if (!scanner.hasNext())
                                break;
                            urls.add(scanner.nextLine());
                            line++;
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Spider.create(new AmazonMovieSpider()).setSpiderListeners(listeners).setDownloader(httpClientDownloaderad).startUrls(urls).addPipeline(new MysqlPipline()).thread(1).run();
                            }
                        }).start();
                        j++;
                    }
                } else {
                    myfilelog("stoped little at line" + line);
                    Thread.sleep(50000);
                    result=Dao.getInstance().selectProxy();
                    //ProxySpider.startCrawlIP();
                }
            }
        }catch(Exception e)
        {

            e.printStackTrace();

        }


    }
    public static void startAnotherSpider(final Scanner scanner) {
        if (scanner.hasNext()) {

            Vector result=Dao.getInstance().selectProxy();
            Proxy[] ips=null;
            while(true) {
                if (result.size() > 10) {
                    SpiderListener listener = new SpiderListener() {
                        @Override
                        public void onSuccess(Request request) {
                            System.out.println("success");
                        }

                        @Override
                        public void onError(Request request) {
                            System.out.println("error");
                            Dao.getInstance().insertUncrawledURL(request.getUrl());
                        }
                    };
                    SpiderListener spiderlistener = new SpiderListener() {
                        @Override
                        public void onSuccess(Request request) {
                            System.out.println("spider success");
                            myfilelog("success line" + line);
                            startAnotherSpider(scanner);

                        }

                        @Override
                        public void onError(Request request) {
                            System.out.println("spider error");
                            Dao.getInstance().insertUncrawledURL(request.getUrl());
                        }
                    };
                    final ArrayList<SpiderListener> listeners=new ArrayList<SpiderListener>();
                    listeners.add(spiderlistener);
                    Dao.getInstance().deleteProxy();
                    ProxySpider.startCrawlIP();
                    ips = new Proxy[result.size()];
                    for (int i = 0; i < result.size(); i++)
                        ips[i] = new Proxy((String) result.get(1), (Integer) result.get(2));
                    final HttpClientDownloaderAd httpClientDownloaderad = new HttpClientDownloaderAd(listener);
                    httpClientDownloaderad.setProxyProvider(SimpleProxyProvider.from(ips));
                    int j = 0;
                    while (j < 1) {
                        myfilelog("start"+j);
                        final ArrayList<String> urls = new ArrayList<String>();
                        for (int i = 0; i < 20; i++) {
                            if (!scanner.hasNext())
                                break;
                            urls.add(scanner.nextLine());
                            line++;
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Spider.create(new AmazonMovieSpider()).setSpiderListeners(listeners).setDownloader(httpClientDownloaderad).startUrls(urls).addPipeline(new MysqlPipline()).thread(1).run();
                            }
                        }).start();
                        j++;
                    }

                    line++;
                    System.out.println("第" + line + "条");
                } else {
                    myfilelog("stoped at line" + line);
                    //ProxySpider.startCrawlIP();
                    try {
                        Thread.sleep(50000);
                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    result=Dao.getInstance().selectProxy();
                }
            }



        }
    }
    public  static void myfilelog(String data)
    {
        pw.append(data+"\n");
    }
}
