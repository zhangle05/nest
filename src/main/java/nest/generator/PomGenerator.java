/**
 * 
 */
package nest.generator;

import java.io.File;

import org.apache.commons.io.FileUtils;

import nest.model.ProjectInfo;

/**
 * @author zhangle
 *
 */
public class PomGenerator extends AbstractBaseGenerator {

    private ProjectInfo projInfo;

    public PomGenerator(ProjectInfo proj) {
        this.projInfo = proj;
        super.setTemplateParam("MAVEN_GROUP", proj.getMavenGroupId());
        super.setTemplateParam("ROOT_PACKAGE", proj.getRootPackage());
        super.setTemplateParam("PROJECT_NAME", proj.getName());
        super.setTemplateParam("PROJECT_VERSION", proj.getVersion());
    }

    @Override
    public boolean generate() {
        if (projInfo == null) {
            throw new IllegalStateException("Project config is null!");
        }

        String tpl = super.readTemplate("src/main/resources/templates/pom.tpl");
        String result = super.parseTemplate(tpl);
        String outPath = "../" + this.projInfo.getName() + "/";
        String fileName = "pom.xml";
        super.writeResult(result, outPath, fileName);

        // copy local repository
        File src = new File("local_repo");
        File dst = new File("../" + this.projInfo.getName() + "/local_repo");
        try {
            FileUtils.copyDirectory(src, dst);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to copy local repository.");
        }
        return true;
    }

}
