package com.leyifu.weiliaodemo.activity;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by hahaha on 2017/6/1 0001.
 */
public class THreadPoolTest extends ThreadPoolExecutor {

    /**
     * @param corePoolSize      该线程池中核心线程数最大值
     * @param maximumPoolSize   该线程池中线程总数最大值
     * @param keepAliveTime     该线程池中非核心线程闲置超时时长
     * @param unit              keepAliveTime 的单位，TimeUnit 是一个枚举类型
     * @param workQueue         该线程池中的任务队列：维护着等待执行的Runnable对象
     * @param threadFactory     创建线程的方式，这是一个接口，你 new 他的时候需要实现他的 Thread
     * @param handler           这玩意儿就是抛出异常专用的
     */
    public THreadPoolTest(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public THreadPoolTest(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public THreadPoolTest(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public THreadPoolTest(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }
}
