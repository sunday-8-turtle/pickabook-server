package beside.sunday8turtle.pickabookserver.batch.tasklet;

import beside.sunday8turtle.pickabookserver.modules.bookmark.domain.Bookmark;
import beside.sunday8turtle.pickabookserver.modules.bookmark.service.BookmarkService;
import beside.sunday8turtle.pickabookserver.modules.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDate;
import java.util.List;


@Slf4j
@RequiredArgsConstructor // Lombok에 의한 생성자 자동생성
public class PushNotiTasklet implements Tasklet, StepExecutionListener {

    private final LocalDate currentDate;
    private final NotificationService notificationService;
    private final BookmarkService bookmarkService;

    private List<Bookmark> bookmarks;

    @Override
    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        bookmarks = bookmarkService.getBookmarkByBrowserNoti(currentDate);
        log.info("PushNotiTasklet Before Step Start!");
    }

    @Override
    @AfterStep
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("PushNotiTasklet After Step Start!");
        return ExitStatus.COMPLETED;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.debug("contribution : {}, chunkContext : {}", stepContribution, chunkContext);
        notificationService.bookmarkNotify(bookmarks);
        return RepeatStatus.FINISHED;
    }

}
