/**
 * 
 */
package nest.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

import nest.model.ProjectInfo;

/**
 * @author zhangle
 *
 */
public class DaoGenerator extends AbstractBaseGenerator {

    private ProjectInfo projInfo;
    private List<String> dbTables;

    public DaoGenerator(ProjectInfo proj, List<String> tables) {
        this.projInfo = proj;
        this.dbTables = tables;
    }

    @Override
    public boolean generate() {
        if (projInfo == null) {
            throw new IllegalStateException("Project config is null!");
        }

        String tpl = super.readTemplate("src/main/resources/templates/generate-dao.tpl");
        String result = super.parseTemplate(tpl);
        String outPath = super.projRootPath(projInfo);
        String fileName = "generate-dao.bat";
        super.writeResult(result, outPath, fileName);
        fileName = "generate-dao.sh";
        super.writeResult(result, outPath, fileName);

        // make source folder
        File dir = new File("../" + this.projInfo.getName() + "/src/main/java/");
        if (dir.exists() || dir.mkdirs()) {
            try {
                ProcessBuilder builder = new ProcessBuilder();
                File workingDir = new File(outPath);
                builder.command(workingDir.getAbsolutePath() + "/generate-dao.bat");
                builder.directory(workingDir);
                Process p = builder.start();

                int exitCode = p.waitFor();
                if (exitCode != 0) {
                    throw new IllegalStateException("生成Dao失败，请检查Path设置，确保mvn命令能正确执行");
                }
                System.out.println("Exit code is:" + exitCode);
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                addDaoAnnotation();
            } catch (Exception e) {
                e.printStackTrace();
                //throw new IllegalStateException("Failed to generate DAOs.");
            }
            return true;
        }
        return false;
    }

    private void addDaoAnnotation() {
        String daoPkg = super.daoPackage(projInfo);
        String daoPath = super.projRootPath(projInfo) + "src/main/java/" + daoPkg.replace(".", "/") + "/";
        for (String t : dbTables) {
            try {
                String clsName = super.dbName2ClassName(t);
                String fileName = clsName + "Mapper.java";
                String input = super.readTemplate(daoPath + fileName);
                if (input.contains("@Repository")) {
                    continue;
                }
                String output = input.replace("public interface " + clsName + "Mapper",
                        "import org.springframework.stereotype.Repository;\n\n" + "@Repository\n" + "public interface "
                                + clsName + "Mapper");
                super.writeResult(output, daoPath, fileName);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
