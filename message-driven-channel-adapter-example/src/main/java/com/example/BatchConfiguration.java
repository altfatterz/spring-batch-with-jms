package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@Slf4j
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step step() {
        return stepBuilderFactory.get("myStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info("started step");
                    Map<String, Object> jobParameters = chunkContext.getStepContext().getJobParameters();
                    for (String key : jobParameters.keySet()) {
                        log.info("key: {} and value: {}", key, jobParameters.get(key));
                    }
                    return RepeatStatus.FINISHED;
                }).build();
    }


    @Bean
    public Job job(Step step) throws Exception {
        return jobBuilderFactory.get("myJob")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

}
