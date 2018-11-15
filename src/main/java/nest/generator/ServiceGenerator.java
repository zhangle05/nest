/**
 * 
 */
package nest.generator;

import java.util.List;

import nest.model.ProjectInfo;

/**
 * @author zhangle
 *
 */
public class ServiceGenerator extends AbstractBaseGenerator {

    private ProjectInfo projInfo;
    private List<String> dbTables;

    public ServiceGenerator(ProjectInfo proj, List<String> tables) {
        this.projInfo = proj;
        this.dbTables = tables;
        super.setTemplateParam("VERTX_PACKAGE", super.vertxPackage(proj));
        super.setTemplateParam("ROUTER_PACKAGE", super.routerPackage(proj));
        super.setTemplateParam("DAO_PACKAGE", super.daoPackage(proj));
        super.setTemplateParam("POJO_PACKAGE", super.pojoPackage(proj));
        super.setTemplateParam("SERVICE_PACKAGE", super.servicePackage(proj));
        super.setTemplateParam("UTIL_PACKAGE", super.utilPackage(proj));
    }

    @Override
    public boolean generate() {
        String tpl = super.readTemplate("src/main/resources/templates/ModelService.tpl");
        String servicePkg = super.servicePackage(projInfo);
        String pkgPath = servicePkg.replace(".", "/") + "/";
        String outPath = "../" + this.projInfo.getName() + "/src/main/java/" + pkgPath;
        for (String t : dbTables) {
            String clsName = super.dbName2ClassName(t);
            String varName = super.dbName2VariableName(t);
            super.setTemplateParam("MODEL_NAME", clsName);
            super.setTemplateParam("MODEL_VAR_NAME", varName);
            String result = super.parseTemplate(tpl);
            String fileName = clsName + "Service.java";
            super.writeResult(result, outPath, fileName);
        }

        return true;
    }

}