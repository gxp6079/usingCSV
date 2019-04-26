package Routes;

import Application.ClientHandler;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;

public class postTemplateRoute implements Route {
    private static final Logger LOG = Logger.getLogger(postTemplateRoute.class.getName());

    private ClientHandler clientHandler;

    public postTemplateRoute(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;

        LOG.finer("postTemplateRoute initialized");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}
