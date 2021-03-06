package indi.uhyils.pojo.rabbit;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import indi.uhyils.content.RabbitMqContent;
import indi.uhyils.dao.MonitorDao;
import indi.uhyils.dao.MonitorJvmStatusDetailDao;
import indi.uhyils.pojo.model.MonitorJvmStatusDetailDO;
import indi.uhyils.pojo.mqinfo.JvmStatusInfo;
import indi.uhyils.util.LogUtil;
import indi.uhyils.util.ModelTransUtils;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月19日 11时33分
 */
public class RabbitJvmStatusInfoConsumer extends DefaultConsumer {

    private MonitorJvmStatusDetailDao monitorJvmStatusDetailDao;

    private MonitorDao monitorDao;

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel            the channel to which this consumer is attached
     * @param applicationContext
     */
    public RabbitJvmStatusInfoConsumer(Channel channel, ApplicationContext applicationContext) {
        super(channel);
        monitorJvmStatusDetailDao = applicationContext.getBean(MonitorJvmStatusDetailDao.class);
        monitorDao = applicationContext.getBean(MonitorDao.class);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String text = new String(body, StandardCharsets.UTF_8);
        LogUtil.info(this, "接收到JVM状态信息");
        LogUtil.info(this, text);
        JvmStatusInfo jvmStatusInfo = JSONObject.parseObject(text, JvmStatusInfo.class);
        String id = monitorDao.getIdByJvmUniqueMark(jvmStatusInfo.getJvmUniqueMark());
        MonitorJvmStatusDetailDO monitorJvmStatusDetailDO = ModelTransUtils.transJvmStatusInfoToMonitorJvmStatusDetailDO(jvmStatusInfo, id);
        // 保存状态信息
        monitorJvmStatusDetailDO.preInsert(null);
        monitorJvmStatusDetailDao.insert(monitorJvmStatusDetailDO);
        Long time = monitorJvmStatusDetailDO.getTime();
        Double v = time + RabbitMqContent.OUT_TIME * 60 * 1000 * RabbitMqContent.OUT_TIME_PRO;
        // 修改主类假想结束时间
        monitorDao.changeEndTime(id, v.longValue());

        // 获取tag(队列中的唯一标示)
        long deliveryTag = envelope.getDeliveryTag();
        // 确认 false为不批量确认
        getChannel().basicAck(deliveryTag, false);

    }
}
