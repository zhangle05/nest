package ￥VERTX_PACKAGE￥;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

￥ROUTERS_IMPORT￥

/**
 * @author zhangle
 *
 */
public class ServerVerticle extends AbstractVerticle {

    private final Log LOG = LogFactory.getLog(this.getClass());

    ￥ROUTERS_DEFINE￥

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        // ROUTERS INITIALIZATION
        ￥ROUTERS_INIT￥

        ￥ROUTERS_HANDLER￥

        // start server
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }

    ￥ROUTERS_HANDLER_METHOD￥

}
