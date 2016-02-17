package com.weixin.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.weixin.bean.ScheduleJob;

public class QuartzJobFactory implements Job{

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		 ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");  
	        TaskUtils.invokMethod(scheduleJob);  
	}
	
	/*
	 * 真正执行计划任务的代码就在TaskUtils.invokMethod(scheduleJob)里面
通过scheduleJob的beanClass或springId通过反射或spring来获得需要执行的类,通过methodName来确定执行哪个方法
	 */

}
