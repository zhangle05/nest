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
public class VertxGenerator extends AbstractBaseGenerator {

    private ProjectInfo projInfo;
    private List<String> dbTables;

    public VertxGenerator(ProjectInfo proj, List<String> tables) {
        this.projInfo = proj;
        this.dbTables = tables;
        String routersImport = this.getRoutersImport();
        String routersDefine = this.getRoutersDefine();
        String routersInit = this.getRoutersInit();
        String routersHandler = this.getRoutersHandler();
        String routersHandlerMethod = this.getRoutersHandlerMethod();
        super.setTemplateParam("VERTX_PACKAGE", super.vertxPackage(proj));
        super.setTemplateParam("PROJECT_NAME", proj.getName());
        super.setTemplateParam("ROUTERS_IMPORT", routersImport);
        super.setTemplateParam("ROUTERS_DEFINE", routersDefine);
        super.setTemplateParam("ROUTERS_INIT", routersInit);
        super.setTemplateParam("ROUTERS_HANDLER", routersHandler);
        super.setTemplateParam("ROUTERS_HANDLER_METHOD", routersHandlerMethod);
    }

    @Override
    public boolean generate() {
        String tpl = super.readTemplate("src/main/resources/templates/ServerVerticle.tpl");
        String result = super.parseTemplate(tpl);
        String vertxPkg = super.vertxPackage(projInfo);
        String pkgPath = vertxPkg.replace(".", "/") + "/";
        String outPath = "../" + this.projInfo.getName() + "/src/main/java/" + pkgPath;
        String fileName = "ServerVerticle.java";
        super.writeResult(result, outPath, fileName);

        tpl = super.readTemplate("src/main/resources/templates/ErrorMessage.tpl");
        result = super.parseTemplate(tpl);
        fileName = "ErrorMessage.java";
        super.writeResult(result, outPath, fileName);

        return true;
    }

    private String getRoutersImport() {
        StringBuilder sb = new StringBuilder();
        for (String t : this.dbTables) {
            sb.append("import " + this.projInfo.getRootPackage() + ".vertx.router." + super.dbName2ClassName(t)
                    + "Router;\n");
        }
        return sb.toString();
    }

    private String getRoutersDefine() {
        StringBuilder sb = new StringBuilder();
        for (String t : this.dbTables) {
            sb.append("\n    private " + super.dbName2ClassName(t) + "Router " + super.dbName2VariableName(t)
                    + "Router;\n");
        }
        return sb.toString();
    }

    private String getRoutersInit() {
        StringBuilder sb = new StringBuilder();
        for (String t : this.dbTables) {
            sb.append("\n        " + super.dbName2VariableName(t) + "Router = new " + super.dbName2ClassName(t)
                    + "Router();\n");
        }
        return sb.toString();
    }

    private String getRoutersHandler() {
        StringBuilder sb = new StringBuilder();
        for (String t : this.dbTables) {
            String clsName = super.dbName2ClassName(t);
            String varName = super.dbName2VariableName(t);
            sb.append("\n        // " + clsName + " related handlers\n" + "        router.post(\"/" + clsName
                    + "/create\").handler(this::" + varName + "Handler);\n" + "        router.delete(\"/" + clsName
                    + "\").handler(this::" + varName + "Handler);\n" + "        router.get(\"/" + clsName
                    + "/:id\").handler(this::" + varName + "Handler);\n" + "        router.put(\"/" + clsName
                    + "\").handler(this::" + varName + "Handler);\n");
        }
        return sb.toString();
    }

    private String getRoutersHandlerMethod() {
        StringBuilder sb = new StringBuilder();
        for (String t : this.dbTables) {
            String clsName = super.dbName2ClassName(t);
            String varName = super.dbName2VariableName(t);
            sb.append("\n    private void " + varName + "Handler(RoutingContext ctx) {\n" + "        LOG.info(\"handle "
                    + clsName + " request.\");\n" + "        " + varName + "Router.handleRequest(ctx);\n" + "    }\n");
        }
        return sb.toString();
    }

}
