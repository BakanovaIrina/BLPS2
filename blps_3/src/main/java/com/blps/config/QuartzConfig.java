package com.blps.config;

import com.blps.config.jobs.DeleteJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.quartz.CronScheduleBuilder.cronSchedule;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(DeleteJob.class)
                .withIdentity("monthlyJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger jobTrigger() {
        // Запускаем задачу в первый день каждого месяца в полночь
        //0 0 0 1 * ?
        //0 * * * * ?\n
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("monthlyJobTrigger")
                .withSchedule(cronSchedule("0 * * * * ?\n"))
                .build();
    }
}
