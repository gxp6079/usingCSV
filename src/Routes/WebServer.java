package Routes;


import static spark.Spark.get;

public class WebServer {

    public WebServer() {}

    public void initialize() {
        get("/PDFreader", new getTemplateRoute());
    }
}
