package com.company;

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

import java.io.File;
import java.util.Scanner;

public class PDFToCSV {

    private static final String API_KEY = "el9x8gb285ar";
    private static final String FORMAT = "csv";

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        TemplateReader reader = new TemplateReader();
        System.out.println("Enter pdf file name:");
        String filename = scan.nextLine().trim();
        System.out.println("Enter the template type:");
        String templateName = scan.nextLine().trim();

        while (!convertToCSV(filename)) {
            System.out.println("Convert failed, enter new filename: ");
            filename = scan.nextLine().trim();
        }

        reader.readTemplate(getOutputFilename(filename, "csv"), templateName);
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
