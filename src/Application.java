import Routes.WebServer;

public class Application {
    public static void main(String[] args) {
        WebServer webServer = new WebServer();
        webServer.initialize();
    }
}
