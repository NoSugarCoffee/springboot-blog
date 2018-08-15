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
public class Comment implements Serializable{
	private Integer coid;
	private Integer cid;
	private String author;
	private Long created;
	private String mail;
	private String status;
	private String url;
	private String content;
}
