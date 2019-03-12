import java.util.*;

public class ProducerConsumer2 {
    Queue<Integer> workingQueue = new LinkedList<Integer>();
    static int QUEUE_LIMIT=10;
    public synchronized void produce(int num) throws InterruptedException {
        while(workingQueue.size()>=QUEUE_LIMIT)
        {
            wait();
        }
        workingQueue.add(num);
        notifyAll();
    }

    public synchronized Integer consume() throws InterruptedException {
        while (workingQueue.isEmpty()) {
            wait();
        }
        notifyAll(); // it's okay to notify before polling the element because the producers will wait for this::lock to be unlock (what's going to happen after the poll) before checking the queue size
        return workingQueue.poll();
    }
}