package indi.uhyils.service;

import indi.uhyils.pojo.model.UserEntity;
import indi.uhyils.pojo.model.base.TokenInfo;
import indi.uhyils.pojo.request.DefaultRequest;
import indi.uhyils.pojo.request.GetUserRequest;
import indi.uhyils.pojo.request.IdRequest;
import indi.uhyils.pojo.request.LoginRequest;
import indi.uhyils.pojo.response.LoginResponse;
import indi.uhyils.pojo.response.ServiceResult;

import java.util.ArrayList;


/**
 * 用户接口API
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年04月20日 11时29分
 */
public interface UserService extends DefaultEntityService<UserEntity> {


    /**
     * 注入信息用,一般不用
     *
     * @param idRequest 用户的idi
     * @return 用户
     */
    ServiceResult<UserEntity> getUserByIdNoToken(IdRequest idRequest);


    /**
     * token用处是用来获取单点登录服务器使用 此后这个token项目可以是单点登录服务器的原型
     * 使用AES加密
     * 根据当前时间+用户id+盐 生产的token
     * <p>
     * token规则修改-> token有效时间设置为1个小时, 所以不需要加入年月,可以保留天
     * 规则修改为  ddhhmmss + 2位随机数(防重复) + 用户类型 + userId + salt 共8+2+1+16+3 = 30位
     * 如果是游客, 则userId随机生成一个16位数字序列作为这个游客的id
     *
     * @param userRequest 用户id
     * @return 通过用户id和用户类型编译的token
     */
    ServiceResult<String> getUserTokenNoToken(GetUserRequest userRequest);


    /**
     * 根据token 获取token中包含的信息
     * 解析token
     *
     * @param request 默认的信息
     * @return 解析后的token数据
     */
    ServiceResult<TokenInfo> getTokenInfoByTokenNoToken(DefaultRequest request);


    /**
     * 用户登录 包含从哪里登录的信息
     *
     * @param userRequest 用户登录所需要的条件
     * @return 登录所需要的信息
     */
    ServiceResult<LoginResponse> userLoginNoToken(LoginRequest userRequest);

    /**
     * 获取全部用户
     *
     * @param request 默认请求
     * @return 全部用户
     */
    ServiceResult<ArrayList<UserEntity>> getUsers(DefaultRequest request);

    /**
     * 根据用户获取id
     *
     * @return
     */
    ServiceResult<UserEntity> getUserById(IdRequest request);

}
