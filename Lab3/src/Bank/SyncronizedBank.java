package Bank;

public class SyncronizedBank implements IBank{
    public static final int NTEST = 10000;
    private final int[] accounts;
    private long ntransacts = 0;

    public SyncronizedBank(int n, int initialBalance){
        accounts = new int[n];
        int i;
        for (i = 0; i < accounts.length; i++)
            accounts[i] = initialBalance;
        ntransacts = 0;
    }

    public synchronized void transfer(int from, int to, int amount)  throws InterruptedException{
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0)
            test();
    }

    private void test(){
        int sum = 0;
        for (int i = 0; i < accounts.length; i++)
            sum += accounts[i] ;
        System.out.println("Bank: SynchronizedBank | Transactions:" + ntransacts + " Sum: " + sum);
    }

    public int size(){
        return accounts.length;
    }
}
