package com.luxiu.spring.utils.lock.redis;

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
						Thread.sleep(10000);
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
			lock = redisTemplate.opsForValue().setIfAbsent(key, uuid, 30l, TimeUnit.SECONDS);


		}
		// 解决可重入问题
		else if (threadLocal.get().equals(redisTemplate.opsForValue().get(key))) {
			lock = true;
		}

		// 阻塞 占用cpu,性能可能不好
		while (!lock) {
			String uuid = thread.getId() + ":" + UUID.randomUUID().toString();
			threadLocal.set(uuid);
			lock = redisTemplate.opsForValue().setIfAbsent(key, uuid, 30l, TimeUnit.SECONDS);
			if (lock) {
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
		String threadLocalValue = threadLocal.get();
		if (threadLocalValue.equals(redisValue)) {
			redisTemplate.delete(key);
		}
		// 停止异步线程
		if (!StringUtils.isEmpty(redisValue)) {
			String[] split = redisValue.split(":");
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
