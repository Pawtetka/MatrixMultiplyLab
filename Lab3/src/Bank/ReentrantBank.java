package Bank;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantBank implements IBank{
    public final ReentrantLock reentrantLock = new ReentrantLock();
    public static final int NTEST = 10000;
    private final int[] accounts;
    private long ntransacts = 0;

    public ReentrantBank(int n, int initialBalance){
        accounts = new int[n];
        int i;
        for (i = 0; i < accounts.length; i++)
            accounts[i] = initialBalance;
        ntransacts = 0;
    }

    public void transfer(int from, int to, int amount)  throws InterruptedException{
        reentrantLock.lock();
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0)
            test();
        reentrantLock.unlock();
    }

    private void test(){
        int sum = 0;
        for (int i = 0; i < accounts.length; i++)
            sum += accounts[i] ;
        System.out.println("Bank: ReentrantBank | Transactions:" + ntransacts + " Sum: " + sum);
    }

    public int size(){
        return accounts.length;
    }
}
