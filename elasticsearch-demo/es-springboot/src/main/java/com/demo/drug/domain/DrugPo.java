package com.demo.drug.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author huwj
 * @date 2020-11-18 11:18
 */
@Data
public class DrugPo {

    private String drugid;

    private String drugname;

    private String artno;

    private String barcode;

    private String gencpycode;

    private String manuftidname;

    private float taxrto;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date last_update;

    private String store_ip;
}
