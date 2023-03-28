package job;

import app.Assets;
import app.Properties;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ScanningJobQueue implements JobQueue{

    private BlockingQueue<Job> jobs;

    public ScanningJobQueue() {
        this.jobs = new ArrayBlockingQueue<>(10);
    }

    @Override
    public void enqueue(Job job) {
        try {
            this.jobs.put(job);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Job dequeue() {
        try {
            return this.jobs.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}