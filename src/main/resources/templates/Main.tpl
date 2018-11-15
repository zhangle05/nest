/**
 * 
 */
package ￥MAIN_PACKAGE￥;

import io.vertx.core.Vertx;
import ￥VERTX_PACKAGE￥.ServerVerticle;

/**
 * @author zhangle
 *
 */
public class ￥PROJECT_NAME￥Main {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        // 部署服务器模块
        System.out.println("部署￥PROJECT_NAME￥-Web服务模块");
        vertx.deployVerticle(new ServerVerticle());
    }

}
