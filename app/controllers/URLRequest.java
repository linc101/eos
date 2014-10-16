package controllers;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Created by hgqput on 14-9-19.
 */
public class URLRequest extends CommonRender{
    private static final Logger log = LoggerFactory.getLogger(URLRequest.class);


    public static void authDouban(){
        URLConnection connection = null;
        try{
            connection = new URL("https://www.douban.com/service/auth2/token?client_id=009d7e877f154b8501d87b967d3fe28f&client_secret=3a0fe37b10ff10d3&redirect_uri=http://121.40.184.111/Application/index&grant_type=authorization_code&code=357d7621a0e8de82").openConnection();
            connection.connect();

            InputStream fin = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));

            StringBuffer buffer = new StringBuffer();
            String temp = null;
            while((temp = br.readLine()) != null){
                buffer.append(temp);
            }

            System.out.println("-------------------test : " + buffer.toString());
        } catch (IOException e){
            log.info(e.getMessage(), e);
        }
    }

    public static void main(String[] args) throws IOException{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        final String url = "https://www.douban.com/service/auth2/token?client_id=009d7e877f154b8501d87b967d3fe28f&client_secret=3a0fe37b10ff10d3&redirect_uri=http://121.40.184.111/Application/index&grant_type=authorization_code&code=435d84c78bc1e082";
        HttpPost post = new HttpPost(url);
        CloseableHttpResponse response = null;
        Scanner in = null;
        try{
            response  = httpClient.execute(post);
            HttpEntity entity = response.getEntity();
            in = new Scanner(entity.getContent());

            while (in.hasNext()) {
                System.out.println(in.next());

            }
        }finally{

        }
    }
}
