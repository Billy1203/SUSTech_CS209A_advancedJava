package ProducerCosumer1;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 公共常量
 * @author 骆昊
 *
 */
class Constants2 {
    public static final int MAX_BUFFER_SIZE = 10;
    public static final int NUM_OF_PRODUCER = 2;
    public static final int NUM_OF_CONSUMER = 3;
}

/**
 * 工作任务
 * @author 骆昊
 *
 */
class Task2 {
    private int id = 0;  // 任务的编号
    
    public Task2() {
        id = id++;
    }

    @Override
    public String toString() {
        return "Task[" + id + "]";
    }
}

/**
 * 消费者
 * @author 骆昊
 *
 */
class Consumer2 implements Runnable {
    private BlockingQueue<Task> buffer;

    public Consumer2(BlockingQueue<Task> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Task task = buffer.take();
                System.out.println("Consumer[" + Thread.currentThread().getName() + "] got " + task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * 生产者
 * @author 骆昊
 *
 */
class Producer2 implements Runnable {
    private BlockingQueue<Task> buffer;

    public Producer2(BlockingQueue<Task> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Task task = new Task();
                buffer.put(task);
                System.out.println("Producer[" + Thread.currentThread().getName() + "] put " + task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}

public class Test2 {

    public static void main(String[] args) {
        BlockingQueue<Task> buffer = new LinkedBlockingQueue<>(Constants.MAX_BUFFER_SIZE);
        ExecutorService es = Executors.newFixedThreadPool(Constants.NUM_OF_CONSUMER + Constants.NUM_OF_PRODUCER);
        for(int i = 1; i <= Constants.NUM_OF_PRODUCER; ++i) {
            es.execute(new Producer2(buffer));
        }
        for(int i = 1; i <= Constants.NUM_OF_CONSUMER; ++i) {
            es.execute(new Consumer2(buffer));
        }
    }
}
