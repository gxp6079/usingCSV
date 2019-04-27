package Application;

import Model.Template;

import java.util.HashMap;
import java.util.Map;

public class ClientHandler {

    /**
     * integer ID for any user that has access to
     * template that they will create during their session
     */
    private Map<Integer, Template> activeUsers;

    public ClientHandler() {
        this.activeUsers = new HashMap<>();
    }


    public Template getTemplate(int id) {
        return activeUsers.get(id);
    }


    public boolean signIn(String key) {
        // TODO if key exists sign them in & create new empty template

        return true;
    }

}
