/**
 * 
 */
package nest.model;

/**
 * @author zhangle
 *
 */
public class ProjectInfo {

    private String mavenGroupId;
    private String name;
    private String version;
    private String rootPackage;

    /**
     * @return the mavenGroupId
     */
    public String getMavenGroupId() {
        return mavenGroupId;
    }

    /**
     * @param mavenGroupId
     *            the mavenGroupId to set
     */
    public void setMavenGroupId(String mavenGroupId) {
        this.mavenGroupId = mavenGroupId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the rootPackage
     */
    public String getRootPackage() {
        return rootPackage;
    }

    /**
     * @param rootPackage
     *            the rootPackage to set
     */
    public void setRootPackage(String rootPackage) {
        this.rootPackage = rootPackage;
    }
}
