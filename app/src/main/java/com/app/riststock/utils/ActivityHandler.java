package com.app.riststock.utils;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;


import com.app.riststock.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by wokman on 11/11/2016.
 */

public class ActivityHandler {

    public static boolean isPackageExist(Context context, String packageName) {

        List<ApplicationInfo> packages;
        PackageManager pm;

        pm = context.getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(packageName))
                return true;
        }
        return false;

    }

    public static void OpenGooglePlay(Context context) {

        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }

    }

    public static void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    public static void OpenBrowser(Context context, String url) {

        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        browserIntent.setPackage("com.android.chrome");
//        context.startActivity(browserIntent);

        try {
            context.startActivity(browserIntent);
        } catch (ActivityNotFoundException ex) {
            // Chrome browser presumably not installed so allow user to choose instead
            browserIntent.setPackage(null);
            context.startActivity(browserIntent);
        }

    }

    public static void OpenSendEmail(Context context, String subject, String body, String receiver) {

        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject + "");
        intent.putExtra(Intent.EXTRA_TEXT, body + "");
        intent.setData(Uri.parse("mailto:" + receiver)); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        context.startActivity(intent);

    }

    @SuppressLint("MissingPermission")
    public static void makeCall(Context context, String phoneNumber) {

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);

    }

    public static void OpenDialerIntent(Context context, String phoneNumber) {

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);

    }

    public static void shareTextUrl(Context context, String title, String url, String shareChannel) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        if (shareChannel != null && !shareChannel.equals(""))
            share.setPackage(shareChannel);
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, title + "");
        String content = title != null && !title.isEmpty() ? title : "";
        content += url != null && !url.isEmpty() ? "\n\n" + url : "";
        share.putExtra(Intent.EXTRA_TEXT, content);

        String shareStr = context.getString(R.string.share_with);
        context.startActivity(Intent.createChooser(share, shareStr));
    }

    public static void shareTwitter(Context context, String message) {
        Intent tweetIntent = new Intent(Intent.ACTION_SEND);
        tweetIntent.putExtra(Intent.EXTRA_TEXT, message);
        tweetIntent.setType("text/plain");

        PackageManager packManager = context.getPackageManager();
        List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);

        boolean resolved = false;
        for (ResolveInfo resolveInfo : resolvedInfoList) {
            if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
                tweetIntent.setClassName(
                        resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.name);
                resolved = true;
                break;
            }
        }
        if (resolved) {
            context.startActivity(tweetIntent);
        } else {
            Intent i = new Intent();
            i.putExtra(Intent.EXTRA_TEXT, message);
            i.setAction(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://twitter.com/intent/tweet?text=" + urlEncode(message)));
            context.startActivity(i);
//            Toast("Twitter app isn't found");
//            Toast.makeText(this, "Twitter app isn't found", Toast.LENGTH_LONG).show();
        }
    }

    private static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.wtf("TAG", "UTF-8 should always be supported", e);
            return "";
        }
    }


}
