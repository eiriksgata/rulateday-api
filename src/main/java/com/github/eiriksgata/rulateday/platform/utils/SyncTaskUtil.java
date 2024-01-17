package com.github.eiriksgata.rulateday.platform.utils;

import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseExceptionEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class SyncTaskUtil {

    public static long TIMEOUT = 5000L;

    public static Map<String, String> taskList = new HashMap<>();


    public static String SynchronizationTask(String taskId) {
        taskList.put(taskId, "null");
        FutureTask<String> future = new FutureTask<>(new Callable<>() {
            @Override
            public String call() throws Exception {
                while (true) {
                    if (!taskList.get(taskId).equals("null")) {
                        return taskList.get(taskId);
                    }
                }
            }
        });
        ThreadPool.executorService.execute(future);
        try {
            String result = future.get(5, TimeUnit.SECONDS);
            taskList.remove(taskId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            future.cancel(true);
            throw new CommonBaseException(CommonBaseExceptionEnum.METHOD_ARGUMENT_NOT_VALID);
        }
    }


}
