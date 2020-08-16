package com.luxiu.spring.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName User
 * @date 2020/8/15 0:30
 * @company https://www.beyond.com/
 */
@Data
public class User implements Serializable {

	private static final long serialVersionUID = 4850477405933780205L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 登录名
	 */
	@NotBlank(message = "账号为必填项")
	private String username;

	/**
	 * 密码
	 */
	@NotBlank(message = "密码为必填项")
	@Length(min = 6, max = 20, message = "密码长度在 6 - 20 位字符之间")
	private String password;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 邮箱
	 */
	@NotBlank(message = "邮箱地址为必填项")
	@Email(message = "请输入正确的邮箱地址")
	private String email;

	/**
	 * 网址
	 */
	private String url;

	/**
	 * 用户状态：1(已启用) 0(已禁用)
	 */
	private Long status;

	/**
	 * 激活码
	 */
	private String activationKey;

	/**
	 * 逻辑删除：1(已删除) 0(未删除)
	 */
	private Long isDeleted;

	/**
	 * 创建时间
	 */
	// @NotBlank(message = "创建时间必填项")
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date updateTime;

}