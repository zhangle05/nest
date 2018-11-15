/**
 * 
 */
package nest.generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import nest.model.ProjectInfo;

/**
 * @author zhangle
 *
 */
public abstract class AbstractBaseGenerator {

    private HashMap<String, String> tplParamMap = new HashMap<String, String>();

    public abstract boolean generate();

    protected void setTemplateParam(String paramName, String paramValue) {
        this.tplParamMap.put(paramName, paramValue);
    }

    protected String getTemplateParamValue(String paramName) {
        return this.tplParamMap.get(paramName);
    }

    protected String parseTemplate(String template) {
        return this.parseTemplate(template, this.tplParamMap);
    }

    protected String parseTemplate(String template, HashMap<String, String> tplParamMap) {
        if (StringUtils.isEmpty(template)) {
            return template;
        }
        Iterator<String> it = tplParamMap.keySet().iterator();
        while (it.hasNext()) {
            String param = it.next();
            String value = tplParamMap.get(param);
            template = template.replaceAll("￥" + param + "￥", value);
        }
        return template;
    }

    protected String parseTemplate(String template, String param, String value) {
        if (StringUtils.isEmpty(template)) {
            return template;
        }
        template = template.replaceAll("￥" + param + "￥", value);
        return template;
    }

    protected String dbName2ClassName(String dbName) {
        String[] arr1 = dbName.split("_");
        List<String> words = new ArrayList<String>();
        for (String tmp : arr1) {
            String[] arr2 = tmp.split("-");
            for (String tmp2 : arr2) {
                words.add(tmp2);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(word.substring(0, 1).toUpperCase());
            sb.append(word.substring(1).toLowerCase());
        }
        return sb.toString();
    }

    protected String vertxPackage(ProjectInfo proj) {
        return proj.getRootPackage() + ".vertx";
    }

    protected String routerPackage(ProjectInfo proj) {
        return proj.getRootPackage() + ".vertx.router";
    }

    protected String daoPackage(ProjectInfo proj) {
        return proj.getRootPackage() + ".gen.dao";
    }

    protected String pojoPackage(ProjectInfo proj) {
        return proj.getRootPackage() + ".gen.pojo";
    }

    protected String servicePackage(ProjectInfo proj) {
        return proj.getRootPackage() + ".service";
    }

    protected String utilPackage(ProjectInfo proj) {
        return proj.getRootPackage() + ".util";
    }

    protected String mainPackage(ProjectInfo proj) {
        return proj.getRootPackage() + ".main";
    }

    protected String projRootPath(ProjectInfo proj) {
        return "../" + proj.getName() + "/";
    }

    protected String appCtxFolder(ProjectInfo proj) {
        return this.projRootPath(proj) + "src/main/resources/META-INF/";
    }

    protected String dbName2VariableName(String dbName) {
        String[] arr1 = dbName.split("_");
        List<String> words = new ArrayList<String>();
        for (String tmp : arr1) {
            String[] arr2 = tmp.split("-");
            for (String tmp2 : arr2) {
                words.add(tmp2);
            }
        }
        StringBuilder sb = new StringBuilder();
        String head = words.get(0);
        sb.append(head.toLowerCase());
        words.remove(0);
        for (String word : words) {
            sb.append(word.substring(0, 1).toUpperCase());
            sb.append(word.substring(1).toLowerCase());
        }
        return sb.toString();
    }

    protected String readTemplate(String tplPathName) {
        File f = new File(tplPathName);
        System.out.println("read template from:" + f.getAbsolutePath());
        FileInputStream fs = null;
        InputStreamReader in = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        int lineCount = 0;
        try {
            fs = new FileInputStream(f);
            in = new InputStreamReader(fs);
            br = new BufferedReader(in);
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                lineCount++;
                line = br.readLine();
            }
            System.out.println("read template file '" + tplPathName + "' done." + lineCount + " lines imported.");
            return sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Failed to read template file '" + tplPathName + "'.");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception ignore) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (Exception ignore) {
                }
            }
            if (fs != null) {
                try {
                    fs.close();
                } catch (Exception ignore) {
                }
            }
        }
    }

    protected void writeResult(String result, String filePath, String fileName) {
        File dir = new File(filePath);
        File f = new File(filePath + fileName);
        System.out.println("write result to:" + f.getAbsolutePath());
        FileOutputStream fos = null;
        OutputStreamWriter out = null;
        BufferedWriter bw = null;
        try {
            dir.mkdirs();
            if (!f.exists()) {
                f.createNewFile();
            }
            fos = new FileOutputStream(f);
            out = new OutputStreamWriter(fos);
            bw = new BufferedWriter(out);
            bw.write(result);
            bw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Failed to write result file '" + fileName + "'.");
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (Exception ignore) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception ignore) {
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception ignore) {
                }
            }
        }
    }
}
