package com.example.loantracker.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Loan.class, LoanHistory.class}, version = 1, exportSchema = false)
public abstract class LoanDatabase extends RoomDatabase {

    private static final String TAG = "LoanDatabase";
    private static LoanDatabase instance;

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

                loanDao.insert(new Loan("Johnny", 2500.52));
                loanDao.insert(new Loan("Robby", 599.00));
                loanDao.insert(new Loan("Jenny", 3959.88));
            });
        }
    };
}
