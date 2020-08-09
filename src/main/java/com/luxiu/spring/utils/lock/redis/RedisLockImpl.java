package com.luxiu.spring.utils.lock.redis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName RedisLockImpl
 * @date 2020/8/6 13:53
 * @company https://www.beyond.com/
 */
@Component
public class RedisLockImpl implements RedisLock {
	public static final Logger logger = LoggerFactory.getLogger(RedisLockImpl.class);

	@Autowired
	private RedisTemplate redisTemplate;

	// 线程安全,线程复用
	private ThreadLocal<String> threadLocal = new ThreadLocal<String>();

	/**
	 * 如果此处用一个 private String ss = "" ; 来承接生产的uuid 通常 RedisLockImpl 是单例的
	 * 如果有多个人调用这个类,那么多个人调用就会出现线程不安全,每个人都可以拿到这个变量 为了保证线程安全,我将uuid放入到 threadLocal
	 * 中,这样可以保证每个线程只会操作自己在当前线程中设置的变量
	 */

	// 娶一个老婆
	public boolean tryLock(final String key, final long timeout, final TimeUnit unit) {
		Boolean lock = false;

		// 异步续命操作,实现高可用
		Thread thread = new Thread() {
			@Override
			public void run() {
				// 自旋
				while (true) {
					try {
						Thread.sleep(100);
					}
					catch (InterruptedException e) {
						// e.printStackTrace();
						// 当线程执行sleep(1000)之后会被立即阻塞，如果在阻塞时外面调用interrupt来中断这个线程，那么就会执行e.printStackTrace();
						// 这个时候其实线程并未中断，执行完这条语句之后线程会继续执行while循环，开始sleep，所以说如果没有对InterruptedException进行处理，后果就是线程可能无法中断
						// 所以，在任何时候碰到InterruptedException，都要手动把自己这个线程中断,this.interrupt();
						this.interrupt();
					}
					redisTemplate.expire(key, timeout, unit);
				}
			}
		};

		if (threadLocal.get() == null) {
			String uuid = thread.getId() + ":" + UUID.randomUUID().toString();
			threadLocal.set(uuid);
			logger.info("上锁时: threadLocal中设置的key = {}, value = {}",thread.getId(),uuid);
			lock = redisTemplate.opsForValue().setIfAbsent(key, uuid, 30l, TimeUnit.SECONDS);
			if(lock){
				logger.info("上锁时: 第一次拿到锁的线程id = {}",thread.getId());
			}
			logger.info("上锁时: redis中设置的key = {}, value = {}",key,uuid);

		}
		// 解决可重入问题
		else if (threadLocal.get().equals(redisTemplate.opsForValue().get(key))) {
			lock = true;
		}

		// 假如现在同一个product_id商品有66件库存,采用秒杀方案，此时有100个请求过来抢同一件商品,那么只有第一个人会拿到锁,其他99个人没有拿到锁，
		// 没有拿到锁的用户此时如果直接返回的话,就会存在商品卖不完的问题。解决此问题我们要做阻塞异步操作。
		// 如果商品量足够大的话,并发量不高的话，就没有必要加阻塞异步操作了。这里具体场景具体对待。
		// 问题: 阻塞 占用cpu,性能可能不好
		while (!lock) {
			logger.info("上锁时: 没有拿到锁的线程 id = {}",thread.getId());
			String uuid = thread.getId() + ":" + UUID.randomUUID().toString();
			threadLocal.set(uuid);
			lock = redisTemplate.opsForValue().setIfAbsent(key, uuid, 30l, TimeUnit.SECONDS);
			if (lock) {
				logger.info("上锁时: 第N次拿到锁的线程 id = {}",thread.getId());
				break;
			}

		}

		// 注意放置位置,只用拿到锁的线程才会启动异步线程续命
		thread.start();

		return lock;
	}

	// 给个唯一标识,防止自己的老婆被别人干
	public void releaseLock(String key) {
		String redisValue = (String) redisTemplate.opsForValue().get(key);
		logger.info("释放锁时: redis中设置的key = {}, value = {}",key,redisValue);
		String threadLocalValue = threadLocal.get();
		logger.info("释放锁时: threadLocal中设置的value ={}",threadLocalValue);
		if (threadLocalValue.equals(redisValue)) {
			redisTemplate.delete(key);
		}
		// 停止异步线程
		if (!StringUtils.isEmpty(threadLocalValue)) {
			String[] split = threadLocalValue.split(":");
			if (split.length > 0) {
				Long threadId = Long.valueOf(split[0]);
				// 停止异步线程
				if (findThread(threadId) != null) {
					this.findThread(threadId).interrupt();
				}
			}
		}
	}

	/**
	 * 根据线程id拿到线程
	 */
	public static Thread findThread(long threadId) {
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		while (group != null) {
			Thread[] threads = new Thread[(int) (group.activeCount() * 1.2)];
			int count = group.enumerate(threads, true);
			for (int i = 0; i < count; i++) {
				if (threadId == threads[i].getId()) {
					return threads[i];
				}
			}
			group = group.getParent();
		}
		return null;
	}

}
