/**
 * 
 */
package nest.generator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import nest.model.DBConfig;
import nest.model.ProjectInfo;

/**
 * @author zhangle
 *
 */
public class MybatisConfigGenerator extends AbstractBaseGenerator {

    private DBConfig dbConfig;
    private ProjectInfo projInfo;
    private List<String> tables;

    public MybatisConfigGenerator(DBConfig config, ProjectInfo proj) {
        this.dbConfig = config;
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
        super.setTemplateParam("ROOT_PACKAGE", proj.getRootPackage());
        super.setTemplateParam("PROJECT_NAME", proj.getName());
    }

    @Override
    public boolean generate() {
        if (dbConfig == null) {
            throw new IllegalStateException("DB config is null!");
        }

        String tpl = super.readTemplate("src/main/resources/templates/mybatis-generator-config.tpl");
        String result = super.parseTemplate(tpl);
        String tableResult = this.getTableResult();
        result = super.parseTemplate(result, "TABLES_TO_GENERATE", tableResult);
        String outPath = "../" + this.projInfo.getName() + "/src/main/resources/";
        String fileName = "mybatis-generator-config.xml";
        super.writeResult(result, outPath, fileName);
        return true;
    }

    public List<String> getTables() {
        if (tables == null || tables.size() == 0) {
            readTablesFromDB();
        }
        return tables;
    }

    private String getTableResult() {
        List<String> tables = this.getTables();
        StringBuilder sb = new StringBuilder();
        for (String t : tables) {
            sb.append(this.generateTableConfig(t));
        }
        return sb.toString();
    }

    private String generateTableConfig(String tableName) {
        String tpl = "\n        <table schema=\"￥TABLE_NAME￥\" tableName=\"￥TABLE_NAME￥\">\n"
                + "            <generatedKey column=\"id\" sqlStatement=\"MySql\"\n"
                + "                identity=\"true\" />\n" + "        </table>\n";
        return super.parseTemplate(tpl, "TABLE_NAME", tableName);
    }

    private void readTablesFromDB() {
        Connection conn = null;
        try {
            tables = new ArrayList<String>();
            Class.forName(dbConfig.getDriverClass());
            conn = DriverManager.getConnection(dbConfig.getConnectionUrl(), dbConfig.getUserName(),
                    dbConfig.getPassword());
            System.out.println("init connection done, connection is:" + conn);
            java.sql.DatabaseMetaData dbmd = conn.getMetaData();
            java.sql.ResultSet rs = dbmd.getTables(null, null, "%", null);
            while (rs.next()) {
                if (rs.getString(4).equalsIgnoreCase("TABLE")) {
                    tables.add(rs.getString("TABLE_NAME"));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ignore) {
                }
            }
        }
    }
}
