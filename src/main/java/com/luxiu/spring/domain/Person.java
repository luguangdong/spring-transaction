package com.luxiu.spring.domain;

import lombok.Data;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName Person
 * @date 2020/7/31 18:26
 * @company https://www.beyond.com/
 */
@Data
public class Person {

	private Long pid;

	private String pname;

	private String gender;

	// 当前页码从1开始
	private Integer pageNumber;

	// 每页条数
	private Integer pageSize;

}