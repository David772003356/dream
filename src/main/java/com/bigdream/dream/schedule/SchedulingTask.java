package com.bigdream.dream.schedule;

import com.bigdream.dream.entity.DreamScheduleCronDO;
import com.bigdream.dream.mapper.DreamScheduleCronMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.StringUtil;

import java.time.LocalDateTime;

/**
 * @description:
 * @author: Wu Yuwei
 * @create: 2020-07-03 13:51
 **/
@Configuration
@EnableScheduling
@RestController
@Slf4j
public class SchedulingTask  implements SchedulingConfigurer {
    @Autowired
    private DreamScheduleCronMapper dreamScheduleCronMapper;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        /**
        * @Description: 待付款订单任务检查
        * @Author: Wu Yuwei
        * @Date: 2020/7/6 15:14
        */
        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                ()-> System.out.println("执行时间"+ LocalDateTime.now()),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期 0/5 * * * * ?
                    DreamScheduleCronDO result = dreamScheduleCronMapper.getScheduleCronByTaskId("ActiveMQ_task");
                    String taskTime = result.getCron();
                    if (StringUtil.isEmpty(taskTime)){
                        log.error("==checkUnPayOrderTask==,执行周期为空");
                        return null;
                    }
                    //2.2 返回执行周期(Date)
                    return new CronTrigger(taskTime).nextExecutionTime(triggerContext);
                }
        );
    }
}
