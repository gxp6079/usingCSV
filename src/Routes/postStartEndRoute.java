package Routes;

import Application.ClientHandler;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;


/**
 * used to give start/end keys for a table
 *
 * each call contains one start and one end key
 */
public class postStartEndRoute implements Route {
    private static final Logger LOG = Logger.getLogger(postStartEndRoute.class.getName());

    private ClientHandler clientHandler;

    public postStartEndRoute(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;

        LOG.finer("postStartEndRoute initialized");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}
