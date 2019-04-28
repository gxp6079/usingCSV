package Routes;

import Application.ClientHandler;
import Model.TableAttributes;
import Model.TableFactory;
import Model.Template;
import Model.TemplateReader;
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
        String start = request.queryParams("start");
        String end = request.queryParams("end");

        // TODO call tableReader.makeTable(table factory, template, start, end, instance)

        Template currentTemplate = request.session().attribute("template");

        TableFactory factory = request.session().attribute("factory");
        factory.initialize(start, end);

        if (factory.getNumLocations() > 1) {
            TableAttributes tableAttributes = new TableAttributes(start, end);
            request.session().attribute("currentAttributes", tableAttributes);

            response.redirect(WebServer.MULTIPLE_INSTANCE_URL + "?num=" + factory.getNumLocations());
            return 0;
        }

        TemplateReader.createTable(currentTemplate, start, end, 1);

        return 1;
    }
}
