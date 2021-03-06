package indi.uhyils.serviceImpl;

import indi.uhyils.annotation.ReadWriteMark;
import indi.uhyils.dao.DeptDao;
import indi.uhyils.dao.MenuDao;
import indi.uhyils.dao.RoleDao;
import indi.uhyils.enum_.ReadWriteTypeEnum;
import indi.uhyils.pojo.model.DeptEntity;
import indi.uhyils.pojo.model.DeptMenuMiddle;
import indi.uhyils.pojo.model.DeptPowerMiddle;
import indi.uhyils.pojo.request.PutDeptsToMenuRequest;
import indi.uhyils.pojo.request.PutMenusToDeptsRequest;
import indi.uhyils.pojo.request.PutPowersToDeptRequest;
import indi.uhyils.pojo.request.base.IdRequest;
import indi.uhyils.pojo.request.base.IdsRequest;
import indi.uhyils.pojo.response.GetAllMenuWithHaveMarkResponse;
import indi.uhyils.pojo.response.GetAllPowerWithHaveMarkResponse;
import indi.uhyils.pojo.response.base.ServiceResult;
import indi.uhyils.service.DeptService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年05月27日 16时28分
 */
@Service(group = "${spring.profiles.active}")
@ReadWriteMark(tables = {"sys_dept"})
public class DeptServiceImpl extends BaseDefaultServiceImpl<DeptEntity> implements DeptService {
    @Resource
    private DeptDao dao;

    @Resource
    private MenuDao menuDao;

    @Resource
    private RoleDao roleDao;

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_dept_power"})
    public ServiceResult<Boolean> putPowersToDept(PutPowersToDeptRequest request) {
        dao.deleteDeptPowerMiddleByDeptId(request.getDeptId());
        String deptId = request.getDeptId();
        for (String powerId : request.getPowerIds()) {
            DeptPowerMiddle middle = DeptPowerMiddle.build(deptId, powerId);
            middle.preInsert(request);
            dao.insertDeptPower(middle);
        }
        return ServiceResult.buildSuccessResult("权限集添加权限成功", true, request);
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_dept_power"})
    public ServiceResult<Boolean> deleteDeptPower(IdsRequest idsRequest) {
        dao.deleteDeptPower(idsRequest.getIds());
        return ServiceResult.buildSuccessResult("删除成功", true, idsRequest);
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_dept_menu"})
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public ServiceResult<Boolean> putMenusToDept(PutMenusToDeptsRequest request) {
        List<DeptMenuMiddle> build = DeptMenuMiddle.build(request.getDeptId(), request.getMenuIds());
        String deptId = request.getDeptId();
        List<String> deptIds = new ArrayList<>(1);
        deptIds.add(deptId);
        menuDao.deleteDeptMenuByDeptIds(deptIds);
        build.forEach(t -> {
            t.preInsert(request);
            dao.insertDeptMenu(t);
        });
        return ServiceResult.buildSuccessResult("赋权成功", true, request);
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_dept_menu"})
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public ServiceResult<Boolean> putDeptsToMenu(PutDeptsToMenuRequest request) {
        String menuId = request.getMenuId();
        List<String> menuIds = new ArrayList<>(1);
        menuIds.add(menuId);
        menuDao.deleteDeptMenuByMenuIds(menuIds);
        List<DeptMenuMiddle> build = DeptMenuMiddle.build(request.getDeptIds(), request.getMenuId());
        build.forEach(t -> {
            t.preInsert(request);
            dao.insertDeptMenu(t);
        });
        return ServiceResult.buildSuccessResult("赋权成功", true, request);
    }

    @Override
    public ServiceResult<ArrayList<DeptEntity>> getDepts(PutDeptsToMenuRequest request) {
        return ServiceResult.buildSuccessResult("获取成功", dao.getAll(), request);
    }

    @Override
    @ReadWriteMark(tables = {"sys_dept_menu", "sys_menu"})
    public ServiceResult<ArrayList<GetAllMenuWithHaveMarkResponse>> getAllMenuWithHaveMark(IdRequest request) {
        ArrayList<GetAllMenuWithHaveMarkResponse> list = menuDao.getAllMenuWithHaveMark(request.getId());
        return ServiceResult.buildSuccessResult("查询菜单(包含羁绊)成功", list, request);
    }

    @Override
    @ReadWriteMark(tables = {"sys_dept_power", "sys_power"})
    public ServiceResult<ArrayList<GetAllPowerWithHaveMarkResponse>> getAllPowerWithHaveMark(IdRequest request) {
        ArrayList<GetAllPowerWithHaveMarkResponse> list = dao.getAllPowerWithHaveMark(request.getId());
        return ServiceResult.buildSuccessResult("查询权限(包含羁绊)成功", list, request);
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE, tables = {"sys_dept", "sys_dept_power", "sys_dept_menu", "sys_role_dept"})
    public ServiceResult<Boolean> deleteDept(IdRequest request) {
        DeptEntity t = getDao().getById(request.getId());
        if (t == null) {
            return ServiceResult.buildFailedResult("查无此人", null, request);
        }
        t.setDeleteFlag(true);
        t.preUpdate(request);
        getDao().update(t);
        dao.deleteDeptPowerMiddleByDeptId(request.getId());
        dao.deleteDeptMenuMiddleByDeptId(request.getId());
        dao.deleteRoleDeptMiddleByDeptId(request.getId());

        return ServiceResult.buildSuccessResult("删除成功", true, request);
    }


    public DeptDao getDao() {
        return dao;
    }

    public void setDao(DeptDao dao) {
        this.dao = dao;
    }
}
