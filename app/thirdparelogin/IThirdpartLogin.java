package thirdparelogin;

/**
 * Created by yehuizhang on 14/10/24.
 */
public interface IThirdpartLogin {
    public void auth(String url);

    public void setThirdpartInfo(String authCode);
}
