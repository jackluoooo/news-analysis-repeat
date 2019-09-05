package info.biocion.newsanalysisrepeat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import info.biocion.newsanalysisrepeat.entity.News;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface NewsMapper extends BaseMapper<News> {
    @Select("select Id as id, content,`date`,is_search as isSearch ,is_need_check as isNeedCheck,is_filter as isFilter  from news where is_search=0 and is_need_check=0 order by id desc limit 100")
    List<News> select();

    @Select("select Id as id, content,`date`,is_search as isSearch ,is_need_check as isNeedCheck,is_filter as isFilter from news where UNIX_TIMESTAMP(#{date}) -UNIX_TIMESTAMP(`date`)>=14400 order by `date` desc ")
    List<News> selectByDate(@Param("date") Date date);

    @Update("update news set is_filter=#{is_filter} where id=#{id}")
    int updateNews(@Param("is_filter") Integer isFilter,@Param("id") Long id);

    @Update("update news set is_search=1,is_need_check=1 where id=#{id}")
    int update(@Param("id") Long id);

}
