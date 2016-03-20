package org.phoenix.cases.webservice;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import org.phoenix.api.action.APIAction;
import org.phoenix.api.action.WebAPIAction;

import com.meterware.httpunit.WebResponse;
 
/**
 * Executors+CyclicBarrier实现的并发测试小例子<br>
 * 例子实现了并发测试中使用的集合点，集合点超时时间及思考时间等技术
 * @author mengfeiyang
 *
 */
public class FlushGenerator {
    private static volatile boolean RUNFLAG = true;
    private CyclicBarrier rendzvous;
    private static int threads;
    private static AtomicInteger totalCount = new AtomicInteger();
    private static AtomicInteger startedCount = new AtomicInteger();
    private static AtomicInteger finishCount = new AtomicInteger();
    private static AtomicInteger runCount = new AtomicInteger();
    private static AtomicInteger successCount = new AtomicInteger();
    private static AtomicInteger failCount = new AtomicInteger();
    private String url;
    private long rendzvousWaitTime = 0;
    private long thinkTime = 0;
    private int iteration = 0;
     
    /**
     * 初始值设置
     * @param url 被测url
     * @param threads 总线程数
     * @param iteration 每个线程迭代次数
     * @param rendzvousWaitTime 集合点超时时间，如果不启用超时时间，请将此值设置为0.<br>
     *                            如果不启用集合点，请将此值设置为-1<br>
     *                            如果不启用超时时间，则等待所有的线程全部到达后，才会继续往下执行<br>
     * @param thinkTime 思考时间，如果启用思考时间，请将此值设置为0
     */
    public FlushGenerator(String url,int threads,int iteration,long rendzvousWaitTime,long thinkTime){
        totalCount.getAndSet(threads);
        FlushGenerator.threads = threads;
        this.url = url;
        this.iteration = iteration;
        this.rendzvousWaitTime = rendzvousWaitTime;
        this.thinkTime = thinkTime;
    }
 
    public static ThreadCount getThreadCount(){
        return new ThreadCount(threads,runCount.get(),startedCount.get(),finishCount.get(),successCount.get(),failCount.get());
    }
     
    public static boolean isRun(){
        return finishCount.get() != threads;
    }
     
    public synchronized static void stop(){
        RUNFLAG = false;
    }
     
    public void runTask(){
        List<Future<String>> resultList = new ArrayList<Future<String>>();
        ExecutorService exeService = Executors.newFixedThreadPool(threads);
        rendzvous = new CyclicBarrier(threads);//默认加载全部线程
        for(int i=0;i<threads;i++){
            resultList.add(exeService.submit(new TaskThread(i,url,iteration,rendzvousWaitTime,thinkTime)));
        }
        exeService.shutdown();
        for(int j=0;j<resultList.size();j++){
            try{
                System.out.println(resultList.get(j).get());
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        stop();
    }
     
    static class ThreadCount {
        public final int runThreads;
        public final int startedThreads;
        public final int finishedThreads;
        public final int totalThreads;
        public final int successCount;
        public final int failCount;
         
         
        public ThreadCount(int totalThreads,int runThreads, int startedThreads, int finishedThreads,int successCount,int failCount) {
            this.totalThreads = totalThreads;
            this.runThreads = runThreads;
            this.startedThreads = startedThreads;
            this.finishedThreads = finishedThreads;
            this.successCount = successCount;
            this.failCount = failCount;
        }
    }
     
    private class TaskThread implements Callable<String> {
        private String url;
        private long rendzvousWaitTime = 0;
        private long thinkTime = 0;
        private int iteration = 0;
        private int iterCount = 0;
        private int taskId;
         
        /**
         * 任务执行者属性设置
         * @param taskId 任务id号
         * @param url 被测url
         * @param iteration 迭代次数，如果一直执行则需将此值设置为0
         * @param rendzvousWaitTime 集合点超时时间，如果不需要设置时间，则将此值设置为0。如果不需要设置集合点，则将此值设置为-1
         * @param thinkTime 思考时间，如果不需要设置思考时间，则将此值设置为0
         */
        public TaskThread(int taskId,String url,int iteration, long rendzvousWaitTime,long thinkTime){
            this.taskId = taskId;
            this.url = url;
            this.rendzvousWaitTime = rendzvousWaitTime;
            this.thinkTime = thinkTime;
            this.iteration = iteration;
        }
        @Override
        public String call() throws Exception{
            startedCount.getAndIncrement();
            runCount.getAndIncrement();
            APIAction webAPI = new WebAPIAction();
            while(RUNFLAG && iterCount<iteration){
                if(iteration != 0)iterCount++;
                try{
                        if(rendzvousWaitTime > 0){
                            try{
                                System.out.println("任务：task-"+taskId+" 已到达集合点...等待其他线程,集合点等待超时时间为："+rendzvousWaitTime);
                                rendzvous.await(rendzvousWaitTime,TimeUnit.MICROSECONDS);
                            } catch (InterruptedException e) {
                            } catch (BrokenBarrierException e) {
                                System.out.println("task-"+taskId+" 等待时间已超过集合点超时时间："+rendzvousWaitTime+" ms,将开始执行任务....");
                            } catch (TimeoutException e) {
                            }
                        } else if(rendzvousWaitTime == 0){
                            try{
                                System.out.println("任务：task-"+taskId+" 已到达集合点...等待其他线程");
                                rendzvous.await();
                            } catch (InterruptedException e) {
                            } catch (BrokenBarrierException e) {
                            }
                        }
                        //需要定制的部分
                    WebResponse wr = webAPI.getResponseByGet(url);
                    //WebResponse wr = new WebConversation().getResponse(new GetMethodWebRequest(url));
                    System.out.println("线程：task-"+taskId+" 获取到的资源大小："+wr.getText().length()+",状态码："+wr.getResponseCode());
                    successCount.getAndIncrement();
                    if(thinkTime !=0){
                        System.out.println("task-"+taskId+" 距下次启动时间："+thinkTime);
                        Thread.sleep(thinkTime);
                    }
                }catch(Exception e){
                    failCount.getAndIncrement();
                }
            }
            finishCount.getAndIncrement();
            runCount.decrementAndGet();
            return Thread.currentThread().getName()+" 执行完成！";
        }
    }
     
    public static void main(String[] args) {
        new Thread(){
            public void run(){
                new FlushGenerator("http://10.108.79.8:8080/hh.php",5,3,0,200).runTask();
            }
        }.start();
         
        
        //监控执行过程
        new Thread(){
            public void run(){
                while(true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("isRun:"+FlushGenerator.isRun());
                    System.out.println("totalThreads:"+FlushGenerator.getThreadCount().totalThreads);
                    System.out.println("startedThreads:"+FlushGenerator.getThreadCount().startedThreads);
                    System.out.println("runThreads:"+FlushGenerator.getThreadCount().runThreads);
                    System.out.println("finishedThread:"+FlushGenerator.getThreadCount().finishedThreads);
                    System.out.println("successCount:"+FlushGenerator.getThreadCount().successCount);
                    System.out.println("failCount:"+FlushGenerator.getThreadCount().failCount);
                    System.out.println();
                }
            }
        }.start();
    }
}