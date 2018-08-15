package com.dll.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Option implements Serializable{
   private Integer oid;
	/**
    * 配置名称
 	*/
	private String name;

    /**
     * 配置值
     */
    private String value;

    private String description;
}
