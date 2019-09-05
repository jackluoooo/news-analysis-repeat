package info.biocion.newsanalysisrepeat.task;

import info.biocion.newsanalysisrepeat.entity.BlackList;
import info.biocion.newsanalysisrepeat.mapper.BlackListMapper;
import info.biocion.newsanalysisrepeat.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Configuration
@EnableScheduling
@Slf4j
public class DoBlackTask {
    @Resource
    BlackListMapper blackListMapper;

    @Resource
    NewsService newsService;


    @Scheduled(fixedRate = 60*1000)
    public void run() {
        try {
            Map map=new HashMap();
            List<BlackList> bls=blackListMapper.selectByMap(map);
            bls.forEach(blackList -> {
//                log.info("blacklist:{}",blackList);
                newsService.doBlack(blackList.getBlackstr(),blackList.getStatus());
            });

        }catch (Exception e){
            log.error("blackTask error:{}",e);
        }

    }
}
