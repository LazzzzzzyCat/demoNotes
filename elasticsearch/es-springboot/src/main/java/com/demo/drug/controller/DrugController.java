package com.demo.drug.controller;

import com.demo.drug.domain.DrugPo;
import com.demo.drug.maper.DrugMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huwj
 * @date 2020-11-18 10:45
 */
@RestController
@RequestMapping("drug")
public class DrugController {

    @Resource
    private DrugMapper drugMapper;

    @GetMapping("queryAll")
    public List<DrugPo> queryAll(){
        return drugMapper.queryAll();
    }
}
