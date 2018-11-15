/**
 * 
 */
package nest.generator;

import nest.model.ProjectInfo;

/**
 * @author zhangle
 *
 */
public class MainGenerator extends AbstractBaseGenerator {

    private ProjectInfo projInfo;

    public MainGenerator(ProjectInfo proj) {
        this.projInfo = proj;
        super.setTemplateParam("PROJECT_NAME", proj.getName());
        super.setTemplateParam("VERTX_PACKAGE", super.vertxPackage(proj));
        super.setTemplateParam("ROUTER_PACKAGE", super.routerPackage(proj));
        super.setTemplateParam("DAO_PACKAGE", super.daoPackage(proj));
        super.setTemplateParam("POJO_PACKAGE", super.pojoPackage(proj));
        super.setTemplateParam("SERVICE_PACKAGE", super.servicePackage(proj));
        super.setTemplateParam("MAIN_PACKAGE", super.mainPackage(proj));
    }

    @Override
    public boolean generate() {
        String tpl = super.readTemplate("src/main/resources/templates/Main.tpl");
        String mainPkg = super.mainPackage(projInfo);
        String pkgPath = mainPkg.replace(".", "/") + "/";
        String outPath = "../" + this.projInfo.getName() + "/src/main/java/" + pkgPath;
        String result = super.parseTemplate(tpl);
        String fileName = projInfo.getName() + "Main.java";
        super.writeResult(result, outPath, fileName);

        return true;
    }

}