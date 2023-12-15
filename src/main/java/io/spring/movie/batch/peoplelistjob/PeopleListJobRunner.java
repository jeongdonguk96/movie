package io.spring.movie.batch.peoplelistjob;

import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static org.quartz.JobBuilder.newJob;

@Component
@RequiredArgsConstructor
public class PeopleListJobRunner implements ApplicationRunner {

    @Value("${movie.peopleList-api.cron}")
    private String schedulerCron;
    private final Scheduler scheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobDetail jobDetail = buildJobDetail(PeopleLIstJobLauncher.class, "peopleListJob");
        Trigger trigger = buildTrigger(schedulerCron);

        scheduler.scheduleJob(jobDetail, trigger);

    }

    private Trigger buildTrigger(String schedulerExpression) {
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(schedulerExpression))
                .build();
    }

    private JobDetail buildJobDetail(Class job, String name) {
        return newJob(job).withIdentity(name)
                .build();
    }
}
