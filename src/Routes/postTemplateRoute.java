package Routes;

import Application.ClientHandler;
import Model.TableFactory;
import Model.Template;
import Model.TemplateReader;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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


    private Path downloadFile(Request request) {
        try {
            request.raw().setAttribute("org.eclipse.jetty.multipartConfig",
                    new MultipartConfigElement("/", 1000000000, 10000000, 1024));

            String filename;

            try {
                filename = request.raw().getPart("file").getSubmittedFileName();
            } catch (Exception e) {
                return null;
            }

            if (!Files.exists(Paths.get(Paths.get("").toAbsolutePath() + "/temp/"))) {
                Files.createDirectory(Paths.get(Paths.get("").toAbsolutePath() + "/temp/"));
            }

            Path p = Paths.get(Paths.get("").toAbsolutePath().toString() + "/temp/" + filename).toAbsolutePath();

            Part uploadedFile = request.raw().getPart("file");

            try (final InputStream in = uploadedFile.getInputStream()) {
                Files.copy(in, p);
            }

            uploadedFile.delete();
            return p;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Object handle(Request request, Response response) throws Exception {
        //String filename = request.queryParams("fileName");
        String templateType = request.queryParams("type");

        Path path = downloadFile(request);
        if (path == null) {
            response.status(400);
            return "Error loading file from request body";
        }

        String filename = path.getFileName().toString();

        ServletOutputStream out = response.raw().getOutputStream();

        // Template fromDB = TemplateReader.readFromDB(templateType);

        if (TemplateReader.checkIfExists(templateType)) {
            TemplateReader.readExistingTemplate(filename, templateType, out);
            return 0;
        }

        Template currentTemplate = request.session().attribute("template");
        currentTemplate.setType(templateType);

        List<String[]> lines = TemplateReader.readAllLines(filename);

        request.session().attribute("factory", new TableFactory(lines));

        return 1;
    }
}
