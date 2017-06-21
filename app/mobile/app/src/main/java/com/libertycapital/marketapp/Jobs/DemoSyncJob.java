package com.libertycapital.marketapp.Jobs;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.libertycapital.marketapp.Main2Activity;
import com.libertycapital.marketapp.R;

import java.util.Random;

/**
 * Created by root on 6/19/17.
 */

public class DemoSyncJob extends Job {
    public static final String TAG = "job_demo_tag";

    public static void scheduleJob() {
        new JobRequest.Builder(DemoSyncJob.TAG)
                .setExecutionWindow(30_000L, 40_000L)
                .build()
                .schedule();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    protected Result onRunJob(Params params) {


        boolean success = new DemoSyncEngine(getContext()).sync();

        if (params.isPeriodic()) {
            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, new Intent(getContext(), Main2Activity.class), 0);

            Notification notification = new NotificationCompat.Builder(getContext())
                    .setContentTitle("Job Demo")
                    .setContentText("Periodic job ran")
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_avatar)
                    .setShowWhen(true)
                    .setColor(Color.GREEN)
                    .setLocalOnly(true)
                    .build();

            NotificationManagerCompat.from(getContext()).notify(new Random().nextInt(), notification);
        }

        return success ? Result.SUCCESS : Result.FAILURE;
    }
}
