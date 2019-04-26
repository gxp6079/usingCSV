package Routes;

import Application.ClientHandler;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;

public class postMultipleInstancesRoute implements Route {
    private static final Logger LOG = Logger.getLogger(postMultipleInstancesRoute.class.getName());

    private ClientHandler clientHandler;

    public postMultipleInstancesRoute(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;

        LOG.finer("postMultipleInstancesRoute initialized");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}
