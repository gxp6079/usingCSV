package Routes;

import Application.ClientHandler;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;

public class getMultipleInstancesRoute implements Route {
    private static final Logger LOG = Logger.getLogger(getMultipleInstancesRoute.class.getName());

    private ClientHandler clientHandler;

    public getMultipleInstancesRoute(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;

        LOG.finer("getMultipleInstancesRoute initialized");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}
