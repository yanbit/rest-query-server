package rest;

import application.JdbcApplication;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.restlet.Component;
import org.restlet.data.Protocol;

/**
 * User: yanbit
 * Date: 2016/2/3
 * Time: 10:33
 */
public class RestMain {
    private static final Log LOG = LogFactory.getLog(RestMain.class);

    public static void main(String[] args) throws Exception {
        // Create a new Component.
        Component component = new Component();

        // Add a new HTTP server listening on port 8088.
        component.getServers().add(Protocol.HTTP, 8088);

        // Attach the sample application.
        component.getDefaultHost().attach("/taotaole/query",
                new JdbcApplication());
//        component.getDefaultHost().attach("/monitor/hbase/log/reset",
//                new HBaseResetApplication());
        // Start the component.
        component.start();
        LOG.info("starting the http server on port:" + 8282);
    }
}
