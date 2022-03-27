/**
* 在示例方法中，就是在main线程中，启动一个线程或线程池，执行斐波那契数列求和计算，计算完毕之后，将计算结果返回到主线程。
* 实际上是main线程和计算线程的通讯问题
* 主线程在启动计算线程之后，需要进入等待或阻塞状态，
* 直到等待的变量值发生改变，或者被阻塞的任务完成后，才能运行获取结果的方法，拿到计算结果
* */


import java.util.concurrent.TimeUnit;

/**
 *方法一：synchronized锁
 * 让计算线程先拿到锁，
 * 之后main线程被synchronized阻塞，直到计算线程执行完毕
 * */
public class createthread1
{
    private static final Object lock = new Object();

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
        long start=System.currentTimeMillis();
        SumThread sumThread = new SumThread();
        sumThread.start();

        TimeUnit.MILLISECONDS.sleep(1);
        synchronized (lock)
        {

        }

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
            synchronized (lock)
            {
                result = sum();
            }
        }
    }
}
