/**
 * 
 */
package nest.generator;

import nest.model.DBConfig;
import nest.model.ProjectInfo;

/**
 * @author zhangle
 *
 */
public class AppContextGenerator extends AbstractBaseGenerator {

    private ProjectInfo projInfo;

    public AppContextGenerator(DBConfig config, ProjectInfo proj) {
        this.projInfo = proj;
        super.setTemplateParam("DRIVER_CLASS", config.getDriverClass());
        String dbUrl = config.getConnectionUrl();
        try {
            dbUrl = dbUrl.replaceAll("&", "&amp;");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        super.setTemplateParam("CONNECTION_URL", dbUrl);
        super.setTemplateParam("USERNAME", config.getUserName());
        super.setTemplateParam("PASSWORD", config.getPassword());
        super.setTemplateParam("VERTX_PACKAGE", super.vertxPackage(proj));
        super.setTemplateParam("ROUTER_PACKAGE", super.routerPackage(proj));
        super.setTemplateParam("DAO_PACKAGE", super.daoPackage(proj));
        super.setTemplateParam("POJO_PACKAGE", super.pojoPackage(proj));
        super.setTemplateParam("SERVICE_PACKAGE", super.servicePackage(proj));
        super.setTemplateParam("UTIL_PACKAGE", super.utilPackage(proj));
    }

    @Override
    public boolean generate() {
        String tpl = super.readTemplate("src/main/resources/templates/springContext.tpl");
        String appCtxFolder = super.appCtxFolder(projInfo);
        String result = super.parseTemplate(tpl);
        String fileName = "springContext.xml";
        super.writeResult(result, appCtxFolder, fileName);

        return true;
    }

}