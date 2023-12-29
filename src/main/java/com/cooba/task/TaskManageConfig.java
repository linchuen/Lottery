package com.cooba.task;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.quartz.CronScheduleBuilder.cronSchedule;

@Configuration
public class TaskManageConfig {
    @Bean
    public SchedulerFactoryBeanCustomizer schedulerFactoryBeanCustomizer(JobDetail[] jobDetails, Trigger[] triggers) {
        return schedulerFactoryBean -> {
//            schedulerFactoryBean.setConfigLocation(new ClassPathResource("quartz.properties"));
            schedulerFactoryBean.setOverwriteExistingJobs(true);
            schedulerFactoryBean.setAutoStartup(true);
            schedulerFactoryBean.setJobDetails(jobDetails);
            schedulerFactoryBean.setTriggers(triggers);
        };
    }

    @Bean
    public JobDetail markSixTask() {
        return JobBuilder.newJob(MarkSixLotteryDrawTask.class)
                .withIdentity("markSixLotteryDrawTask")
                .withDescription("六合彩開獎排程")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger markSixTrigger(@Qualifier("markSixTask") JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("markSixLotteryDrawTaskTrigger")
                .withSchedule(cronSchedule("55 59 * ? * *"))
                .build();
    }
}
