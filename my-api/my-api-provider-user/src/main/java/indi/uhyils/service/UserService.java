package indi.uhyils.service;

import indi.uhyils.pojo.model.UserEntity;
import indi.uhyils.pojo.model.base.TokenInfo;
import indi.uhyils.pojo.request.LoginRequest;
import indi.uhyils.pojo.request.UpdatePasswordRequest;
import indi.uhyils.pojo.request.base.DefaultRequest;
import indi.uhyils.pojo.request.base.IdRequest;
import indi.uhyils.pojo.response.LoginResponse;
import indi.uhyils.pojo.response.base.ServiceResult;
import indi.uhyils.service.base.DefaultEntityService;

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
    ServiceResult<UserEntity> getUserById(IdRequest idRequest);


    /**
     * token用处是用来获取单点登录服务器使用 此后这个token项目可以是单点登录服务器的原型
     * 使用AES加密
     * 根据当前时间+用户id+盐 生产的token
     * <p>
     * token规则修改-> token有效时间设置为1个小时, 所以不需要加入年月,可以保留天
     * 规则修改为  ddhhmmss + 2位随机数(防重复) + 用户类型 + userId + salt 共8+2+1+16+3 = 30位
     * 如果是游客, 则userId随机生成一个16位数字序列作为这个游客的id
     *
     * @param request 用户id
     * @return 通过用户id和用户类型编译的token
     */
    ServiceResult<String> getUserToken(IdRequest request);


    /**
     * 根据token 获取token中包含的信息
     * 解析token
     *
     * @param request 默认的信息
     * @return 解析后的token数据
     */
    ServiceResult<TokenInfo> getTokenInfoByToken(DefaultRequest request);


    /**
     * 用户登录 包含从哪里登录的信息
     *
     * @param userRequest 用户登录所需要的条件
     * @return 登录所需要的信息
     */
    ServiceResult<LoginResponse> login(LoginRequest userRequest);

    /**
     * 登出(删除redis中的用户)
     *
     * @param request 无参数
     * @return 是否登出成功
     */
    ServiceResult<Boolean> logout(DefaultRequest request);

    /**
     * 获取全部用户
     *
     * @param request 默认请求
     * @return 全部用户
     */
    ServiceResult<ArrayList<UserEntity>> getUsers(DefaultRequest request);

    /**
     * 默认获取用户本身的方式
     *
     * @param request 默认请求
     * @return 用户
     */
    ServiceResult<UserEntity> getUserByToken(DefaultRequest request);

    /**
     * 更新密码
     *
     * @param request 修改密码请求
     * @return 修改密码的返回
     */
    ServiceResult<String> updatePassword(UpdatePasswordRequest request);

    /**
     * 根据id获取用户名称
     *
     * @param request id
     * @return 用户名称
     */
    ServiceResult<String> getNameById(IdRequest request);


}
