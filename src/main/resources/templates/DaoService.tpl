/**
 * 
 */
package ￥SERVICE_PACKAGE￥;

import java.util.List;
import java.util.UUID;

import javax.naming.AuthenticationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangle
 *
 */
@Service("userService")
public class UserService {

    private final Log LOG = LogFactory.getLog(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ConsumerMapper consumerMapper;

    public User createUser(String mobileOrEmail, String clientEncryptedPwd) {
        LOG.info("create user:" + mobileOrEmail);
        User u = this.getUserByMobileOrEmail(mobileOrEmail, false);
        if (u == null) {
            u = new User();
            if (StringUtils.isMobile(mobileOrEmail)) {
                u.setMobileNumber(mobileOrEmail);
            } else if (StringUtils.isEmail(mobileOrEmail)) {
                u.setEmail(mobileOrEmail);
            } else {
                throw new IllegalArgumentException(
                        "'" + mobileOrEmail + "' format error. Use valid mobile or email please.");
            }
            java.util.Date now = new java.util.Date();
            u.setCreateTime(now);
            u.setUpdateTime(now);
            u.setUuid(UUID.randomUUID().toString());
            u.setPassword(UserHelper.generateEncryptPassword(u.getUuid(), clientEncryptedPwd));
            u.setStatus(UserStatus.CREATED);
            userMapper.insert(u);
        } else if (u.getStatus() == UserStatus.DELETED) {
            java.util.Date now = new java.util.Date();
            u.setCreateTime(now);
            u.setUpdateTime(now);
            u.setPassword(UserHelper.generateEncryptPassword(u.getUuid(), clientEncryptedPwd));
            u.setStatus(UserStatus.CREATED);
            userMapper.updateByPrimaryKey(u);
        } else {
            throw new IllegalArgumentException("User '" + mobileOrEmail + "' already registered.");
        }
        return u;
    }

    public User createUser(User u) {
        if (u == null) {
            throw new IllegalArgumentException("用户信息为空.");
        }
        if (StringUtils.isEmpty(u.getMobileNumber()) && StringUtils.isEmpty(u.getEmail())) {
            throw new IllegalArgumentException("手机号和email不能都为空.");
        }
        // check duplication
        UserExample example = new UserExample();
        UserExample.Criteria c1 = example.createCriteria();
        if (!StringUtils.isEmpty(u.getMobileNumber())) {
            c1.andMobileNumberEqualTo(u.getMobileNumber());
        }
        UserExample.Criteria c2 = example.or();
        if (!StringUtils.isEmpty(u.getEmail())) {
            c2.andEmailEqualTo(u.getEmail());
        }
        User deleted = null;
        List<User> list = userMapper.selectByExample(example);
        if (list.size() > 0) {
            if (list.get(0).getStatus() == UserStatus.DELETED) {
                deleted = list.get(0);
            } else {
                // duplicate mobile or email
                throw new IllegalArgumentException("手机号/email已存在.");
            }
        }
        if (deleted == null) {
            java.util.Date now = new java.util.Date();
            u.setCreateTime(now);
            u.setUpdateTime(now);
            u.setUuid(UUID.randomUUID().toString());
            u.setPassword(UserHelper.generateEncryptPassword(u));
            u.setStatus(UserStatus.CREATED);
            userMapper.insert(u);
        } else {
            u.setId(deleted.getId());
            u.setUuid(deleted.getUuid());
            java.util.Date now = new java.util.Date();
            u.setCreateTime(now);
            u.setUpdateTime(now);
            u.setPassword(UserHelper.generateEncryptPassword(u));
            u.setStatus(UserStatus.CREATED);
            userMapper.updateByPrimaryKey(u);
        }
        return u;
    }

    public User createUserBy3rd(User u, String consumerKey) throws AuthenticationException {
        LOG.debug("create by 3rd-party partner, user:" + u + ", consumerKey:" + consumerKey);
        ConsumerExample example = new ConsumerExample();
        ConsumerExample.Criteria c = example.createCriteria();
        c.andConsumerKeyEqualTo(consumerKey);
        List<Consumer> list = consumerMapper.selectByExample(example);
        if (list.size() <= 0) {
            throw new AuthenticationException("合作方 '" + consumerKey + "' 错误.");
        }
        return createUser(u);
    }

    public User deleteUserById(int userId) {
        User u = userMapper.selectByPrimaryKey(userId);
        if (u == null) {
            throw new IllegalArgumentException("User '" + userId + "' not exist.");
        }
        u.setStatus(UserStatus.DELETED);
        userMapper.updateByPrimaryKey(u);
        return u;
    }

    public User deleteUserByUuid(String uuid) {
        UserExample example = new UserExample();
        UserExample.Criteria c = example.createCriteria();
        c.andUuidEqualTo(uuid);
        List<User> list = userMapper.selectByExample(example);
        if (list.size() <= 0) {
            throw new IllegalArgumentException("User '" + uuid + "' not exist.");
        }
        User u = list.get(0);
        u.setStatus(UserStatus.DELETED);
        userMapper.updateByPrimaryKey(u);
        return u;
    }

    public User deleteUserByMobile(String mobile) {
        UserExample example = new UserExample();
        UserExample.Criteria c = example.createCriteria();
        c.andMobileNumberEqualTo(mobile);
        List<User> list = userMapper.selectByExample(example);
        if (list.size() <= 0) {
            throw new IllegalArgumentException("User '" + mobile + "' not exist.");
        }
        User u = list.get(0);
        u.setStatus(UserStatus.DELETED);
        userMapper.updateByPrimaryKey(u);
        return u;
    }

    public User deleteUserByEmail(String email) {
        UserExample example = new UserExample();
        UserExample.Criteria c = example.createCriteria();
        c.andEmailEqualTo(email);
        List<User> list = userMapper.selectByExample(example);
        if (list.size() <= 0) {
            throw new IllegalArgumentException("User '" + email + "' not exist.");
        }
        User u = list.get(0);
        u.setStatus(UserStatus.DELETED);
        userMapper.updateByPrimaryKey(u);
        return u;
    }

    public User getUserByOpenId(String openId, boolean createIfNotFound) {
        UserExample example = new UserExample();
        UserExample.Criteria c = example.createCriteria();
        c.andOpenIdEqualTo(openId);
        List<User> list = userMapper.selectByExample(example);
        if (list.size() > 0) {
            User u = list.get(0);
            if (u.getStatus() == UserStatus.DELETED) {
                throw new IllegalStateException("User '" + openId + "' is deleted.");
            }
            return u;
        }
        if (createIfNotFound) {
            User u = new User();
            u.setOpenId(openId);
            java.util.Date now = new java.util.Date();
            u.setCreateTime(now);
            u.setUpdateTime(now);
            u.setUuid(UUID.randomUUID().toString());
            u.setStatus(UserStatus.CREATED);
            userMapper.insert(u);
            return u;
        }
        return null;
    }

    public User getUserByMobileOrEmail(String mobileOrEmail, boolean excludeDeleted) {
        UserExample example = new UserExample();
        UserExample.Criteria c1 = example.createCriteria();
        c1.andMobileNumberEqualTo(mobileOrEmail);
        UserExample.Criteria c2 = example.or();
        c2.andEmailEqualTo(mobileOrEmail);
        List<User> list = userMapper.selectByExample(example);
        if (list.size() > 0) {
            if (excludeDeleted) {
                User u = list.get(0);
                if (u.getStatus() == UserStatus.DELETED) {
                    throw new IllegalStateException("User '" + mobileOrEmail + "' is deleted.");
                }
            }
            return list.get(0);
        }
        return null;
    }

    public User getUserByUUID(String uuid, boolean excludeDeleted) {
        UserExample example = new UserExample();
        UserExample.Criteria c = example.createCriteria();
        c.andUuidEqualTo(uuid);
        List<User> list = userMapper.selectByExample(example);
        if (list.size() > 0) {
            if (excludeDeleted) {
                User u = list.get(0);
                if (u.getStatus() == UserStatus.DELETED) {
                    throw new IllegalStateException("User '" + uuid + "' is deleted.");
                }
            }
            return list.get(0);
        }
        return null;
    }

    /**
     * update user properties (password excluded)
     *
     * @param input
     * @return
     */
    public User updateUser(User input) {
        User u = this.getUserByUUID(input.getUuid(), true);
        if (u == null) {
            throw new IllegalArgumentException("User '" + input.getUuid() + "' not found.");
        }
        u.setAvatar(input.getAvatar() == null ? u.getAvatar() : input.getAvatar());
        u.setCity(input.getCity() == null ? u.getCity() : input.getCity());
        u.setCountry(input.getCountry() == null ? u.getCountry() : input.getCountry());
        u.setEmail(input.getEmail() == null ? u.getEmail() : input.getEmail());
        u.setGender(input.getGender() == null ? u.getGender() : input.getGender());
        u.setLastLoginTime(input.getLastLoginTime() == null ? u.getLastLoginTime() : input.getLastLoginTime());
        u.setMobileNumber(input.getMobileNumber() == null ? u.getMobileNumber() : input.getMobileNumber());
        u.setNickName(input.getNickName() == null ? u.getNickName() : input.getNickName());
        u.setOpenId(input.getOpenId() == null ? u.getOpenId() : input.getOpenId());
        u.setProvince(input.getProvince() == null ? u.getProvince() : input.getProvince());
        u.setRealName(input.getRealName() == null ? u.getRealName() : input.getRealName());
        u.setRoleId(input.getRoleId() == null ? u.getRoleId() : input.getRoleId());
        u.setStatus(input.getStatus() == null ? u.getStatus() : input.getStatus());
        userMapper.updateByPrimaryKey(u);
        return u;
    }
}
