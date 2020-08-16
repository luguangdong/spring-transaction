package com.luxiu.spring.controller.datasourcetwo;

import com.luxiu.spring.conf.page.Pagination;
import com.luxiu.spring.conf.response.ResponseCode;
import com.luxiu.spring.conf.response.ResponseResult;
import com.luxiu.spring.domain.User;
import com.luxiu.spring.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName UserController
 * @date 2020/8/15 0:31
 * @company https://www.beyond.com/
 */
@RestController
@RequestMapping("core/user")
public class UserController {

	@Resource
	private UserService userService;

	@Resource
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * 新增
	 * @param user {@link User}
	 * @return {@link ResponseResult}
	 */
	@PostMapping("create")
	public ResponseResult create(@Validated @RequestBody User user, BindingResult bindingResult) {
		// 表单验证
		if (bindingResult.hasErrors()) {
			return ResponseResult.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
		}

		// 业务逻辑
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		boolean created = userService.create(user);
		if (created) {
			return ResponseResult.success("创建成功");
		}

		return ResponseResult.failure(ResponseCode.INTERFACE_ADDRESS_INVALID);
	}

	/**
	 * 删除
	 * @param userId {@code Long}
	 * @return {@link ResponseResult}
	 */
	@DeleteMapping("remove/{userId}")
	public ResponseResult remove(@PathVariable Long userId) {
		// 业务逻辑
		boolean deleted = userService.remove(userId);
		if (deleted) {
			return ResponseResult.success("删除成功");
		}

		return ResponseResult.failure(ResponseCode.INTERFACE_ADDRESS_INVALID);
	}

	/**
	 * 修改
	 * @param user {@link User}
	 * @return {@link ResponseResult}
	 */
	@PutMapping("update")
	public ResponseResult update(@RequestBody User user) {
		// 业务逻辑
		boolean updated = userService.update(user);
		if (updated) {
			return ResponseResult.success("编辑成功");
		}

		return ResponseResult.failure(ResponseCode.INTERFACE_ADDRESS_INVALID);
	}

	/**
	 * 获取
	 * @param userId {@code Long}
	 * @return {@link ResponseResult}
	 */
	@GetMapping("get/{userId}")
	public ResponseResult get(@PathVariable Long userId) {
		User user = userService.get(userId);
		return ResponseResult.success(user);
	}

	/**
	 * 分页
	 * @param current {@code int} 页码
	 * @param size {@code int} 笔数
	 * @return {@link ResponseResult}
	 */
	@PostMapping("page")
	public ResponseResult page(@RequestParam int current, @RequestParam int size, @RequestBody User user) {
		Pagination<User> page = userService.page(current, size, user);
		return ResponseResult.success(page);
	}

}
