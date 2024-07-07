package com.tenutz.storemngsim.web.api.dto.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
public class CommonCondition {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateTo;

    private String query;
    private String queryType;
}