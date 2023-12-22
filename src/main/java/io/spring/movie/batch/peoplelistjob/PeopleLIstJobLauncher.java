package io.spring.movie.batch.peoplelistjob;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

//@Component
@RequiredArgsConstructor
public class PeopleLIstJobLauncher extends QuartzJobBean {

    private final Job peopleListJob;
    private final JobLauncher jobLauncher;

    @Override
    @SneakyThrows
    protected void executeInternal(JobExecutionContext context) {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("id", new Date().getTime())
                .toJobParameters();

        jobLauncher.run(peopleListJob, jobParameters);
    }
}
