package proxy;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

public class HttpClientDownloaderAd extends HttpClientDownloader {
    private SpiderListener spiderListener;
    public HttpClientDownloaderAd(SpiderListener listener)
    {
        super();
        this.spiderListener=listener;
    }
    @Override
    public void onSuccess(Request request) {
        spiderListener.onSuccess(request);
    }

    protected void onError(Request request) {
        spiderListener.onError(request);
    }
}
