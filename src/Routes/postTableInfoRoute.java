package Routes;

import Model.Field;
import Model.TableFactory;
import Model.Template;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;


/**
 * called to add a field data to a template that is being created
 * @redirect getMultipleInstancesRoute if multple instances of start/end were found
 *
 */
public class postTableInfoRoute implements Route {
    private static final Logger LOG = Logger.getLogger(WebServer.class.getName());


    public postTableInfoRoute() {

        LOG.finer("postTableInfoRoute initialized");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Template currentTemplate = request.session().attribute("template");

        TableFactory factory = request.session().attribute("factory");

        String fieldName = request.queryParams("field");
        String value = request.queryParams("value");
        int id = Integer.parseInt(request.queryParams("id"));

        currentTemplate.addField(new Field(fieldName, id, value));

        return 1;
    }
}
