package com.libertycapital.marketapp.Jobs;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.WorkerThread;

import net.vrallev.android.cat.Cat;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

/**
 * Created by root on 6/19/17.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class DemoSyncEngine {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());

    private final Context mContext;

    public DemoSyncEngine(Context context) {
        mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @WorkerThread
    public boolean sync() {
        // do something fancy

        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new NetworkOnMainThreadException();
        }

        SystemClock.sleep(1_000);
        boolean success = Math.random() > 0.1; // successful 90% of the time
        saveSuccess(success);
        return success;
    }

    @NonNull
    public String getSuccessHistory() {
        try {
            byte[] data = FileUtils.readFile(getSuccessFile());
            if (data == null || data.length == 0) {
                return "";
            }
            return new String(data);
        } catch (IOException e) {
            return "";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void saveSuccess(boolean success) {
        String text = DATE_FORMAT.format(new Date()) + "\t\t" + success + '\n';
        try {
            FileUtils.writeFile(getSuccessFile(), text, true);
        } catch (IOException e) {
            Cat.e(e);
        }
    }

    private File getSuccessFile() {
        return new File(mContext.getCacheDir(), "success.txt");
    }
}
