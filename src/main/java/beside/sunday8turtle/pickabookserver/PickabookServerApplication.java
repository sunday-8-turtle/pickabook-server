package beside.sunday8turtle.pickabookserver;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@SpringBootApplication
@EnableBatchProcessing // Spring Batch 을 유효화
@EnableScheduling //Spring Schedule 을 유효화
@EnableJpaAuditing//jpaAuddit 적용
public class PickabookServerApplication extends DefaultBatchConfigurer {

	@Override
	public void setDataSource(DataSource dataSource) {
		// 여기를 비워놓는다
	}


	public static void main(String[] args) {
		SpringApplication.run(PickabookServerApplication.class, args);
	}
}
