package application;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import resource.JdbcResource;

/**
 * User: yanbit
 * Date: 2016/2/3
 * Time: 10:35
 */
public class JdbcApplication extends Application{
    public Restlet createRoot() {
        Router router = new Router(getContext());
        router.attachDefault(JdbcResource.class);
        return router;
    }
}
