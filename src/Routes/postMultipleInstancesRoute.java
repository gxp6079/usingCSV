package Routes;

import Application.ClientHandler;
import Model.TableAttributes;
import Model.Template;
import Model.TemplateReader;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;


/**
 * called to specify which instance of start/end the user wants to use
 */
public class postMultipleInstancesRoute implements Route {
    private static final Logger LOG = Logger.getLogger(postMultipleInstancesRoute.class.getName());

    private ClientHandler clientHandler;

    public postMultipleInstancesRoute(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;

        LOG.finer("postMultipleInstancesRoute initialized");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        int instance = Integer.parseInt(request.queryParams("num"));

        TableAttributes tableAttributes = request.session().attribute("currentAttributes");

        Template currentTemplate = request.session().attribute("template");

        TemplateReader.createTable(currentTemplate, tableAttributes.START, tableAttributes.END, instance);

        request.session().removeAttribute("currentAttributes");
        return null;
    }
}
