package info.biocion.newsanalysisrepeat.service.impl;

import info.biocion.newsanalysisrepeat.entity.News;
import info.biocion.newsanalysisrepeat.mapper.NewsMapper;
import info.biocion.newsanalysisrepeat.service.NewsService;
import info.biocion.newsanalysisrepeat.util.RepeatUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NewServiceImpl implements NewsService {
    @Resource
    NewsMapper newsMapper;
    @Override
    public void searchnews() {
        List<News> newsList = newsMapper.select();
        //判断
        newsList.forEach(news->{
            //更改查询和审查状态
            newsMapper.update(news.getId());
            List<News> dateList = newsMapper.selectByDate(news.getDate());
            List<News> repeats=new ArrayList<>();
            dateList.forEach(dl->{
                //判断重复率
                if(RepeatUtil.getSimilarityRatio(news.getContent(),dl.getContent())>0.7){
                    repeats.add(dl);
                }
            });
            repeats.sort((id1,id2)->{
               return id1.getId().intValue()-id2.getId().intValue();
            });
            if(repeats.size()!=0){
                News lastNews=repeats.get(0);
                //为空标1 ;有值不变
                if(null==lastNews.getIsFilter()){
                    newsMapper.updateNews(1,lastNews.getId());
                }
                //（其他数据标记为零）
                repeats.remove(lastNews);
                repeats.forEach(repeat->{
                    newsMapper.updateNews(0,repeat.getId());
                });
            }
            log.info("{},{}",news,repeats);
        });
    }
}
