package Bank;

import java.io.IOException;

public class BankMain {
    public static final int NACCOUNTS = 10;
    public static final int INITIAL_BALANCE = 10000;

    public static void main(String[] args){
        IBank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
        run(b);

        IBank b2 = new SyncronizedBank(NACCOUNTS, INITIAL_BALANCE);
        run(b2);

        IBank b3 = new BankAtomic(NACCOUNTS, INITIAL_BALANCE);
        run(b3);

        IBank b4 = new ReentrantBank(NACCOUNTS, INITIAL_BALANCE);
        run(b4);

    }

    private static void run(IBank b){
        for (int i = 0; i < NACCOUNTS; i++){
            TransferThread t = new TransferThread(b, i,
                    INITIAL_BALANCE);
            t.setPriority(Thread.NORM_PRIORITY + i % 2);
            t.start () ;
        }
    }
}
