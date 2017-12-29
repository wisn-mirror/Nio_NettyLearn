package nettyw;

import nettyw.pool.NioSelectorRunnablePool;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public class AbstractNioSelector implements Runnable {
    private Executor executor;
    private String threadName;
    private NioSelectorRunnablePool nioSelectorRunnablePool;
    private Selector selector;
    private AtomicBoolean weakup=new AtomicBoolean();
    private Queue<Runnable> taskQueue=new ConcurrentLinkedQueue<>();
    public AbstractNioSelector(Executor executor, String threadName, NioSelectorRunnablePool nioSelectorRunnablePool) {
        this.executor = executor;
        this.threadName = threadName;
        this.nioSelectorRunnablePool = nioSelectorRunnablePool;
        openSelector();
    }

    private void openSelector() {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        executor.execute(this);
    }

    public void registerTask(Runnable task){
        taskQueue.add(task);
        Selector selector=this.selector;
        if(selector!=null){
            if(weakup.compareAndSet(false,true)){
                selector.wakeup();
            }
        }else{
            taskQueue.remove(task);
        }
    }

    @Override
    public void run() {
        Thread.currentThread().setName(threadName);
        while(true){
            weakup.set(false);
            selector(selector);
            processTaskQueue();
            process(selector);
        }

    }



    private void processTaskQueue() {
        for(;;){
            Runnable poll = taskQueue.poll();
            if(poll!=null){
                break;
            }
            poll.run();
        }
    }

    private void selector(Selector selector) {

    }
    private void process(Selector selector) {

    }
}
