package Routes;

import Application.ClientHandler;
import Model.TableFactory;
import Model.Template;
import Model.TemplateReader;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.logging.Logger;


/**
 * first route called
 *
 * provide pdf file and name of template
 * @redirect getTemplateRoute if the template name is found in the database
 * @redirect getTableInfoRoute if the template name is not found
 */
public class postTemplateRoute implements Route {
    private static final Logger LOG = Logger.getLogger(postTemplateRoute.class.getName());

    private ClientHandler clientHandler;

    public postTemplateRoute(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;

        LOG.finer("postTemplateRoute initialized");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String filename = request.queryParams("fileName");
        String templateType = request.queryParams("type");

        Template fromDatabase = TemplateReader.readFromDB(templateType);

        if (fromDatabase != null) {
            request.session().attribute("template", fromDatabase);
            response.redirect(WebServer.TEMPLATE_URL);
            return null;
        }

        List<String[]> lines = TemplateReader.readAllLines(filename);

        request.session().attribute("factory", new TableFactory(lines));

        return null;
    }
}
