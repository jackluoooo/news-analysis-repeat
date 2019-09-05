package info.biocion.newsanalysisrepeat.controller;

import com.alibaba.fastjson.JSONObject;
import info.biocion.newsanalysisrepeat.app.WebResult;
import info.biocion.newsanalysisrepeat.service.NewsService;
import info.biocion.newsanalysisrepeat.service.bean.NewsBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("news")
@Slf4j
public class NewsController {

    @Resource
    NewsService newsService;
    @GetMapping("all")
    public WebResult getAllNews(@RequestParam(value = "pageno",defaultValue = "1")  Integer pageno,
                                @RequestParam(value = "pagesize",defaultValue = "20")  Integer pagesize){
        try {
            List<NewsBean> allNews = newsService.getAllNews(pageno, pagesize);
            return WebResult.okResult(allNews);
        }catch (Exception e){
            log.error("获取所有信息失败",e);
        }
        return WebResult.failResult(4000);
    }

    @GetMapping("recommend")
    public WebResult getRecommendNews(@RequestParam(value = "pageno",defaultValue = "1")  Integer pageno,
                                      @RequestParam(value = "pagesize",defaultValue = "20")  Integer pagesize){
        try {
            List<NewsBean> recommendNews = newsService.getRecommendNews(pageno, pagesize);
            return WebResult.okResult(recommendNews);
        }catch (Exception e){
            log.error("获取推荐信息失败",e);
        }
        return WebResult.failResult(4000);
    }

    @GetMapping("getNewsByClassId")
    public WebResult getNewsByClassId(@RequestParam(value = "pageno",defaultValue = "1")  Integer pageno,
                                      @RequestParam(value = "pagesize",defaultValue = "20")  Integer pagesize,
                                      @RequestParam(value = "classId")  Integer classId){
        try {
            List<NewsBean> recommendNews = newsService.getNewsByClassId(pageno, pagesize,classId);
            return WebResult.okResult(recommendNews);
        }catch (Exception e){
            log.error("获取推荐信息失败",e);
        }
        return WebResult.failResult(4000);
    }

    @GetMapping("getNewsContent")
    public WebResult getNewsByClassId(@RequestParam(value = "id")  Long id){
        try {
            NewsBean newsContent = newsService.getNewsContent(id);
            return WebResult.okResult(newsContent);
        }catch (Exception e){
            log.error("获取推荐信息失败",e);
            if(e.getMessage().equals("1001")){
                return WebResult.failResult(4000,"不存在该帖子");
            }
        }
        return WebResult.failResult(4000);
    }

    @PostMapping("doBlack")
    public WebResult update(@RequestBody JSONObject jsonObject){
        try {
            String blackStr = jsonObject.getString("blackStr");
            Integer status = jsonObject.getIntValue("status");
            newsService.insertBlackList(blackStr,status);
            return WebResult.okResult();
        }catch (Exception e){
            log.error("拉黑失败",e);
        }
        return WebResult.failResult(4000);
    }

}
