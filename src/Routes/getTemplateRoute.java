package Routes;

import Application.ClientHandler;
import Model.Main;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;

/**
 * gets a template and extracts data from pdf when a template has been
 * found in the database
 *
 * @pre template is in database
 */
public class getTemplateRoute implements Route {
    private static final Logger LOG = Logger.getLogger(getTemplateRoute.class.getName());

    private ClientHandler clientHandler;

    public getTemplateRoute(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;

        LOG.finer("getTemplateRoute initialized");
    }


    @Override
    public Object handle(Request request, Response response) throws Exception {
        Main.run_with_csv(response.raw().getOutputStream(), request.raw().getInputStream());
        return null;
    }
}
