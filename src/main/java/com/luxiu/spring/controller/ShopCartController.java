package com.luxiu.spring.controller;

import com.luxiu.spring.conf.response.ResponseResult;
import com.luxiu.spring.utils.lock.redis.RedisLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName ShopCartController
 * @date 2020/8/5 20:31
 * @company https://www.beyond.com/
 */
@RestController
@RequestMapping("/cart")
public class ShopCartController {

	@Autowired
	private RedisTemplate redisTemplate;

	protected static final String product = "123456789";

	/**
	 * 并发会产生超卖
	 * @return
	 */

	@RequestMapping(value = "/submitOrder", method = RequestMethod.GET)
	public ResponseResult submitOrder() {
		int stock = Integer.parseInt((String) redisTemplate.opsForValue().get("stock"));
		if (stock > 0) {
			// 下单
			stock = stock - 1;
			redisTemplate.opsForValue().set("stock", stock + "");
			System.out.println("扣减成功，库存stock：" + stock);
		}
		else {
			// 下单失败
			System.out.println("扣减失败，库存不足");
			return ResponseResult.success("扣减失败，库存不足");
		}

		return ResponseResult.success("扣减成功，库存stock：" + stock);
	}

	/**
	 * 使用同步锁synchronized,解决同一JVM并发超卖
	 * @return
	 */

	@RequestMapping(value = "/submitOrder2", method = RequestMethod.GET)
	public ResponseResult submitOrder2() {
		int stock = 0;
		synchronized (product) {
			stock = Integer.parseInt((String) redisTemplate.opsForValue().get("stock"));
			if (stock > 0) {
				// 下单
				stock = stock - 1;
				redisTemplate.opsForValue().set("stock", stock + "");
				System.out.println("扣减成功，库存stock：" + stock);
			}
			else {
				// 下单失败
				System.out.println("扣减失败，库存不足");
				return ResponseResult.success("扣减失败，库存不足");
			}
		}

		return ResponseResult.success("扣减成功，库存stock：" + stock);
	}

	/**
	 * 使用分布式锁,解决不同JVM并发超卖
	 * @return
	 */
	@RequestMapping(value = "/submitOrder3", method = RequestMethod.GET)
	public ResponseResult submitOrder3() {
		// 相当于 setnx
		Boolean lock = redisTemplate.opsForValue().setIfAbsent(product, "ant");
		if (!lock) {
			System.out.println("扣减失败，当前未获取分布式锁");
			return ResponseResult.success("扣减失败，当前未获取分布式锁");
		}
		int stock = Integer.parseInt((String) redisTemplate.opsForValue().get("stock"));
		if (stock > 0) {
			// 下单
			/**
			 * 此处出现Exception,以下代码会不执行,其中 redisTemplate.delete(product); 也不执行
			 * 此时会出现死锁,别人会一直拿不到锁
			 *
			 * 解决此问题 一定要 try cathch
			 */
			stock = stock - 1;
			redisTemplate.opsForValue().set("stock", stock + "");
			System.out.println("扣减成功，库存stock：" + stock);
		}
		else {
			// 下单失败
			System.out.println("扣减失败，库存不足");
			return ResponseResult.success("扣减失败，库存不足");
		}
		// 删除是为了防止死锁
		redisTemplate.delete(product);

		return ResponseResult.success("扣减成功，库存stock：" + stock);
	}

	/**
	 * 使用分布式锁,解决不同JVM并发超卖,解决异常下出现死锁
	 * @return
	 */
	@RequestMapping(value = "/submitOrder4", method = RequestMethod.GET)
	public ResponseResult submitOrder4() {
		// 相当于 setnx
		Boolean lock = redisTemplate.opsForValue().setIfAbsent(product, "ant");
		if (!lock) {
			System.out.println("扣减失败，当前未获取分布式锁");
			return ResponseResult.success("扣减失败，当前未获取分布式锁");
		}
		int stock = 0;
		try {
			stock = Integer.parseInt((String) redisTemplate.opsForValue().get("stock"));
			if (stock > 0) {
				// 下单
				/**
				 * 代码执行到此处,突然停电或者服务器宕机,以下代码会不执行,其中 redisTemplate.delete(product); 也不执行
				 * 此时会出现死锁,别人会一直拿不到锁
				 *
				 * 解决此问题则设置 redis中 key的过期时间
				 */
				stock = stock - 1;
				redisTemplate.opsForValue().set("stock", stock + "");
				System.out.println("扣减成功，库存stock：" + stock);
			}
			else {
				// 下单失败
				System.out.println("扣减失败，库存不足");
				return ResponseResult.success("扣减失败，库存不足");
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseResult.failure("扣减异常，异常信息：" + e);
		}
		finally {
			// 删除是为了防止死锁
			redisTemplate.delete(product);
		}

		return ResponseResult.success("扣减成功，库存stock：" + stock);
	}

	/**
	 * 使用分布式锁,解决不同JVM并发超卖,解决异常下出现死锁
	 * @return
	 */
	@RequestMapping(value = "/submitOrder5", method = RequestMethod.GET)
	public ResponseResult submitOrder5() {
		/**
		 * Boolean lock = redisTemplate.opsForValue().setIfAbsent(product, "ant");
		 * //此处停电,那么也会出现死锁 redisTemplate.expire(product,30, TimeUnit.SECONDS);
		 *
		 * 这样设置过期时间,不能保证redis的原子性,因为他是两次操作,可能会出现超卖
		 */

		// 相当于 setnx
		Boolean lock = redisTemplate.opsForValue().setIfAbsent(product, "ant", 30l, TimeUnit.SECONDS);

		// 加入库存现在有100件商品,采用秒杀方案
		// 此时恰好有100个请求过来,那么只有第一个人会拿到锁,其他99个人没有拿到锁
		// 此时如果直接返回的话,就会存在商品卖不完的问题
		// 所以此处我们要做阻塞异步操作
		// 如果此处有10000件商品的话,就没有必要加阻塞异步操作了
		if (!lock) {
			System.out.println("扣减失败，当前未获取分布式锁");
			return ResponseResult.success("扣减失败，当前未获取分布式锁");
		}
		int stock = 0;
		try {
			stock = Integer.parseInt((String) redisTemplate.opsForValue().get("stock"));
			if (stock > 0) {
				// 下单
				/**
				 * 代码执行到此处,突然停电或者服务器宕机,以下代码会不执行,其中 redisTemplate.delete(product); 也不执行
				 * 此时会出现死锁,别人会一直拿不到锁
				 *
				 * 解决此问题则设置 redis中 key的过期时间
				 */

				/**
				 * 此处如果有一个方法调用链路 调用A类a方法 a方法中调用B类b方法 b方法中调用C类c方法 如果在c方法中也执行 Boolean lock =
				 * redisTemplate.opsForValue().setIfAbsent(product, "ant"); 是相同的Key
				 * ,那么就会返回false, c方法逻辑就不会执行 所以此处一定要加可重入锁
				 */

				/**
				 * 如果在redis中设置key的超时时间是30s,如果这段业务逻辑代码执行了30秒还没有处理减库存操作
				 * 那么它会释放锁,但是库存没有减,此处也是不合理的
				 */
				stock = stock - 1;
				redisTemplate.opsForValue().set("stock", stock + "");
				System.out.println("扣减成功，库存stock：" + stock);
			}
			else {
				// 下单失败
				System.out.println("扣减失败，库存不足");
				return ResponseResult.success("扣减失败，库存不足");
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseResult.failure("扣减异常，异常信息：" + e);
		}
		finally {
			// 删除是为了防止死锁
			redisTemplate.delete(product);
		}

		return ResponseResult.success("扣减成功，库存stock：" + stock);
	}

	@Autowired
	private RedisLock redisLock;

	/**
	 * 使用分布式锁,解决不同JVM并发超卖
	 * @return
	 */
	@RequestMapping(value = "/submitOrder6", method = RequestMethod.GET)
	public ResponseResult submitOrder6() {
		int stock = 0;
		try {
			boolean lock = redisLock.tryLock(product, 30, TimeUnit.SECONDS);
			if (lock) {
				stock = Integer.parseInt((String) redisTemplate.opsForValue().get("stock"));
				if (stock > 0) {
					stock = stock - 1;
					redisTemplate.opsForValue().set("stock", stock + "");
					System.out.println("扣减成功，库存stock：" + stock);
				}
				else {
					// 下单失败
					System.out.println("扣减失败，库存不足");
					redisLock.releaseLock(product);
					return ResponseResult.success("扣减失败，库存不足");
				}
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			redisLock.releaseLock(product);
			return ResponseResult.failure("扣减异常，异常信息：" + e);
		}
		redisLock.releaseLock(product);
		return ResponseResult.success("扣减成功，库存stock：" + stock);
	}

	@Autowired
	private RedissonClient redisson;

	@RequestMapping(value = "/submitOrder7", method = RequestMethod.GET)
	public ResponseResult submitOrder7() {
		int stock = 0;
		RLock lock = null;
		try {
			lock = redisson.getLock(product);
			lock.lock(30, TimeUnit.SECONDS);
			stock = Integer.parseInt((String) redisTemplate.opsForValue().get("stock"));
			if (stock > 0) {
				stock = stock - 1;
				redisTemplate.opsForValue().set("stock", stock + "");
				System.out.println("扣减成功，库存stock：" + stock);
			}
			else {
				// 下单失败
				System.out.println("扣减失败，库存不足");
				redisLock.releaseLock(product);
				return ResponseResult.success("扣减失败，库存不足");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			lock.unlock();
			return ResponseResult.failure("扣减异常，异常信息：" + e);
		}
		lock.unlock();
		return ResponseResult.success("扣减成功，库存stock：" + stock);
	}

}
