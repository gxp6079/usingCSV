package Routes;

import Model.TableFactory;
import Model.Template;
import Model.TemplateReader;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import spark.Request;
import spark.Response;
import spark.Route;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Part;
import java.io.File;
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
    private static final String API_KEY = "el9x8gb285ar";
    private static final String FORMAT = "csv";

    public postTemplateRoute() {

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
                Path p = Paths.get(Paths.get("").toAbsolutePath().toString() + "/temp/" + "NewPDF.csv").toAbsolutePath();
                return p;
            }

            if (!Files.exists(Paths.get(Paths.get("").toAbsolutePath() + "/temp/"))) {
                Files.createDirectory(Paths.get(Paths.get("").toAbsolutePath() + "/temp/"));
            }

            Path p = Paths.get(Paths.get("").toAbsolutePath().toString() + "/temp/" + filename).toAbsolutePath();

            Part uploadedFile = request.raw().getPart("file");

            try (final InputStream in = uploadedFile.getInputStream()) {
                Files.copy(in, p);
            } catch (Exception e) {
                p = Paths.get(Paths.get("").toAbsolutePath().toString() + "/temp/" + "NewPDF.csv").toAbsolutePath();
                return p;
            }

            uploadedFile.delete();
            return p;

        } catch (Exception e) {
            Path p = Paths.get(Paths.get("").toAbsolutePath().toString() + "/temp/" + "NewPDF.csv").toAbsolutePath();
            return p;
        }
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

        convertToCSV(path.toAbsolutePath().toString());

        request.session().attribute("path", path);

        ServletOutputStream out = response.raw().getOutputStream();

        // Template fromDB = TemplateReader.readFromDB(templateType);

        String csvFilePath = getOutputFilename(path.toAbsolutePath().toString(), "csv");

        if (TemplateReader.checkIfExists(templateType)) {
            TemplateReader.readExistingTemplate(csvFilePath, templateType, out);
            return 0;
        }

        Template currentTemplate = request.session().attribute("template");
        currentTemplate.setType(templateType);

        List<String[]> lines = TemplateReader.readAllLines(csvFilePath);

        request.session().attribute("factory", new TableFactory(lines));

        return 1;
    }


    public static boolean convertToCSV(String filename) {


        final String apiKey = API_KEY;
        final String format = FORMAT;
        final String pdfFilename = filename;


        // Avoid cookie warning with default cookie configuration
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();

        File inputFile = new File(pdfFilename);

        if (!inputFile.canRead()) {
            System.out.println("Can't read input PDF file: \"" + pdfFilename + "\"");
            System.exit(1);
        }

        try (CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(globalConfig).build()) {
            HttpPost httppost = new HttpPost("https://pdftables.com/api?format=" + format + "&key=" + apiKey);
            FileBody fileBody = new FileBody(inputFile);

            HttpEntity requestBody = MultipartEntityBuilder.create().addPart("f", fileBody).build();
            httppost.setEntity(requestBody);

            System.out.println("Sending request");

            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                if (response.getStatusLine().getStatusCode() != 200) {
                    System.out.println(response.getStatusLine());
                    System.exit(1);
                }
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    final String outputFilename = getOutputFilename(pdfFilename, format.replaceFirst("-.*$", ""));
                    System.out.println("Writing output to " + outputFilename);

                    final File outputFile = new File(outputFilename);
                    FileUtils.copyToFile(resEntity.getContent(), outputFile);
                    return true;
                } else {
                    System.out.println("Error: file missing from response");
                    return false;
                    // System.exit(1);
                }
            }
        } catch (Exception e) {
            return false;
        }
    }

    private static String getOutputFilename(String pdfFilename, String suffix) {
        if (pdfFilename.length() >= 5 && pdfFilename.toLowerCase().endsWith(".pdf")) {
            return pdfFilename.substring(0, pdfFilename.length() - 4) + "." + suffix;
        } else {
            return pdfFilename + "." + suffix;
        }
    }

}
