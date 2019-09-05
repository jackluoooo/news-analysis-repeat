package info.biocion.newsanalysisrepeat.service;

import info.biocion.newsanalysisrepeat.entity.News;
import info.biocion.newsanalysisrepeat.service.bean.NewsBean;

import java.util.List;

public interface NewsService {
 void searchnews();

 //查询所有帖子
 List<NewsBean> getAllNews(int pageno, int pagesize);

 //精选帖
 List<NewsBean> getRecommendNews(int pageno, int pagesize);

 //栏目
 List<NewsBean> getNewsByClassId(int pageno, int pagesize,int classId);

 //获取
 NewsBean getNewsContent(Long id) throws Exception;

 void insertBlackList(String str,Integer status);

 void doBlack(String str,Integer status);
}
