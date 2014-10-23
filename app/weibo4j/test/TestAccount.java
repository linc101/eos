package weibo4j.test;

import weibo4j.Account;
import weibo4j.model.Privacy;
import weibo4j.model.WeiboException;
import weibo4j.model.*;
import weibo4j.org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by yehuizhang on 14/10/22.
 */
public class TestAccount {
    public static void main(String[] args) throws WeiboException {
        Account account = new Account("2.00zP9ywBNwkgZC88ed636688LYjnsD");
        Privacy privacy = account.getAccountPrivacy();
        System.out.println("test for privacy:"+ privacy.toString());

        List<School> schoolList = account.getAccountProfileSchoolList("清华大学");
        System.out.println("the length of schoolList is:" + schoolList.size());

    }
}
