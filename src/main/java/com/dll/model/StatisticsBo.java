package com.dll.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 后台统计对象
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsBo implements Serializable {
    private Integer articles;
    private Integer comments;
    private Integer links;
    
}
