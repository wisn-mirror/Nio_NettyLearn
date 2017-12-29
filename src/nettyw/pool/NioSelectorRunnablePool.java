package nettyw.pool;

import nettyw.NioServerBoss;
import nettyw.NioServerWorker;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

public class NioSelectorRunnablePool {
    private AtomicInteger bossInteger = new AtomicInteger();
    private Boss[] bosses;
    private AtomicInteger workInteger = new AtomicInteger();
    private Worker[] workers;

    public NioSelectorRunnablePool(Executor bossExecutor, Executor workExecutor) {
        initBoss(bossExecutor, 1);
        initWorke(workExecutor, Runtime.getRuntime().availableProcessors() * 2);
    }


    private void initBoss(Executor bossExecutor, int count) {
        bosses = new NioServerBoss[count];
        for (int i = 0; i <count; i++) {
            bosses[i] = new NioServerBoss(bossExecutor, "boss" + i, this);
        }
    }

    private void initWorke(Executor workExecutor, int count) {
        workers = new NioServerWorker[count];
        for (int i = 0; i < count; i++) {
            workers[i] = new NioServerWorker(workExecutor, "worker" + i, this);
        }
    }

    public Boss nextBoss() {
        return bosses[Math.abs(bossInteger.incrementAndGet() % bosses.length)];
    }

    public Worker nextWorker() {
        return workers[Math.abs(workInteger.incrementAndGet() % workers.length)];
    }

}
