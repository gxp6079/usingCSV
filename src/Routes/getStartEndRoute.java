package Routes;

import Application.ClientHandler;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;

/**
 * prompts the user to give start and end keys for each of the tables
 */
public class getStartEndRoute implements Route {
    private static final Logger LOG = Logger.getLogger(getStartEndRoute.class.getName());

    private ClientHandler clientHandler;

    public getStartEndRoute(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;

        LOG.finer("getStartEndRoute initialized");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}
