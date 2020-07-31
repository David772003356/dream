package com.bigdream.dream.schedule;

/**
 * @description:
 * @author: Wu Yuwei
 * @create: 2020-07-30 19:46
 **/
//@Component
//@EnableScheduling
//public class  ScheduleTask implements SchedulingConfigurer {
//    @Autowired
//    private DreamScheduleCronMapper dreamScheduleCronMapper;
//
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        Runnable task = new Runnable() {
//            @Override
//            public void run() {
//                //任务逻辑代码部分.
////                System.out.println("I am going:" + DateUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
//            }
//        };
//        Trigger trigger = new Trigger() {
//            @Override
//            public Date nextExecutionTime(TriggerContext triggerContext) {
//                //任务触发，可修改任务的执行周期.
//                //每一次任务触发，都会执行这里的方法一次，重新获取下一次的执行时间
//                String activeMQ_task_cron="";
//                DreamScheduleCronDO activeMQ_task = dreamScheduleCronMapper.getScheduleCronByTaskId("ActiveMQ_task");
//                if (null!=activeMQ_task){
//                    activeMQ_task_cron = activeMQ_task.getCron();
//                }
//                CronTrigger trigger = new CronTrigger(activeMQ_task_cron);
//                Date nextExec = trigger.nextExecutionTime(triggerContext);
//                return nextExec;
//            }
//        };
//        taskRegistrar.addTriggerTask(task, trigger);
//    }
//
//}
