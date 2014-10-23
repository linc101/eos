package weibo4j.examples.comment;

import weibo4j.Comments;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.CommentWapper;
import weibo4j.model.WeiboException;

import java.io.FileWriter;
import java.io.IOException;

public class GetCommentByMe {

	public static void main(String[] args) throws IOException {
		String access_token = "2.00zP9ywBNwkgZC88ed636688LYjnsD";
		Comments cm = new Comments(access_token);
		try {
			CommentWapper comment = cm.getCommentByMe();
            FileWriter writer = new FileWriter("/Users/yehuizhang/comment.txt");
            writer.write(comment.toString());
//			Log.logInfo(comment.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
