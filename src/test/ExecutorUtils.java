package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorUtils {
    public static ExecutorService pool = Executors.newCachedThreadPool();

    public static void execute(Runnable runnable){
        pool.execute(runnable);
    }
}
