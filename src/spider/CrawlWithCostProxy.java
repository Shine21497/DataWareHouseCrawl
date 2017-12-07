package spider;

import com.alibaba.fastjson.JSON;
import dao.Dao;
import helper.PathHelper;
import org.json.JSONArray;
import org.json.JSONObject;
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

public class CrawlWithCostProxy {
    public static int line=0;
    private static File file;
    private static PrintWriter pw;
    static {
        file=new File(PathHelper.getlogPath());
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
    public static void main(String[] args) {
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

            }

            @Override
            public void onError(Request request) {
                System.out.println("spider error" + "now line" + line);

            }
        };
        /*String jsonresult="";
        try {
            FileInputStream f = new FileInputStream("F:\\ips.json");
            final Scanner scanner = new Scanner(f);
            while(scanner.hasNext())
            {
                jsonresult=jsonresult+scanner.nextLine();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }*/
        final ArrayList<SpiderListener> listeners = new ArrayList<SpiderListener>();
        listeners.add(spiderlistener);
        /*JSONObject jsonObject=new JSONObject(jsonresult);
        JSONArray jsonArray=jsonObject.getJSONArray("RESULT");
        Proxy[] ips = new Proxy[jsonArray.length() + 1];
        int i=0;
        for(Object object:jsonArray)
        {
            JSONObject temp=(JSONObject)object;
            ips[i]=new Proxy(temp.getString("ip"),Integer.parseInt(temp.getString("port")));

        }
        ips[jsonArray.length()] = new Proxy("115.159.34.252", 8080);*/
        Vector result=Dao.getInstance().selectProxy();
        Proxy[] ips=null;
        ips = new Proxy[result.size()];
        for (int i = 0; i < result.size(); i++) {
            Vector lineof = (Vector) result.get(i);
            ips[i] = new Proxy((String) lineof.get(1), (Integer) lineof.get(2));
        }
        final HttpClientDownloaderAd httpClientDownloaderad = new HttpClientDownloaderAd(listener);
        httpClientDownloaderad.setProxyProvider(SimpleProxyProvider.from(ips));
        try{
            FileInputStream f = new FileInputStream(PathHelper.geturlPath());
            Scanner scanner=new Scanner(f);
            while(scanner.hasNext()) {
                final ArrayList<String> urls = new ArrayList<String>();
                for (int k = 0; k < 1000; k++) {
                    if (!scanner.hasNext())
                         break;
                    urls.add(scanner.nextLine());
                    line++;
                 }
                Spider.create(new AmazonMovieSpider()).setSpiderListeners(listeners).setDownloader(httpClientDownloaderad).startUrls(urls).addPipeline(new MysqlPipline()).thread(10).run();
                System.out.println(line);
                myfilelog(""+line);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public  static void myfilelog(String data)
    {
        pw.append(data+"\n");
    }
}
