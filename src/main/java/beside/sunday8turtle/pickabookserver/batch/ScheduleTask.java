package beside.sunday8turtle.pickabookserver.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ScheduleTask {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job notiJob;

    //5초에 한 번씩 스케줄
    @Scheduled(cron = "0/5 * * * * *")
    public void jobScheduled() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Map<String, JobParameter> jobParametersMap = new HashMap<>();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Date time = new Date();
        String time1 = format1.format(time);

        jobParametersMap.put("date",new JobParameter(time1));

        JobParameters parameters = new JobParameters(jobParametersMap);

        jobLauncher.run(notiJob, parameters);
    }
}
