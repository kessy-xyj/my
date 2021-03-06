package indi.uhyils.util;

import indi.uhyils.job.ExecutionJob;
import indi.uhyils.job.JobConfig;
import indi.uhyils.pojo.model.JobEntity;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 定时任务管理类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月25日 16时59分
 */
@Component
public class ScheduledManager {


    @Resource
    private Scheduler scheduler;

    public boolean addJob(JobEntity jobEntity) {
        try {
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ExecutionJob.class).
                    withIdentity(JobConfig.JOB_NAME + jobEntity.getId()).build();

            //通过触发器名和cron 表达式创建 Trigger
            Trigger cronTrigger = newTrigger()
                    .withIdentity(JobConfig.JOB_NAME + jobEntity.getId())
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobEntity.getCron()))
                    .build();

            cronTrigger.getJobDataMap().put(JobConfig.JOB_KEY, jobEntity);

            //重置启动时间
            ((CronTriggerImpl) cronTrigger).setStartTime(new Date());

            //执行定时任务
            scheduler.scheduleJob(jobDetail, cronTrigger);

            // 暂停任务
            if (jobEntity.getPause()) {
                pauseJob(jobEntity);
            }
        } catch (Exception e) {
            LogUtil.error(this, e);
            return false;
        }
        return true;
    }

    /**
     * 更新job cron表达式
     *
     * @param quartzJob /
     */
    public boolean updateJobCron(JobEntity quartzJob) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(JobConfig.JOB_NAME + quartzJob.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if (trigger == null) {
                addJob(quartzJob);
                trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            }
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getCron());
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            //重置启动时间
            ((CronTriggerImpl) trigger).setStartTime(new Date());
            trigger.getJobDataMap().put(JobConfig.JOB_KEY, quartzJob);

            scheduler.rescheduleJob(triggerKey, trigger);
            // 暂停任务
            if (quartzJob.getPause()) {
                pauseJob(quartzJob);
            }
        } catch (Exception e) {
            LogUtil.error(this, e);
            return false;
        }
        return true;

    }

    /**
     * 删除一个job
     *
     * @param quartzJob /
     */
    public boolean deleteJob(JobEntity quartzJob) {
        try {
            JobKey jobKey = JobKey.jobKey(JobConfig.JOB_NAME + quartzJob.getId());
            scheduler.pauseJob(jobKey);
            scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            LogUtil.error(this, e);
            return false;
        }
        return true;
    }

    /**
     * 恢复一个job
     *
     * @param quartzJob /
     */
    public boolean resumeJob(JobEntity quartzJob) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(JobConfig.JOB_NAME + quartzJob.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if (trigger == null) {
                addJob(quartzJob);
            }
            JobKey jobKey = JobKey.jobKey(JobConfig.JOB_NAME + quartzJob.getId());
            scheduler.resumeJob(jobKey);
        } catch (Exception e) {
            LogUtil.error(this, e);
            return false;
        }
        return true;
    }

    /**
     * 立即执行job
     *
     * @param quartzJob /
     */
    public boolean runJobNow(JobEntity quartzJob) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(JobConfig.JOB_NAME + quartzJob.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if (trigger == null) {
                addJob(quartzJob);
            }
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(JobConfig.JOB_KEY, quartzJob);
            JobKey jobKey = JobKey.jobKey(JobConfig.JOB_NAME + quartzJob.getId());
            scheduler.triggerJob(jobKey, dataMap);
        } catch (Exception e) {
            LogUtil.error(this, e);
            return false;
        }
        return true;
    }

    /**
     * 暂停一个job
     *
     * @param quartzJob /
     */
    public boolean pauseJob(JobEntity quartzJob) {
        try {
            JobKey jobKey = JobKey.jobKey(JobConfig.JOB_NAME + quartzJob.getId());
            scheduler.pauseJob(jobKey);
        } catch (Exception e) {
            LogUtil.error(this, e);
            return false;
        }
        return true;
    }
}
