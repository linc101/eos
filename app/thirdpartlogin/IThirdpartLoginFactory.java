package thirdpartlogin;

import thirdpartlogin.IThirdpartLogin;

/**
 * Created by yehuizhang on 14/10/24.
 */
public interface IThirdpartLoginFactory {
    public <T extends IThirdpartLogin> T createThirdpartLogin(Class<T> c);
}
