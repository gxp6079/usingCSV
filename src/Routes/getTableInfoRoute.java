package Routes;

import Application.ClientHandler;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;


/**
 * redirected to from postTemplateRoute when template name given is not found in database
 * prompts the user to give start and end keys for the tables
 *
 * @pre template not in database
 */
public class getTableInfoRoute implements Route {
    private static final Logger LOG = Logger.getLogger(getTableInfoRoute.class.getName());

    private ClientHandler clientHandler;

    public getTableInfoRoute(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        LOG.finer("getTableInfoRoute initialized");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}
