package thirdparelogin;

/**
 * Created by yehuizhang on 14/10/24.
 */
public class ThirdpartLoginFactory implements IThirdpartLoginFactory{
    @Override
    public <T extends IThirdpartLogin> T createThirdpartLogin(Class<T> c){
        IThirdpartLogin thirdpartLogin = null;
        try {
            thirdpartLogin = (IThirdpartLogin) Class.forName(c.getName()).newInstance();
        } catch(Exception e){
            e.getMessage();
        }
        return (T)thirdpartLogin;
    }

}
