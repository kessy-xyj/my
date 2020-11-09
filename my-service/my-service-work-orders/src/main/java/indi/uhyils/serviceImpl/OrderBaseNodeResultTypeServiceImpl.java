package indi.uhyils.serviceImpl;

import org.apache.dubbo.config.annotation.Service;
import indi.uhyils.annotation.NoToken;
import indi.uhyils.content.Content;
import indi.uhyils.dao.OrderBaseNodeResultTypeDao;
import indi.uhyils.enum_.ServiceCode;
import indi.uhyils.pojo.model.*;
import indi.uhyils.service.OrderBaseNodeResultTypeService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年11月09日 10时11分
 */
@Service(group = "${spring.profiles.active}")
public class OrderBaseNodeResultTypeServiceImpl extends BaseDefaultServiceImpl<OrderBaseNodeResultTypeEntity> implements OrderBaseNodeResultTypeService {

    @Autowired
    private OrderBaseNodeResultTypeDao dao;


    public OrderBaseNodeResultTypeDao getDao() {
        return dao;
    }

    public void setDao(OrderBaseNodeResultTypeDao dao) {
        this.dao = dao;
    }
}
