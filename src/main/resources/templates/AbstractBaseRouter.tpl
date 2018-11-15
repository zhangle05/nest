/**
 * 
 */
package ￥ROUTER_PACKAGE￥;

import org.apache.commons.lang3.StringUtils;

import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import ￥VERTX_PACKAGE￥.ErrorMessage;

/**
 * @author zhangle
 *
 */
public abstract class AbstractBaseRouter {

    public static final int DEFAULT_HTTP_FAILURE = 500;
    public static final int DEFAULT_HTTP_OK = 200;

    abstract public void handleRequest(RoutingContext ctx);

    protected String getCharset(HttpClientResponse response) {
        String charset = "utf-8";
        String contentType = response.getHeader("content-type");
        if (!StringUtils.isEmpty(contentType)) {
            String[] arr = contentType.split(";");
            if (arr.length > 1 && !StringUtils.isEmpty(arr[1])) {
                String[] charsetArr = arr[1].split("=");
                if (charsetArr.length > 1) {
                    charset = charsetArr[1];
                }
            }
        }
        return charset;
    }

    protected <T> T parseInput(RoutingContext ctx, Class<T> klass) {
        // input data checking
        String body = ctx.getBodyAsString();
        if (StringUtils.isEmpty(body)) {
            ErrorMessage err = new ErrorMessage("请求数据为空");
            ctx.response().setStatusCode(400).end(Json.encodePrettily(err));
            return null;
        }
        T result = null;
        try {
            result = Json.decodeValue(body, klass);
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorMessage err = new ErrorMessage("请求数据json格式错误");
            ctx.response().setStatusCode(400).end(Json.encodePrettily(err));
        }
        return result;
    }

}
