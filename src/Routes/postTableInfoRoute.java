package Routes;

import Application.ClientHandler;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;


/**
 * called to add a start and end key to a template that is being created
 * @redirect getMultipleInstancesRoute if multple instances of start/end were found
 *
 */
public class postTableInfoRoute implements Route {
    private static final Logger LOG = Logger.getLogger(WebServer.class.getName());

    private ClientHandler clientHandler;

    public postTableInfoRoute(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;

        LOG.finer("postTableInfoRoute initialized");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}
