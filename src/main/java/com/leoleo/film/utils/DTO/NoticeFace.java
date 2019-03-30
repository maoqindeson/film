package com.leoleo.film.utils.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

@Data
public class NoticeFace {
    @JsonFormat(locale = "zh",timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private String notify_time;
    private List<NoticeFaceData> data;
}
