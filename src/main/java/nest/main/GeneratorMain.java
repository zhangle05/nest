/**
 * 
 */
package nest.main;

import nest.generator.AppContextGenerator;
import nest.generator.DaoGenerator;
import nest.generator.MainGenerator;
import nest.generator.MybatisConfigGenerator;
import nest.generator.PomGenerator;
import nest.generator.RoutersGenerator;
import nest.generator.ServiceGenerator;
import nest.generator.UtilsGenerator;
import nest.generator.VertxGenerator;
import nest.model.DBConfig;
import nest.model.ProjectInfo;

/**
 * @author zhangle
 *
 */
public class GeneratorMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        DBConfig config = new DBConfig();
        config.setDriverClass("com.mysql.jdbc.Driver");
        config.setConnectionUrl(
                "jdbc:mysql://localhost:3306/lean?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT");
        config.setUserName("lean");
        config.setPassword("lean123");

        ProjectInfo proj = new ProjectInfo();
        proj.setName("LeanBack");
        proj.setRootPackage("com.lean.innovation.api");
        proj.setMavenGroupId("com.lean.innovation");
        proj.setVersion("0.0.1");

        MybatisConfigGenerator gen = new MybatisConfigGenerator(config, proj);
        gen.generate();
        PomGenerator gen2 = new PomGenerator(proj);
        gen2.generate();
        DaoGenerator gen3 = new DaoGenerator(proj, gen.getTables());
        gen3.generate();
        VertxGenerator gen4 = new VertxGenerator(proj, gen.getTables());
        gen4.generate();
        RoutersGenerator gen5 = new RoutersGenerator(proj, gen.getTables());
        gen5.generate();
        ServiceGenerator gen6 = new ServiceGenerator(proj, gen.getTables());
        gen6.generate();
        UtilsGenerator gen7 = new UtilsGenerator(proj);
        gen7.generate();
        AppContextGenerator gen8 = new AppContextGenerator(config, proj);
        gen8.generate();
        MainGenerator gen9 = new MainGenerator(proj);
        gen9.generate();
    }

}
