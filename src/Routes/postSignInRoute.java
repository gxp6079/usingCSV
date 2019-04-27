package Routes;

import Application.ClientHandler;
import Model.Template;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;


/**
 * can be used to validate a user's permission to use the API
 */
public class postSignInRoute implements Route {
    private static final Logger LOG = Logger.getLogger(postSignInRoute.class.getName());

    private ClientHandler clientHandler;

    public postSignInRoute(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;

        LOG.finer("postSignInRoute initialized");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String apiKey = request.queryParams("key");

        // TODO check if key exists in something (file, map, etc.)
        // put user into active user list with an id to keep track of their template creation

        request.session().attribute("template", new Template());

        return null;
    }
}
