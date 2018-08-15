package com.dll.model;

import java.beans.Transient;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable{
	private Integer caid;
	private String name;
	private int count;
}
