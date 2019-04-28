package Routes;


import Application.ClientHandler;

import java.util.logging.Logger;

import static spark.Spark.get;
import static spark.Spark.post;

public class WebServer {

    private final ClientHandler clientHandler = new ClientHandler();

    private static final Logger LOG = Logger.getLogger(WebServer.class.getName());


    public static final String TEMPLATE_URL = "/PDFreader";

    public static final String TABLE_INFO_URL = "/tableInfo";

    public static final String START_END_URL = "/startEnd";

    public static final String MULTIPLE_INSTANCE_URL = "/multi";

    public static final String FINAL_INFO = "/finalInfo";

    public WebServer() {
    }

    public void initialize() {
        get(TEMPLATE_URL, new getTemplateRoute(clientHandler));

        get(FINAL_INFO, new getFinalInfoRoute(clientHandler));

        get(MULTIPLE_INSTANCE_URL, new getMultipleInstancesRoute(clientHandler));

        get(START_END_URL, new getStartEndRoute(clientHandler));

        get(TABLE_INFO_URL, new getTableInfoRoute(clientHandler));

        //get("/template", new getTemplateRoute());

        post(MULTIPLE_INSTANCE_URL, new postMultipleInstancesRoute(clientHandler));

        post(START_END_URL, new postStartEndRoute(clientHandler));

        post(TABLE_INFO_URL, new postTableInfoRoute(clientHandler));

        post(TEMPLATE_URL, new postTemplateRoute(clientHandler));

        LOG.finer("WebServer Initialized");

    }
}
