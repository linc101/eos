package thirdpartlogin;

import weibo4j.model.WeiboException;

/**
 * Created by yehuizhang on 14/10/24.
 */
public interface IThirdpartLogin {
    public void auth(String url);

    public void setThirdpartInfo(String authCode) throws WeiboException;
}
