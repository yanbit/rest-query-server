package resource;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.QueryFunction;

/**
 * User: yanbit
 * Date: 2016/2/3
 * Time: 10:36
 */
public class JdbcResource extends ServerResource {
    @Get
    public String handler() {
        QueryFunction queryFunction = new QueryFunction();
        return queryFunction.handlerParam(getRequest());
    }



}
