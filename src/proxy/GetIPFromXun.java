package proxy;

import dao.Dao;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import us.codecraft.webmagic.proxy.Proxy;

import java.io.IOException;
import java.util.Vector;

public class GetIPFromXun {
    private static final OkHttpClient client = new OkHttpClient();
    public static void main(String[] args) {
        try {
            Request request = new Request.Builder()
                    .url("http://api.xdaili.cn/xdaili-api/greatRecharge/getGreatIp?spiderId=2442699d86ac4d13bd2bdb6bb3ed8f1e&orderno=YZ20171283571icdLW8&returnType=2&count=20")
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responsebody=response.body().string();
            JSONObject jsonObject=new JSONObject(responsebody);
            JSONArray jsonArray=jsonObject.getJSONArray("RESULT");
            for(Object object:jsonArray)
            {
                JSONObject temp=(JSONObject)object;
                String ip=temp.getString("ip");
                int port=Integer.parseInt(temp.getString("port"));
                Vector result=Dao.getInstance().getProxyWithIPandPort(ip,port);
                if(result==null || result.size()==0)
                {
                    Dao.getInstance().insertProxy(ip,port);
                }

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
