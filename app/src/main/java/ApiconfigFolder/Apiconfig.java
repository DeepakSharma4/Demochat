package ApiconfigFolder;

/**
 * Created by hp on 4/19/2017.
 */

public class Apiconfig {
    private String api = "http://192.168.57.210:8084/API/";      //http://192.168.57.210:8084/API/
    private String imageURL = "http://192.168.57.210/UserImages/";

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
