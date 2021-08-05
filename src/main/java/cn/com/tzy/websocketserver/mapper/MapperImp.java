package cn.com.tzy.websocketserver.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface MapperImp {
    @Select("select * from user_info where ID=#{ID}")
    public Map<String,Object> getUser(Map<String,String> map);
}
