package douban;

import java.util.*;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.LoadConfig;

/**
 * Created by yehuizhang on 14/10/25.
 */
public class HttpClientPost {
    private static final Logger log = LoggerFactory.getLogger(HttpClientPost.class);

    private HttpPost httpPost;

    private List<NameValuePair> params = new ArrayList<NameValuePair>();

    private static HttpClient httpClient = new DefaultHttpClient();

    public HttpClientPost(String url, Map<String, String> params){
        this.httpPost = new HttpPost(url);
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();
            this.params.add(new BasicNameValuePair(key, value));
        }
    }

    public String sendPost() throws Exception{
        this.httpPost.setEntity(new UrlEncodedFormEntity(this.params, HTTP.UTF_8));
        HttpResponse response = httpClient.execute(this.httpPost);
        if(response.getStatusLine().getStatusCode() == 200){
            String result = EntityUtils.toString(response.getEntity());
            return result;
        }
        return null;
    }

    public static void main(String[] args) throws Exception{
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(LoadConfig.getValue("douban_token"));
        List<NameValuePair> param = new ArrayList<NameValuePair>();
        param.add(new BasicNameValuePair("client_id", LoadConfig.getValue("douban_client_id")));
        param.add(new BasicNameValuePair("client_secret", LoadConfig.getValue("douban_secret")));
        param.add(new BasicNameValuePair("redirect_uri", LoadConfig.getValue("douban_redirect_url")));
        param.add(new BasicNameValuePair("grant_type", "authorization_code"));
        param.add(new BasicNameValuePair("code", "85889f5c0d4f8760"));
        httpPost.setEntity(new UrlEncodedFormEntity(param,HTTP.UTF_8));
        HttpResponse response = new DefaultHttpClient().execute(httpPost);
        System.out.println("results:" + EntityUtils.toString(response.getEntity()));

    }
}
