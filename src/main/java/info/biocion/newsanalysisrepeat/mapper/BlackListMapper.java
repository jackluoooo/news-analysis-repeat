package info.biocion.newsanalysisrepeat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import info.biocion.newsanalysisrepeat.entity.BlackList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface BlackListMapper extends BaseMapper<BlackList> {

    @Insert("insert into `black_list` values ( #{entity.id}, #{entity.blackstr},#{entity.status})")
    int insert(@Param("entity") BlackList entity);
}
