package org.xuyao.tool.thread;

import cn.hutool.core.collection.CollUtil;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * 线程池工具类
 *
 * @author xuyao
 * @since 2021-05-18
 */
public class ThreadPoolUtil extends ThreadPoolExecutor {


    public ThreadPoolUtil(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public ThreadPoolUtil(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public ThreadPoolUtil(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public ThreadPoolUtil(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }


    public Runnable warp(Runnable run) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
        return () -> {
            try {
                if(CollUtil.isNotEmpty(copyOfContextMap)){
                    MDC.setContextMap(copyOfContextMap);
                }
                if(Objects.nonNull(requestAttributes)){
                    RequestContextHolder.setRequestAttributes(requestAttributes);
                }
                run.run();
            } finally {
                MDC.clear();
            }
        };
    }



}
