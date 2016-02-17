package com.weixin.service.task;

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

import com.weixin.bean.ScheduleJob;
import com.weixin.service.ScheduleJobService;
import com.weixin.task.QuartzJobFactory;
import com.weixin.task.QuartzJobFactoryDisallowConcurrentExecution;
/*spring怎么使用注解在初始化bean的时候init-method指定的方法
 * 用的三种指定特定操作的方法：
通过实现InitializingBean/DisposableBean 接口来定制初始化之后/销毁之前的操作方法；
通过<bean> 元素的 init-method/destroy-method属性指定初始化之后 /销毁之前调用的操作方法；
在指定方法上加上@PostConstruct或@PreDestroy注解来制定该方法是在初始化之后还是销毁之前调用。 
 */

public interface SchedulerTaskService {
	
	
	/** 
    * 添加任务 
    *  
    * @param scheduleJob 
    * @throws SchedulerException 
    */  
   public void addJob(ScheduleJob job) throws SchedulerException ;
   
   /**  
   * 获取所有计划中的任务列表  
   *   
   * @return  
   * @throws SchedulerException  
   */  
  public List<ScheduleJob> getAllJob() throws SchedulerException ;

  /** 
   * 所有正在运行的job 
   *  
   * @return 
   * @throws SchedulerException 
   */  
  public List<ScheduleJob> getRunningJob() throws SchedulerException ;

  /** 
   * 暂停一个job 
   *  
   * @param scheduleJob 
   * @throws SchedulerException 
   */  
  public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException ;

  /** 
   * 恢复一个job 
   *  
   * @param scheduleJob 
   * @throws SchedulerException 
   */  
  public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException ;

  /** 
   * 删除一个job 
   *  
   * @param scheduleJob 
   * @throws SchedulerException 
   */  
  public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException ;

  /** 
   * 立即执行job 
   *  
   * @param scheduleJob 
   * @throws SchedulerException 
   */  
  public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException ;

  /** 
   * 更新job时间表达式 
   *  
   * @param scheduleJob 
   * @throws SchedulerException 
   */  
  public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException;
   
  

}
