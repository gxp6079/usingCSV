package Routes;

import com.company.Main;
import spark.Request;
import spark.Response;
import spark.Route;

public class getTemplateRoute implements Route {


    public getTemplateRoute() {
    }


    @Override
    public Object handle(Request request, Response response) throws Exception {
        Main.run_with_csv(response.raw().getOutputStream(), request.raw().getInputStream());
        return null;
    }
}
