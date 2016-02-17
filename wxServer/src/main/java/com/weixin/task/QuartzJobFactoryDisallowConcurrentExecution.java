package com.weixin.task;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.weixin.bean.ScheduleJob;
/*
 * 若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作 
 */
@DisallowConcurrentExecution 
public class QuartzJobFactoryDisallowConcurrentExecution implements Job{

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("开始 执行 ");
		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");  
		 System.out.println(scheduleJob.getMethodName()+"method ");
      TaskUtils.invokMethod(scheduleJob);  
		
	}

}
