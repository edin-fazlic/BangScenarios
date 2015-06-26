package com.BangScenarios.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.BangScenarios.Activities.MyActivity;

/**
 * Created by Edin on 21.6.2015.
 */
public class DialogBuilder {

    private static boolean fistfulChecked;

    public static void getExitDialog(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        activity.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public static void popExpansionDialog(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("Expansions")
                .setMultiChoiceItems(new String[]{"Fistful of cards"},
                        new boolean[]{((MyActivity)activity).isFistfulIncluded()},
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                switch (which) {
                                    case 0:
                                        fistfulChecked = isChecked;
                                }
                            }
                        })
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((MyActivity)activity).toggleFistful(fistfulChecked);
                        ((MyActivity)activity).resetAction();
                    }
                })
                .show();
    }
}
