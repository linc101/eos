package weibo4j.test;

import weibo4j.Comments;
import weibo4j.model.Comment;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by yehuizhang on 14/10/23.
 */
public class TestComments {
    public static void main(String[] args) throws WeiboException, JSONException, IOException{
        String mid = "Bs6cDufsK";
        String accessToken = "2.00zP9ywBNwkgZC88ed636688LYjnsD";
        Comments comments = new Comments(accessToken);
        Comment comment = comments.getWeiboID(mid);
        System.out.println(comment.toString());
        FileWriter writer = new FileWriter("/Users/yehuizhang/comment.txt");
        writer.write(comment.toString());
        System.out.println(comment.toString());
    }
}
