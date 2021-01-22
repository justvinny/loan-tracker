package com.example.loantracker.utilities;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Message {
    public static void show(View view, String msg) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        snackbar.setAnchorView(view);
        snackbar.show();
    }
}
