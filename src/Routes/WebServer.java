package Routes;


import java.util.logging.Logger;

import static spark.Spark.get;
import static spark.Spark.post;

public class WebServer {

    private static final Logger LOG = Logger.getLogger(WebServer.class.getName());


    public static final String TEMPLATE_URL = "/PDFreader";

    public static final String TABLE_INFO_URL = "/tableInfo";

    public static final String START_END_URL = "/startEnd";

    public static final String MULTIPLE_INSTANCE_URL = "/multi";

    public static final String FINAL_INFO = "/finalInfo";

    public static final String SIGN_IN = "/signIn";

    public WebServer() {
    }

    public void initialize() {
        get(TEMPLATE_URL, new getTemplateRoute());

        get(FINAL_INFO, new getFinalInfoRoute());

        get(MULTIPLE_INSTANCE_URL, new getMultipleInstancesRoute());

        get(START_END_URL, new getStartEndRoute());

        get(TABLE_INFO_URL, new getTableInfoRoute());

        //get("/template", new getTemplateRoute());

        post(MULTIPLE_INSTANCE_URL, new postMultipleInstancesRoute());

        post(START_END_URL, new postStartEndRoute());

        post(TABLE_INFO_URL, new postTableInfoRoute());

        post(TEMPLATE_URL, new postTemplateRoute());

        post(SIGN_IN, new postSignInRoute());

        LOG.finer("WebServer Initialized");

    }
}
