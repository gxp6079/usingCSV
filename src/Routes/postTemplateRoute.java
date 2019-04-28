package Routes;

import Application.ClientHandler;
import Model.TableFactory;
import Model.Template;
import Model.TemplateReader;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.servlet.ServletOutputStream;
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

        ServletOutputStream out = response.raw().getOutputStream();

        // Template fromDB = TemplateReader.readFromDB(templateType);

        if (TemplateReader.checkIfExists(templateType)) {
            TemplateReader.readExistingTemplate(filename, templateType, out);
            return 1;
        }

        Template currentTemplate = request.session().attribute("template");
        currentTemplate.setType(templateType);

        List<String[]> lines = TemplateReader.readAllLines(filename);

        request.session().attribute("factory", new TableFactory(lines));

        return 0;
    }
}
