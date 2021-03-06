package indi.uhyils.serviceImpl;

import indi.uhyils.annotation.NoToken;
import indi.uhyils.annotation.ReadWriteMark;
import indi.uhyils.dao.MenuDao;
import indi.uhyils.dao.RoleDao;
import indi.uhyils.dao.UserDao;
import indi.uhyils.enum_.ReadWriteTypeEnum;
import indi.uhyils.pojo.model.DeptEntity;
import indi.uhyils.pojo.model.RoleDeptMiddle;
import indi.uhyils.pojo.model.RoleEntity;
import indi.uhyils.pojo.request.PutDeptsToRoleRequest;
import indi.uhyils.pojo.request.base.DefaultRequest;
import indi.uhyils.pojo.request.base.IdRequest;
import indi.uhyils.pojo.request.base.IdsRequest;
import indi.uhyils.pojo.response.GetAllDeptWithHaveMarkResponse;
import indi.uhyils.pojo.response.base.ServiceResult;
import indi.uhyils.service.RoleService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年05月27日 16时27分
 */
@Service(group = "${spring.profiles.active}")
@ReadWriteMark(tables = {"sys_role"})
public class RoleServiceImpl extends BaseDefaultServiceImpl<RoleEntity> implements RoleService {


    @Resource
    private RoleDao dao;

    @Resource
    private UserDao userDao;

    @Resource
    private MenuDao menuDao;


    @Override
    @NoToken
    public ServiceResult<RoleEntity> getRoleByRoleId(IdRequest request) {
        RoleEntity byId = dao.getById(request.getId());
        if (byId == null) {
            return ServiceResult.buildFailedResult("查询失败", null, request);
        }
        return ServiceResult.buildSuccessResult("获取用户角色成功", byId, request);
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_role_dept", "sys_role_dept"})
    public ServiceResult<Boolean> putDeptsToRole(PutDeptsToRoleRequest request) {

        String roleId = request.getRoleId();
        dao.deleteRoleDeptMiddleByRoleId(roleId);
        for (String deptId : request.getDeptIds()) {
            RoleDeptMiddle build = RoleDeptMiddle.build(roleId, deptId);
            build.preInsert(request);
            dao.insertRoleDept(build);
        }
        return ServiceResult.buildSuccessResult("角色添加权限集成功", true, request);
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_role_dept"})
    public ServiceResult<Boolean> deleteRoleDept(IdsRequest idsRequest) {
        dao.deleteRoleDept(idsRequest.getIds());
        return ServiceResult.buildSuccessResult("删除成功", true, idsRequest);
    }

    @Override
    public ServiceResult<ArrayList<RoleEntity>> getRoles(DefaultRequest request) {
        return ServiceResult.buildSuccessResult("查询成功", dao.getAll(), request);
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.READ, tables = {"sys_dept", "sys_role_dept"})
    public ServiceResult<ArrayList<DeptEntity>> getUserDeptsByRoleId(IdRequest request) {
        return ServiceResult.buildSuccessResult("查询角色成功", userDao.getUserDeptsByRoleId(request.getId()), request);
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.READ, tables = {"sys_role_dept", "sys_dept"})
    public ServiceResult<ArrayList<GetAllDeptWithHaveMarkResponse>> getAllDeptWithHaveMark(IdRequest request) {
        ArrayList<GetAllDeptWithHaveMarkResponse> allDeptWithHaveMark = dao.getAllDeptWithHaveMark(request.getId());
        return ServiceResult.buildSuccessResult("查询带存在标记的权限集成功", allDeptWithHaveMark, request);
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_role_dept", "sys_user"})
    public ServiceResult<Boolean> deleteRole(IdRequest request) {
        RoleEntity t = getDao().getById(request.getId());
        if (t == null) {
            return ServiceResult.buildFailedResult("查询失败", null, request);
        }
        t.setDeleteFlag(true);
        t.preUpdate(request);
        dao.update(t);
        dao.deleteRoleDeptMiddleByRoleId(request.getId());
        dao.updateUserRoleToNullByRoleId(request.getId());

        return ServiceResult.buildSuccessResult("删除成功", true, request);

    }


    public RoleDao getDao() {
        return dao;
    }

    public void setDao(RoleDao dao) {
        this.dao = dao;
    }
}
