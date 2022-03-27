/**
 * 方法五：CountDownLatch
 * 当主线程启动之后，让CountDownLatch执行await方法
 * 计算线程执行完毕之后，执行countdown方法
 * 然后继续执行主程序
 * */
import java.util.concurrent.CountDownLatch;



public class createthread5
{
    private static final CountDownLatch latch = new CountDownLatch(1);
    //需要倒计时的线程为1

    private static int sum()
    {
        return fibo(36);
    }

    private static int fibo(int a)
    {
        if (a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

    public  static  void main(String[] args) throws Exception
    {
        long start = System.currentTimeMillis();
        SumThread sumThread = new SumThread();

        sumThread.start();
        latch.await();


        int result = sumThread.getResult();
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间为："+(System.currentTimeMillis()-start) +"ms");

    }

    static class SumThread extends Thread
    {
        private Integer result;

        public Integer getResult()
        {
            return result;
        }

        @Override
        public void run()
        {
            result = sum();
            latch.countDown();
        }
    }

}
