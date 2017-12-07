package spider;

import java.io.*;
import java.util.*;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.jar.Attributes.Name;


import helper.PathHelper;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Selectable;

public class AmazonMovieSpider implements PageProcessor {
    public static int count=0;
    private Site site = Site.me().setRetryTimes(3).setSleepTime(753)
            .setUserAgent(UserAgents.getuseragent())
            ;
    public AmazonMovieSpider()
    {
        super();
    }
    @Override
    public void process(Page page) {
        try {
            Thread.sleep(new Random().nextInt(553)+2553);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        MovieEntity movie=new MovieEntity();

        String url=page.getUrl().toString();
        String movieID=url.substring(url.length()-10,url.length());
        movie.setMovieID(movieID);

        String producttitle="";
        String actors="";
        String directors="";
        String leadingactors="";
        String movieType="";
        String releaseTime="";
        String versionsurls="";
        movie.setLeadingActors(leadingactors);
        movie.setMovieType(movieType);
        movie.setReleaseTime(releaseTime);
        movie.setDirector(directors);
        movie.setVersions(versionsurls);
        movie.setActors(actors);
        if(page.getHtml().xpath("//span[@id='productTitle']/text()").toString()!=null)
        {
            producttitle=page.getHtml().xpath("//span[@id='productTitle']/text()").toString();
            movie.setMovieName(producttitle);
            List<String> messages=page.getHtml().xpath("//*[@id='detail-bullets']/table/tbody/tr/td/div/ul//li").all();
            for(int i=0;i<messages.size();i++)
            {
                String temp=messages.get(i);
                if(temp.contains("Actors"))
                {
                    actors=temp;
                    movie.setActors(actors);
                }
                else if(temp.contains("Directors"))
                {
                    directors=temp;
                    movie.setDirector(directors);
                }
                else if(temp.contains("Release Date"))
                {
                    releaseTime=temp;
                    movie.setReleaseTime(releaseTime);
                }

            }
            List<String> othertypeurls=page.getHtml().xpath("//div[@id='tmmSwatches']/ul//li//a/@href").all();
            for(int i=0;i<othertypeurls.size();i++)
            {
                versionsurls=versionsurls+othertypeurls.get(i)+"\n";
            }
            movie.setVersions(versionsurls);

        }
        else if(page.getHtml().xpath("//h1[@id='aiv-content-title']/text()").toString()!=null)
        {
            producttitle=page.getHtml().xpath("//h1[@id='aiv-content-title']/text()").toString();
            movie.setMovieName(producttitle);
            List<String> lefts=page.getHtml().xpath("//*[@id='dv-center-features']/div[1]/div/table/tbody//tr/th").all();
            List<String> rights=page.getHtml().xpath("//*[@id='dv-center-features']/div[1]/div/table/tbody//tr/td").all();
            //here
            for(int i=0;i<lefts.size();i++)
            {
                String temp=lefts.get(i);
                if(temp.contains("Supporting actors"))
                {
                    actors=rights.get(i);
                    movie.setActors(actors);
                }
                else if(temp.contains("Director"))
                {
                    directors=rights.get(i);
                    movie.setDirector(directors);
                }
                else if(temp.contains("Release Date"))
                {
                    releaseTime=rights.get(i);
                    movie.setReleaseTime(releaseTime);
                }
                else if(temp.contains("Genres"))
                {
                    movieType=rights.get(i);
                    movie.setMovieType(movieType);
                }
                else if(temp.contains("Starring"))
                {
                    leadingactors=rights.get(i);
                    movie.setLeadingActors(leadingactors);
                }

            }

        }
        else
        {
            movie.setMovieName("");
            page.putField("uncrawledurl",page.getUrl().toString());
        }
        page.putField("movie",movie);
        try{
            File htmlfile=null;
            if(producttitle.equals(""))
                htmlfile=new File(PathHelper.getCrawlagainPath()+"\\"+movieID);
            else
                htmlfile=new File(PathHelper.getCrawledPath()+"\\"+movieID);
            if(!htmlfile.exists())
                htmlfile.createNewFile();
            PrintWriter pw=new PrintWriter(new FileOutputStream(htmlfile));
            pw.write(page.getHtml().toString());
            pw.flush();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        count++;
        if(count==10)
        {
            try {
                Thread.sleep(new Random().nextInt(1000)+4000);
                count=0;
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        getSite().setUserAgent(UserAgents.getuseragent());






    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            ArrayList<String> urls=new ArrayList<String>();
            FileInputStream f = new FileInputStream("F:\\datawarehouseurls\\url0_0");
            Scanner scanner=new Scanner(f);
            while(scanner.hasNext())
            {
                urls.add(scanner.nextLine());
            }

            Spider.create(new AmazonMovieSpider()).startUrls(urls).addPipeline(new MysqlPipline()).thread(1).run();
        }catch(Exception e)
        {

            e.printStackTrace();

        }
    }

}
