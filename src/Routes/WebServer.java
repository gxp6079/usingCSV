package Routes;


import Application.ClientHandler;

import java.util.logging.Logger;

import static spark.Spark.get;
import static spark.Spark.post;

public class WebServer {

    private final ClientHandler clientHandler = new ClientHandler();

    private static final Logger LOG = Logger.getLogger(WebServer.class.getName());

    public WebServer() {
    }

    public void initialize() {
        get("/PDFreader", new getTemplateRoute(clientHandler));

        get("/finalInfo", new getFinalInfoRoute(clientHandler));

        get("/multi", new getMultipleInstancesRoute(clientHandler));

        get("/startEnd", new getStartEndRoute(clientHandler));

        get("/tableInfo", new getTableInfoRoute(clientHandler));

        //get("/template", new getTemplateRoute());

        post("/multi", new postMultipleInstancesRoute(clientHandler));

        post("/startEnd", new postStartEndRoute(clientHandler));

        post("/tableInfo", new postTableInfoRoute(clientHandler));

        post("/template", new postTemplateRoute(clientHandler));

        LOG.finer("WebServer Initialized");

    }
}
