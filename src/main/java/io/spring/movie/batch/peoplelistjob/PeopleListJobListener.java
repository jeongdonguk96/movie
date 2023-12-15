package io.spring.movie.batch.peoplelistjob;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class PeopleListJobListener implements JobExecutionListener {

    long startTime = 0L;
    long endTime = 0L;
    long timeDiff = 0L;
    double transactionTime = 0;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("========== PeopleListJob START ==========");
        startTime = System.currentTimeMillis();
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        endTime = System.currentTimeMillis();
        log.info("========== PeopleListJob END ==========");

        timeDiff = (endTime - startTime);
        transactionTime = timeDiff / 1000.0;
        log.info("========== PeopleListJob 소요 시간 = {} ==========", transactionTime);
    }
}
