package com.fqxyi.iconchange;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AppSwitchMonitor implements Application.ActivityLifecycleCallbacks {
  private static final String TAG = "AppSwitchMonitor";
  //计数器
  private int count = 0;

  @Override
  public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

  }

  @Override
  public void onActivityStarted(@NonNull Activity activity) {
    if (count == 0) { //后台切换到前台
    }
    count++;
  }

  @Override
  public void onActivityResumed(@NonNull Activity activity) {

  }

  @Override
  public void onActivityPaused(@NonNull Activity activity) {

  }

  @Override
  public void onActivityStopped(@NonNull Activity activity) {
    count--;
    if (count == 0) { //前台切换到后台
      Log.d(TAG, "从前台切换到后台了");
      IconChangeManager.changeIcon(activity, 1, false);
    }
  }

  @Override
  public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

  }

  @Override
  public void onActivityDestroyed(@NonNull Activity activity) {

  }
}
