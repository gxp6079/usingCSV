package Routes;

import Application.ClientHandler;
import Model.Template;
import Model.TemplateReader;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.logging.Logger;


/**
 * retrieves completed info, very last route called
 */
public class getFinalInfoRoute implements Route {
    private static final Logger LOG = Logger.getLogger(getFinalInfoRoute.class.getName());

    private ClientHandler clientHandler;

    public getFinalInfoRoute(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;

        LOG.finer("getFinalInfoRoute initialized");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String fileName = request.queryParams("fileName");

        Template currentTemplate = request.session().attribute("template");

        TemplateReader.addToDB(currentTemplate);
        TemplateReader.readExistingTemplate(fileName, currentTemplate.getType(), response.raw().getOutputStream());

        return 1;
    }
}
