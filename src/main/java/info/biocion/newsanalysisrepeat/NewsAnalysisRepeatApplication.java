package info.biocion.newsanalysisrepeat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("info.biocion.newsanalysisrepeat.mapper")
public class NewsAnalysisRepeatApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsAnalysisRepeatApplication.class, args);
    }

}
