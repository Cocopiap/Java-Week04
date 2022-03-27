
/**
 * 方法三：wait+notify/notifyAll
 * 在启动完计算线程之后
 * 将主线程wait
 * 在计算线程中执行完毕后
 * 调用notify/notifyAll方法唤醒主线程唤醒继续执行
 * */
import java.util.concurrent.TimeUnit;
public class createthread3
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
        synchronized (lock)
        {
            lock.wait();
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
                lock.notifyAll();
            }
        }
    }
}
