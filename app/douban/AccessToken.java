package douban;

import java.io.Serializable;
/**
 * Created by yehuizhang on 14/10/25.
 */

public class AccessToken implements Serializable{
    private String accessToken = null;
    private String expiresIn = null;
    private String refreshToken = null;
    private String doubanUserId = null;
    private String doubanUsername = null;


    public String getDoubanUsername(){
        return doubanUsername;
    }

    public void setDoubanUsername(String doubanUsername){
        this.doubanUsername = doubanUsername;
    }

    /**
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return the expiresIn
     */
    public String getExpiresIn() {
        return expiresIn;
    }

    /**
     * @param expiresIn the expiresIn to set
     */
    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    /**
     * @return the refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * @param refreshToken the refreshToken to set
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * @return the doubanUserId
     */
    public String getDoubanUserId() {
        return doubanUserId;
    }

    /**
     * @param doubanUserId the doubanUserId to set
     */
    public void setDoubanUserId(String doubanUserId) {
        this.doubanUserId = doubanUserId;
    }

    public AccessToken() {

    }

    public AccessToken (String accessToken) {
        this.accessToken = accessToken;
    }

    public AccessToken (String accessToken, String expiresIn, String refreshToken, String doubanUserId, String doubanUsername) {
        this.accessToken = accessToken;
        this.doubanUserId = doubanUserId;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.doubanUserId = doubanUsername;
    }

    @Override
    public String toString(){
        return "AccessToken[accessToken:"+this.accessToken +
                " expiresIn: " +this.expiresIn + " refreshToken: " + this.refreshToken + " doubanUserId:" +
                this.doubanUserId +"]";
    }

}
