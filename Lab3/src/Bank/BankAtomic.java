package Bank;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;

public class BankAtomic implements IBank{
    public static final int NTEST = 10000;
    private final AtomicIntegerArray accounts;
    private AtomicLong ntransacts = new AtomicLong(0);

    public BankAtomic(int n, int initialBalance){
        accounts = new AtomicIntegerArray(new int[n]);
        int i;
        for (i = 0; i < accounts.length(); i++)
            accounts.set(i, initialBalance);
        ntransacts.set(0);
    }

    public void transfer(int from, int to, int amount) throws InterruptedException{
        accounts.addAndGet(from, -amount);
        accounts.addAndGet(to, amount);
        ntransacts.incrementAndGet();
        if (ntransacts.get() % NTEST == 0)
            test();
    }

    private void test(){
        AtomicInteger sum = new AtomicInteger(0);
        for (int i = 0; i < accounts.length(); i++)
            sum.addAndGet(accounts.get(i));
        System.out.println("Bank: AtomicBank | Transactions:" + ntransacts + " Sum: " + sum);
    }

    public int size(){
        return accounts.length();
    }
}
