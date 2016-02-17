package com.weixin.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.bean.ScheduleJob;
import com.weixin.dao.ScheduleJobDao;
import com.weixin.service.ScheduleJobService;
import com.weixin.service.task.SchedulerTaskService;


@Service("scheduleJobService")
public class ScheduleJobServiceImpl implements ScheduleJobService{
	 private static final Logger LOGGER =  LoggerFactory.getLogger(ScheduleJobServiceImpl.class);
      @Autowired
	  private ScheduleJobDao scheduleJobDao;
      @Autowired
      private SchedulerTaskService  secheulerTaskService;
	public void save(ScheduleJob job) {
		// TODO Auto-generated method stub
		try {
			scheduleJobDao.save(job);
			secheulerTaskService.addJob(job);
			LOGGER.info("add task success");
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public List<ScheduleJob> getAll() {
		// TODO Auto-generated method stub
		return scheduleJobDao.findAll();
	}
	
	public void execute(ScheduleJob job) {
		// TODO Auto-generated method stub
		try {
		ScheduleJob jobNew = scheduleJobDao.findObjectById(job.getJobId());
			secheulerTaskService.runAJobNow(jobNew);
			 jobNew.setJobStatus(ScheduleJob.STATUS_RUNNING);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void stop(ScheduleJob job) {
		// TODO Auto-generated method stub
		try {
			ScheduleJob jobNew = scheduleJobDao.findObjectById(job.getJobId());
				secheulerTaskService.pauseJob(jobNew);
				 jobNew.setJobStatus(ScheduleJob.STATUS_NOT_RUNNING);
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void updateTime(ScheduleJob job) {
		// TODO Auto-generated method stub
		try {
			ScheduleJob jobNew = scheduleJobDao.findObjectById(job.getJobId());
			  jobNew.setCronExpression(job.getCronExpression());
				secheulerTaskService.updateJobCron(jobNew);
				
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void updateStatus(String status){
		ScheduleJob jobNew = scheduleJobDao.findObjectById(2L);
		 jobNew.setJobStatus(ScheduleJob.STATUS_RUNNING);
	}
	@SuppressWarnings("unchecked")
	public List<ScheduleJob> getInitAll() {
		// TODO Auto-generated method stub
		Session session = null;
		try{
		session =  scheduleJobDao.getSessionFactory().openSession();
		          session.beginTransaction();
		          List<ScheduleJob> list  = session.createQuery(" from "+ScheduleJob.class.getSimpleName()).list();
		          return list;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			  session.close();
		}
		return null;
	}

	

}
