package constants;

import utils.TestBase;

public class Constants extends TestBase {

    public String baseurl = LoadProperties().getProperty("baseURI");
    public String contentType = LoadProperties().getProperty("contentType");
    public String apiKey = LoadProperties().getProperty("apiKey");

}
