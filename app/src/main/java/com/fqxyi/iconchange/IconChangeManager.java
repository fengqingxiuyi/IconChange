package com.fqxyi.iconchange;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

/**
 * @author ShenBF
 *
 * 动态替换ICON管理类
 *
 * 情景分析：
 *
 * 1、预埋一个ICON，需要执行以下两步：
 * 1.1、修改AndroidManifest.xml文件中activity-alias的android:icon值
 * 1.2、替换{@link #getActivityPath}方法中iconType的case值
 *
 * 2、预埋两个ICON，需要执行以下几步：
 * 2.1、修改AndroidManifest.xml文件中activity-alias的android:icon值，并新增一个activity-alias
 * 2.2、为{@link #ACTIVITY_PATH_ARR}添加新增activity-alias的name，name名记得写的通用些
 * 2.3、替换getActivityPath方法中iconType的case值，并新增一个case
 *
 * 3、预埋多个ICON，同理预埋两个ICON，但是需要注意：一旦新增过activity-alias就不能再删除，
 * 否则可能存在应用升级之后，在桌面上找不到应用的情况。
 */
public class IconChangeManager {

    private static final String[] ACTIVITY_PATH_ARR = {
            ".SplashActivity",
            ".activityAlias",//第一个预埋icon
    };

    /**
     * 此方法会频繁变更，所以放在最前面
     */
    private static String getActivityPath(int iconType) {
        String activityPath = ACTIVITY_PATH_ARR[0];
        switch (iconType) {
            case 1: // 对应第一个预埋icon
                activityPath = ACTIVITY_PATH_ARR[1];
                break;
        }
        return activityPath;
    }

    /**
     * 更换应用icon
     */
    public static void changeIcon(Context context, int iconType) {
        if (context == null) {
            return;
        }
        if (iconType == 0) { // 不更新icon，需要重置为默认icon
            String activityPath = ACTIVITY_PATH_ARR[0];
            if (!componentEnabled(context, activityPath)) {
                enableComponent(context, activityPath);
            }
            return;
        }
        enableComponent(context, getActivityPath(iconType));
    }

    /**
     * 判断 component 是否可用
     */
    private static boolean componentEnabled(Context context, String activityPath) {
        if (context == null) {
            return false;
        }
        try {
            PackageManager pm = context.getPackageManager();
            ComponentName cn = new ComponentName(context, context.getPackageName() + activityPath);
            return pm.getComponentEnabledSetting(cn) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断 component 是否被禁用
     */
    private static boolean componentDisabled(Context context, String activityPath) {
        if (context == null) {
            return false;
        }
        try {
            PackageManager pm = context.getPackageManager();
            ComponentName cn = new ComponentName(context, context.getPackageName() + activityPath);
            return pm.getComponentEnabledSetting(cn) == PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 启用组件
     */
    public static void enableComponent(Context context, String activityPath) {
        if (context == null || TextUtils.isEmpty(activityPath)) {
            return;
        }
        if (componentEnabled(context, activityPath)) {
            return;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            // 先禁用除需要启用组件之外的组件
            for (String item : ACTIVITY_PATH_ARR) {
                if (item.equals(activityPath)) {
                    continue;
                }
                packageManager.setComponentEnabledSetting(
                        new ComponentName(context, context.getPackageName() + item),
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            }
            // 再启用需要启用的组件
            packageManager.setComponentEnabledSetting(
                    new ComponentName(context, context.getPackageName() + activityPath),
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED, 0); // 0表示立刻杀死应用，并刷新ICON
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开应用
     * @param uri intent 数据
     */
    public static void openApp(Context context, Uri uri) {
        if (context == null) {
            return;
        }
        try {
            for (String item : ACTIVITY_PATH_ARR) {
                if (componentDisabled(context, item)) {
                    continue;
                }
                Intent intent = new Intent();
                ComponentName cn = new ComponentName(
                        context, context.getPackageName() + item);
                intent.setComponent(cn);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(uri);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
