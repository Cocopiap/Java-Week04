/**
 * 方法七：join方法
 * 让当前线程进行阻塞，
 * 等被执行线程结束之后，再执行其他线程
 * */

public class createthread7
{
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
        sumThread.join();


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
        }
    }
}
