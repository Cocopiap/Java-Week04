/**
 *方法六：CyclicBarrier
 * 主线程启动计算线程之后，执行await
 * 计算线程执行完之后，执行await，这个内存屏障设置为2，则正好解除屏障，继续执行
 *  */
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;



public class createthread6
{
    private static final CyclicBarrier barrier = new CyclicBarrier(2);
    //内存屏障设置为2

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
        barrier.await();


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
            try
            {
                result = sum();
                barrier.await();
            }
            catch (InterruptedException e) {e.printStackTrace();}
            catch (BrokenBarrierException e) {e.printStackTrace();}

        }
    }
}
