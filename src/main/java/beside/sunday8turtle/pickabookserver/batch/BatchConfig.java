package beside.sunday8turtle.pickabookserver.batch;

import beside.sunday8turtle.pickabookserver.batch.tasklet.PushEmailTasklet;
import beside.sunday8turtle.pickabookserver.batch.tasklet.PushNotiTasklet;
import beside.sunday8turtle.pickabookserver.modules.bookmark.service.BookmarkService;
import beside.sunday8turtle.pickabookserver.modules.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

// 배치 구성 클래스
@Slf4j
@Configuration // Bean 정의 클래스라는 것을 명시하는 어노테이션
@RequiredArgsConstructor // Lombok에 의한 생성자 자동 생성
@EnableBatchProcessing
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    @Autowired
    private final MailService mailService;
    @Autowired
    private final BookmarkService bookmarkService;

    @Bean
    public Job notiJob() {
        return jobBuilderFactory.get("notiJob") // 일억성이 되는 임의 잡 이름을 지정
                .flow(pushEmailStep()) // 실행하는 Step을 지정
//                .next(pushNotiStep()) // 실행하는 Step을 지정
                .end()
                .build();
    }

    @Bean
    public Step pushEmailStep() {
        LocalDate currentDate = LocalDate.now();
        return stepBuilderFactory.get("pushEmailStep") // 임의의 스탭 이름을 지정
                .tasklet(new PushEmailTasklet(currentDate, mailService, bookmarkService)) // 실행하는 Tasklet을 지정
                .build();
    }

    @Bean
    public Step pushNotiStep() {
        System.out.println("pushNotiStep 메서드를 실행");
        return stepBuilderFactory.get("pushNotiStep") // 임의의 스탭 이름을 지정
                .tasklet(new PushNotiTasklet("World!")) // 실행하는 Tasklet을 지정
                .build();
    }
}