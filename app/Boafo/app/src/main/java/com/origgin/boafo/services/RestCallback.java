package com.origgin.boafo.services;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 5/29/17.
 */

public abstract class   StartStopCallback <T> implements Callback<T> {
    StartStopCallback() {
        onStart();
    }

    public final void onSuccess(T body, Response response) {
        onSuccess2(body, response);
        onFinish();
    }

    public final void onFailure(Throwable throwable) {
        onFailure2(throwable);
        onFinish();
    }

    public abstract void onStart();

    public abstract void onFinish();

    public abstract void onSuccess2(T body, Response response);

    public abstract void onFailure2(Throwable throwable);
}