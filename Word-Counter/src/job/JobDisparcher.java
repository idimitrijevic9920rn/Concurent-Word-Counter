package job;

import result.ResultRetriever;
import scanner.file.FileScanner;
import scanner.Scanner;
import scanner.web.WebScanner;

import java.util.HashMap;
import java.util.Map;

public class JobDisparcher implements Runnable{

    private JobQueue jobs;
    private Map<ScanType, Scanner> scanners;


    public JobDisparcher(JobQueue jobs, ResultRetriever resultRetriever) {
        this.jobs = jobs;

        scanners = new HashMap<>();

        scanners.put(ScanType.FILE, new FileScanner(resultRetriever));
        scanners.put(ScanType.WEB, new WebScanner(jobs, resultRetriever));

    }

    @Override
    public void run() {

        while(true) {

            Job job = jobs.dequeue();

            if(job.getScanType() == ScanType.POISON){
                break;
            }

            scanners.get(job.getScanType()).submitTask(job);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


}


