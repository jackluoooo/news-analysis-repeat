package info.biocion.newsanalysisrepeat.task;

import info.biocion.newsanalysisrepeat.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Configuration
@EnableScheduling
@Slf4j
public class FilterTask {

    @Resource
    NewsService newsService;

    @Scheduled(fixedRate = 60*1000)
    public void run() {
        try {
            newsService.searchnews();
        }catch (Exception e){
            log.error("newsService error:{}",e);
        }

    }

}
