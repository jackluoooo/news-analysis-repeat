package info.biocion.newsanalysisrepeat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import info.biocion.newsanalysisrepeat.entity.BlackList;
import info.biocion.newsanalysisrepeat.entity.News;
import info.biocion.newsanalysisrepeat.mapper.BlackListMapper;
import info.biocion.newsanalysisrepeat.mapper.NewsMapper;
import info.biocion.newsanalysisrepeat.service.NewsService;
import info.biocion.newsanalysisrepeat.service.bean.NewsBean;
import info.biocion.newsanalysisrepeat.util.RepeatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class NewServiceImpl implements NewsService {
    @Resource
    NewsMapper newsMapper;

    @Resource
    BlackListMapper blackListMapper;
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
//            log.info("{},{}",news,repeats);
        });
    }

    @Override
    public List<NewsBean> getAllNews(int pageno, int pagesize) {
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
//        Map map=new HashMap();
//        map.put("is_filter",1);
//        map.put("is_black",0);
        queryWrapper.orderByDesc("date");
        queryWrapper.eq("is_black",0).and(wapper->{return wapper.eq("is_filter",1).or().isNull("is_filter");});

        Page<News> newsPage = new Page<>(pageno, pagesize);
        IPage<News> CategoryIPage=newsMapper.selectPage(newsPage,queryWrapper);
        List<News> records = CategoryIPage.getRecords();
        log.info("records:{}",records.get(0));
        List<NewsBean> beans=new ArrayList<>();
        for (News news:records){
            NewsBean newsBean=new NewsBean();
            BeanUtils.copyProperties(news,newsBean);
            beans.add(newsBean);
        }
        return beans;
    }

    @Override
    public List<NewsBean> getRecommendNews(int pageno, int pagesize) {
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();

        queryWrapper.orderByDesc("date");
        queryWrapper.eq("is_black",0).eq("recommend",1).and(wapper->{return wapper.eq("is_filter",1).or().isNull("is_filter");});

        Page<News> newsPage = new Page<>(pageno, pagesize);
        IPage<News> CategoryIPage=newsMapper.selectPage(newsPage,queryWrapper);
        List<News> records = CategoryIPage.getRecords();
        log.info("records:{}",records.get(0));
        List<NewsBean> beans=new ArrayList<>();
        for (News news:records){
            NewsBean newsBean=new NewsBean();
            BeanUtils.copyProperties(news,newsBean);
            beans.add(newsBean);
        }
        return beans;
    }

    @Override
    public List<NewsBean> getNewsByClassId(int pageno, int pagesize, int classId) {
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("date");
        queryWrapper.eq("is_black",0);
        queryWrapper.eq("class_id",classId);
        Page<News> newsPage = new Page<>(pageno, pagesize);
        IPage<News> CategoryIPage=newsMapper.selectPage(newsPage,queryWrapper);
        List<News> records = CategoryIPage.getRecords();
        List<NewsBean> beans=new ArrayList<>();
        for (News news:records){
            NewsBean newsBean=new NewsBean();
            BeanUtils.copyProperties(news,newsBean);
            beans.add(newsBean);
        }
        return beans;
    }

    @Override
    public NewsBean getNewsContent(Long id) throws Exception{
        Map map=new HashMap();
        map.put("Id",id);
        List<News> list = newsMapper.selectByMap(map);
        if(null==list||list.size()==0){
            throw new Exception("1001");
        }
        NewsBean newsBean=new NewsBean();
        BeanUtils.copyProperties(list.get(0),newsBean);
        return newsBean;
    }

    @Override
    public void doBlack(String str,Integer status) {
        if(status==1){
            //拉黑
            QueryWrapper<News> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("is_black",0);
            queryWrapper.like("content",str);
            List<News> newsList = newsMapper.selectList(queryWrapper);
            if(null==newsList||newsList.size()==0){
            return;
        }
            for (News news:newsList){
            log.info("{}",news);
            news.setIsBlack(1);
            newsMapper.updateById(news);
        }
        }else {
            //解除拉黑
            QueryWrapper<News> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("is_black", 1);
            queryWrapper.like("content", str);
            List<News> newsList = newsMapper.selectList(queryWrapper);
            if (null == newsList || newsList.size() == 0) {
                return;
            }
            for (News news : newsList) {
                log.info("{}", news);
                news.setIsBlack(0);
                newsMapper.updateById(news);
            }
        }
    }

    @Override
    public void insertBlackList(String str, Integer status) {
        BlackList blackList=new BlackList();
        blackList.setBlackstr(str);
        blackList.setStatus(status);

        Map map=new HashMap();
        map.put("blackstr",str);
        List<BlackList> bls=blackListMapper.selectByMap(map);
        if(null==bls||bls.size()==0){
            blackListMapper.insert(blackList);
            return;
        }
        blackList.setId(bls.get(0).getId());
        blackListMapper.updateById(blackList);
    }
}
