package com.luxiu.spring.utils.lock.redis;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Description:
 * </p>
 *
 * @author luguangdong
 * @version 1.0.0
 * @ClassName RedisLock
 * @date 2020/8/6 13:51
 * @company https://www.beyond.com/
 */
public interface RedisLock {

	boolean tryLock(String key, long timeout, TimeUnit unit);

	void releaseLock(String key);

}
