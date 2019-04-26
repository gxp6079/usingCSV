package Routes;

import Application.ClientHandler;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;

public class getFinalInfoRoute implements Route {
    private static final Logger LOG = Logger.getLogger(getFinalInfoRoute.class.getName());

    private ClientHandler clientHandler;

    public getFinalInfoRoute(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;

        LOG.finer("getFinalInfoRoute initialized");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}
