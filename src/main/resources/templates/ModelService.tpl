/**
 * 
 */
package ￥SERVICE_PACKAGE￥;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ￥DAO_PACKAGE￥.￥MODEL_NAME￥Mapper;
import ￥POJO_PACKAGE￥.￥MODEL_NAME￥;

/**
 * @author zhangle
 *
 */
@Service("￥MODEL_VAR_NAME￥Service")
public class ￥MODEL_NAME￥Service {

    private final Log LOG = LogFactory.getLog(this.getClass());

    @Autowired
    private ￥MODEL_NAME￥Mapper ￥MODEL_VAR_NAME￥Mapper;

    public ￥MODEL_NAME￥ create￥MODEL_NAME￥(￥MODEL_NAME￥ ￥MODEL_VAR_NAME￥) {
        LOG.info("creating ￥MODEL_NAME￥" + ￥MODEL_VAR_NAME￥);
        if (￥MODEL_VAR_NAME￥ == null) {
            throw new IllegalArgumentException("￥MODEL_NAME￥信息为空.");
        }
        // TODO:check duplication
        ￥MODEL_VAR_NAME￥Mapper.insert(￥MODEL_VAR_NAME￥);
        return ￥MODEL_VAR_NAME￥;
    }

    public ￥MODEL_NAME￥ delete￥MODEL_NAME￥ById(long id) {
        LOG.info("deleting ￥MODEL_NAME￥" + id);
        ￥MODEL_NAME￥ ￥MODEL_VAR_NAME￥ = ￥MODEL_VAR_NAME￥Mapper.selectByPrimaryKey(id);
        if (￥MODEL_VAR_NAME￥ == null) {
            throw new IllegalArgumentException("￥MODEL_NAME￥ '" + id + "' not exist.");
        }
        ￥MODEL_VAR_NAME￥Mapper.deleteByPrimaryKey(id);
        return ￥MODEL_VAR_NAME￥;
    }

    public ￥MODEL_NAME￥ get￥MODEL_NAME￥ById(long id) {
        LOG.info("getting ￥MODEL_NAME￥" + id);
        return ￥MODEL_VAR_NAME￥Mapper.selectByPrimaryKey(id);
    }

    /**
     * update ￥MODEL_NAME￥ properties
     *
     * @param input
     * @return
     */
    public ￥MODEL_NAME￥ update￥MODEL_NAME￥(￥MODEL_NAME￥ input) {
        LOG.info("updating ￥MODEL_NAME￥" + input);
        ￥MODEL_NAME￥ ￥MODEL_VAR_NAME￥ = this.get￥MODEL_NAME￥ById(input.getId());
        if (￥MODEL_VAR_NAME￥ == null) {
            throw new IllegalArgumentException("￥MODEL_NAME￥ '" + input.getId() + "' not found.");
        }
        // TODO: update values
        // mimirBlog.setBlogAbstra(input.getBlogAbstra() == null ? mimirBlog.getBlogAbstra() : input.getBlogAbstra());
        ￥MODEL_VAR_NAME￥Mapper.updateByPrimaryKey(￥MODEL_VAR_NAME￥);
        return ￥MODEL_VAR_NAME￥;
    }
}
