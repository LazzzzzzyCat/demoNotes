package com.demo.drug.maper;

import com.demo.drug.domain.DrugPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huwj
 * @date 2020-11-18 10:40
 */
@Mapper
public interface DrugMapper {

    List<DrugPo> queryAll();

    DrugPo queryById(@Param("drugid") String drugid);
}
