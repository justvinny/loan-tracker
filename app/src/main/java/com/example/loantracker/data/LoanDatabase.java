package com.example.loantracker.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Loan.class, LoanHistory.class}, version = 1, exportSchema = false)
public abstract class LoanDatabase extends RoomDatabase {

    private static final String TAG = "LoanDatabase";
    private static LoanDatabase instance;

    public static SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    public static final int N_THREADS = 1;
    public static ExecutorService dbWriterExecutor = Executors.newFixedThreadPool(N_THREADS);

    public abstract LoanDao loanDao();
    public abstract  LoanHistoryDao loanHistoryDao();

    public static synchronized LoanDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    LoanDatabase.class, "loan_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }

        return instance;
    }

    // Sample Data Upon DB Creation.
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            LoanDatabase.dbWriterExecutor.execute(() -> {
                LoanDao loanDao = instance.loanDao();

                Loan sample1 = new Loan("Johnny", 2500.52);
                Loan sample2 = new Loan("Robby", 599.00);
                Loan sample3 = new Loan("Jenny", 3959.88);

                loanDao.insert(sample1);
                loanDao.insert(sample2);
                loanDao.insert(sample3);

                LoanHistoryDao loanHistoryDao = instance.loanHistoryDao();

                LoanHistory sample11 = new LoanHistory(format.format(new Date()), 1, sample1.getAmount());
                LoanHistory sample12 = new LoanHistory(format.format(new Date()), 2, sample2.getAmount());
                LoanHistory sample13 = new LoanHistory(format.format(new Date()), 3, sample3.getAmount());

                loanHistoryDao.insert(sample11);
                loanHistoryDao.insert(sample12);
                loanHistoryDao.insert(sample13);
            });
        }
    };
}
