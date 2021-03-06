package indi.uhyils.serviceImpl;

import indi.uhyils.annotation.ReadWriteMark;
import indi.uhyils.enum_.ReadWriteTypeEnum;
import indi.uhyils.mongo.MongoManager;
import indi.uhyils.pojo.request.NameRequest;
import indi.uhyils.pojo.request.base.ObjRequest;
import indi.uhyils.pojo.response.base.ServiceResult;
import indi.uhyils.service.MongoService;
import indi.uhyils.util.MD5Util;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年07月01日 07时08分
 */
@Service(group = "${spring.profiles.active}")
public class MongoServiceImpl implements MongoService {

    @Autowired
    private MongoManager mongoManager;

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE)
    public ServiceResult<String> add(ObjRequest<String> request) {
        String file = request.getData();
        String uuid = UUID.randomUUID().toString();
        String md5 = MD5Util.MD5Encode(uuid);
        boolean b1 = mongoManager.addFile(md5, file);
        if (b1) {
            return ServiceResult.buildSuccessResult("插入执行完成", md5, request);
        }
        return ServiceResult.buildFailedResult("插入出错", md5, request);
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE)
    public ServiceResult<Boolean> delete(NameRequest request) {
        String name = request.getName();
        boolean b = mongoManager.removeFile(name);
        return ServiceResult.buildSuccessResult("删除执行完成", b, request);
    }

    @Override
    public ServiceResult<String> getByFileName(NameRequest request) {
        String name = request.getName();
        String file = mongoManager.getFile(name);
        return ServiceResult.buildSuccessResult("获取文件成功", file, request);
    }


}
