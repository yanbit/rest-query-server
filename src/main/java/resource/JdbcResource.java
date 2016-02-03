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

    // select DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') as sd,count(1) as c from zz_member m join zz_yundb y on(m.mid = y.mid) where DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d')>'2015-11-01' and DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d')<'2015-11-15'  GROUP BY sd ORDER BY c desc;


}
