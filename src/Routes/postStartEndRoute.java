package Routes;

import Application.ClientHandler;
import Model.TableAttributes;
import Model.Template;
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

        TableAttributes tableAttributes = new TableAttributes(start, end);

        //TODO look for start and end in csv file
        // if two instances found
        //      redirect to getMultipleInstancesRoute
        // else
        //      put Table Attributes in template

        Template current = request.session().attribute("template");
        current.addTable(tableAttributes);


        return null;
    }
}
