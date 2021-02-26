package com.example.loantracker.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Loan.class, LoanHistory.class}, version = 2, exportSchema = false)
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
                    .addCallback(roomCallBack)
                    .addMigrations(MIGRATION_1_2)
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

                LoanHistory sample11 = new LoanHistory(format.format(new Date()), 1, sample1.getAmount(), "Test N/A");
                LoanHistory sample12 = new LoanHistory(format.format(new Date()), 2, sample2.getAmount(), "Test N/A");
                LoanHistory sample13 = new LoanHistory(format.format(new Date()), 3, sample3.getAmount(), "Test N/A");

                loanHistoryDao.insert(sample11);
                loanHistoryDao.insert(sample12);
                loanHistoryDao.insert(sample13);
            });
        }
    };

    /*
     * Database version 2 adds the description property in loan_history_table.
     */
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            if (database.getVersion() == 1) {
                dbWriterExecutor.execute(() -> {
                    database.execSQL("ALTER TABLE loan_history_table ADD COLUMN description TEXT NOT NULL DEFAULT 'N/A'");
                });
            }
        }
    };
}
