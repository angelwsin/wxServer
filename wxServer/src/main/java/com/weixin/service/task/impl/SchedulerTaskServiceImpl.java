package com.weixin.service.task.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.weixin.bean.ScheduleJob;
import com.weixin.service.ScheduleJobService;
import com.weixin.service.task.SchedulerTaskService;
import com.weixin.task.QuartzJobFactory;
import com.weixin.task.QuartzJobFactoryDisallowConcurrentExecution;
/*spring怎么使用注解在初始化bean的时候init-method指定的方法
 * 用的三种指定特定操作的方法：
通过实现InitializingBean/DisposableBean 接口来定制初始化之后/销毁之前的操作方法；
通过<bean> 元素的 init-method/destroy-method属性指定初始化之后 /销毁之前调用的操作方法；
在指定方法上加上@PostConstruct或@PreDestroy注解来制定该方法是在初始化之后还是销毁之前调用。 
 */
@Service("schedulerTaskService")
public class SchedulerTaskServiceImpl implements SchedulerTaskService{
	private static final Logger LOGGER = LogManager.getLogger(SchedulerTaskServiceImpl.class);
	  @Autowired
	  private SchedulerFactoryBean schedulerFactoryBean;
	  @Autowired
	  private ScheduleJobService  scheduleJobService;
	  
	  @PostConstruct
	public void init() throws Exception { 
       Scheduler scheduler = schedulerFactoryBean.getScheduler();  
  
        // 这里从数据库中获取任务信息数据  
      LOGGER.info(" loading task job ...");
       List<ScheduleJob> jobList = scheduleJobService.getInitAll();  
     
       for (ScheduleJob job : jobList) {
            addJob(job);  
       }  
    }  
	
	/** 
    * 添加任务 
    *  
    * @param scheduleJob 
    * @throws SchedulerException 
    */  
   public void addJob(ScheduleJob job) throws SchedulerException {  
       if (job == null || ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {  
           return;  
       }  
 
       Scheduler scheduler = schedulerFactoryBean.getScheduler();  
      // log.debug(scheduler + ".......................................................................................add");  
       TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());  
 
       CronTrigger trigger =   (CronTrigger) scheduler.getTrigger(triggerKey);  
 
       // 不存在，创建一个  
       if (null == trigger) {  
    	   //CONCURRENT_IS来判断任务是否有状态
           Class clazz = ScheduleJob.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;  
 
           JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();  
 
           jobDetail.getJobDataMap().put("scheduleJob", job);  
 
           CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());  
 
           trigger =TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();  
          
           scheduler.scheduleJob(jobDetail, trigger);  

       } else {  
           // Trigger已存在，那么更新相应的定时设置  
           CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());  
 
           // 按新的cronExpression表达式重新构建trigger  
           
           trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();  
 
           // 按新的trigger重新设置job执行  
           scheduler.rescheduleJob(triggerKey, trigger);  
       }  
   }  
   
   /**  
   * 获取所有计划中的任务列表  
   *   
   * @return  
   * @throws SchedulerException  
   */  
  public List<ScheduleJob> getAllJob() throws SchedulerException {  
      Scheduler scheduler = schedulerFactoryBean.getScheduler();  
      GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();  
      Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);  
      List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();  
      for (JobKey jobKey : jobKeys) {  
          List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);  
          for (Trigger trigger : triggers) {  
              ScheduleJob job = new ScheduleJob();  
              job.setJobName(jobKey.getName());  
              job.setJobGroup(jobKey.getGroup());  
              job.setDescription("触发器:" + trigger.getKey());  
              Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());  
              job.setJobStatus(triggerState.name());  
              if (trigger instanceof CronTrigger) {  
                  CronTrigger cronTrigger = (CronTrigger) trigger;  
                  String cronExpression = cronTrigger.getCronExpression();  
                  job.setCronExpression(cronExpression);  
              }  
              jobList.add(job);  
          }  
      }  
      return jobList;  
  }  

  /** 
   * 所有正在运行的job 
   *  
   * @return 
   * @throws SchedulerException 
   */  
  public List<ScheduleJob> getRunningJob() throws SchedulerException {  
      Scheduler scheduler = schedulerFactoryBean.getScheduler();  
      List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();  
      List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());  
      for (JobExecutionContext executingJob : executingJobs) {  
          ScheduleJob job = new ScheduleJob();  
          JobDetail jobDetail = executingJob.getJobDetail();  
          JobKey jobKey = jobDetail.getKey();  
          Trigger trigger = executingJob.getTrigger();  
          job.setJobName(jobKey.getName());  
          job.setJobGroup(jobKey.getGroup());  
          job.setDescription("触发器:" + trigger.getKey());  
          Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());  
          job.setJobStatus(triggerState.name());  
          if (trigger instanceof CronTrigger) {  
              CronTrigger cronTrigger = (CronTrigger) trigger;  
              String cronExpression = cronTrigger.getCronExpression();  
              job.setCronExpression(cronExpression);  
          }  
          jobList.add(job);  
      }  
      return jobList;  
  }  

  /** 
   * 暂停一个job 
   *  
   * @param scheduleJob 
   * @throws SchedulerException 
   */  
  public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {  
      Scheduler scheduler = schedulerFactoryBean.getScheduler();  
      JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
      scheduler.pauseJob(jobKey);  
  }  

  /** 
   * 恢复一个job 
   *  
   * @param scheduleJob 
   * @throws SchedulerException 
   */  
  public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {  
      Scheduler scheduler = schedulerFactoryBean.getScheduler();  
      JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
      scheduler.resumeJob(jobKey);  
  }  

  /** 
   * 删除一个job 
   *  
   * @param scheduleJob 
   * @throws SchedulerException 
   */  
  public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {  
      Scheduler scheduler = schedulerFactoryBean.getScheduler();  
      JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
      scheduler.deleteJob(jobKey);  

  }  

  /** 
   * 立即执行job 
   *  
   * @param scheduleJob 
   * @throws SchedulerException 
   */  
  public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {  
      Scheduler scheduler = schedulerFactoryBean.getScheduler();  
      JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  
      System.out.println(jobKey);
      scheduler.triggerJob(jobKey);  
      LOGGER.info("excute task");
      System.out.println("excute task");
      
  }  

  /** 
   * 更新job时间表达式 
   *  
   * @param scheduleJob 
   * @throws SchedulerException 
   */  
  public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {  
      Scheduler scheduler = schedulerFactoryBean.getScheduler();  

      TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());  

      CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);  
     //更新表达式，判断表达式是否正确可用一下代码
      CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());  
          System.out.println(scheduleJob.getCronExpression());
      trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();  

      scheduler.rescheduleJob(triggerKey, trigger);  
  }  
   
  

}
