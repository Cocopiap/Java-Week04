import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 方法二：ReentrantLock可重入锁
 * 同Synchronized方法相似
 * */
public class createthread2
{
    private static final ReentrantLock lock =new ReentrantLock(true);

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


    public static void main(String[] args) throws Exception
    {
        long start=System.currentTimeMillis();
        SumThread sumThread = new SumThread();
        sumThread.start();

        TimeUnit.MILLISECONDS.sleep(1);
        lock.lock();
        try
        {
            System.out.println("***");
        }
        finally
        {
            lock.unlock();
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
            lock.lock();
            try
            {
                result = sum();
            }
            finally
            {
                lock.unlock();
            }
        }
    }
}
