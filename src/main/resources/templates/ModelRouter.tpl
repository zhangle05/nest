/**
 * 
 */
package ￥ROUTER_PACKAGE￥;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import ￥POJO_PACKAGE￥.￥MODEL_NAME￥;
import ￥SERVICE_PACKAGE￥.￥MODEL_NAME￥Service;
import ￥UTIL_PACKAGE￥.SpringContextHelper;
import ￥VERTX_PACKAGE￥.ErrorMessage;

/**
 * @author zhangle
 *
 */
public class ￥MODEL_NAME￥Router extends AbstractBaseRouter {

    private final Log LOG = LogFactory.getLog(this.getClass());
    private ￥MODEL_NAME￥Service ￥MODEL_VAR_NAME￥Svc;

    /**
     * Constructor
     */
    public ￥MODEL_NAME￥Router() {
        ￥MODEL_VAR_NAME￥Svc = SpringContextHelper.getInstance().getBean(￥MODEL_NAME￥Service.class);
    }

    @Override
    public void handleRequest(RoutingContext ctx) {
        HttpMethod method = ctx.request().method();
        if (method == HttpMethod.POST) {
            this.create￥MODEL_NAME￥(ctx);
        } else if (method == HttpMethod.DELETE) {
            this.delete￥MODEL_NAME￥(ctx);
        } else if (method == HttpMethod.GET) {
            this.get￥MODEL_NAME￥(ctx);
        } else if (method == HttpMethod.PUT) {
            this.update￥MODEL_NAME￥(ctx);
        }
    }

    private void create￥MODEL_NAME￥(RoutingContext ctx) {
        ￥MODEL_NAME￥ value = parseInput(ctx, ￥MODEL_NAME￥.class);
        if (value == null) {
            // input data wrong
            return;
        }
        LOG.info("Creating ￥MODEL_NAME￥:" + value);
        try {
            ￥MODEL_NAME￥ result = ￥MODEL_VAR_NAME￥Svc.create￥MODEL_NAME￥(value);
            ctx.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(DEFAULT_HTTP_OK)
                    .end(Json.encodePrettily(result));
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorMessage err = new ErrorMessage("添加失败");
            ctx.response().putHeader("content-type", "application/json; charset=utf-8")
                    .setStatusCode(DEFAULT_HTTP_FAILURE).end(Json.encodePrettily(err));
        }
    }

    private void delete￥MODEL_NAME￥(RoutingContext ctx) {
        ￥MODEL_NAME￥ value = parseInput(ctx, ￥MODEL_NAME￥.class);
        if (value == null) {
            // input data wrong
            return;
        }
        LOG.info("Deleting ￥MODEL_NAME￥:" + value.getId());
        try {
            ￥MODEL_NAME￥ result = ￥MODEL_VAR_NAME￥Svc.delete￥MODEL_NAME￥ById(value.getId());
            ctx.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(DEFAULT_HTTP_OK)
                    .end(Json.encodePrettily(result));
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorMessage err = new ErrorMessage("删除失败");
            ctx.response().putHeader("content-type", "application/json; charset=utf-8")
                    .setStatusCode(DEFAULT_HTTP_FAILURE).end(Json.encodePrettily(err));
        }
    }

    private void get￥MODEL_NAME￥(RoutingContext ctx) {
        String idStr = ctx.request().getParam("id");
        long id = 0;
        try {
            id = Long.parseLong(idStr);
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorMessage err = new ErrorMessage("id '" + idStr + "' 格式错误");
            ctx.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(400)
                    .end(Json.encodePrettily(err));
            return;
        }
        LOG.info("Getting ￥MODEL_NAME￥:" + id);
        try {
            ￥MODEL_NAME￥ result = ￥MODEL_VAR_NAME￥Svc.get￥MODEL_NAME￥ById(id);
            ctx.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(DEFAULT_HTTP_OK)
                    .end(Json.encodePrettily(result));
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorMessage err = new ErrorMessage("获取失败");
            ctx.response().putHeader("content-type", "application/json; charset=utf-8")
                    .setStatusCode(DEFAULT_HTTP_FAILURE).end(Json.encodePrettily(err));
        }
    }

    private void update￥MODEL_NAME￥(RoutingContext ctx) {
        ￥MODEL_NAME￥ value = parseInput(ctx, ￥MODEL_NAME￥.class);
        if (value == null) {
            // input data wrong
            return;
        }
        LOG.info("updating ￥MODEL_NAME￥:" + value.getId());
        try {
            ￥MODEL_NAME￥ result = ￥MODEL_VAR_NAME￥Svc.update￥MODEL_NAME￥(value);
            ctx.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(DEFAULT_HTTP_OK)
                    .end(Json.encodePrettily(result));
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorMessage err = new ErrorMessage("修改失败");
            ctx.response().putHeader("content-type", "application/json; charset=utf-8")
                    .setStatusCode(DEFAULT_HTTP_FAILURE).end(Json.encodePrettily(err));
        }
    }

}
