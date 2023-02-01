package it.uniba.dib.sms222329;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.lang.reflect.Array;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import it.uniba.dib.sms222329.classi.Tesi;
import it.uniba.dib.sms222329.database.Database;

public class Utility {

    public static final int TESISTA = 1;
    public static final int RELATORE = 2;
    public static final int CORELATORE = 3;
    public static DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private Utility() {}

    public static void replaceFragment(FragmentManager fragmentManager, int containerId, Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(null)
                .commit();
    }
}

