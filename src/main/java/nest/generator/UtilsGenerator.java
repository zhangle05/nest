/**
 * 
 */
package nest.generator;

import nest.model.ProjectInfo;

/**
 * @author zhangle
 *
 */
public class UtilsGenerator extends AbstractBaseGenerator {

    private ProjectInfo projInfo;

    public UtilsGenerator(ProjectInfo proj) {
        this.projInfo = proj;
        super.setTemplateParam("VERTX_PACKAGE", super.vertxPackage(proj));
        super.setTemplateParam("ROUTER_PACKAGE", super.routerPackage(proj));
        super.setTemplateParam("DAO_PACKAGE", super.daoPackage(proj));
        super.setTemplateParam("POJO_PACKAGE", super.pojoPackage(proj));
        super.setTemplateParam("SERVICE_PACKAGE", super.servicePackage(proj));
        super.setTemplateParam("UTIL_PACKAGE", super.utilPackage(proj));
    }

    @Override
    public boolean generate() {
        String tpl = super.readTemplate("src/main/resources/templates/SpringContextHelper.tpl");
        String utilPkg = super.utilPackage(projInfo);
        String pkgPath = utilPkg.replace(".", "/") + "/";
        String outPath = "../" + this.projInfo.getName() + "/src/main/java/" + pkgPath;
        String result = super.parseTemplate(tpl);
        String fileName = "SpringContextHelper.java";
        super.writeResult(result, outPath, fileName);

        return true;
    }

}
