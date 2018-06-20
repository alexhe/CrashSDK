package com.summer.crashsdk;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.File;

public final class CrashSDK {

    private static final String TAG = "CrashSDK";

    public static final int init(Context context){
        System.loadLibrary("crashsdk");

        String abi = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for(String s:Build.SUPPORTED_ABIS){
                abi += s;
            }
        }else{
            abi = Build.CPU_ABI;
        }

        setSystemInfo(Build.FINGERPRINT, Build.VERSION.SDK_INT, abi);
        Log.e("cqx_ttt","sdk int: " + Build.VERSION.SDK_INT + " abi: " + abi);

        File files = context.getExternalFilesDir(null);
        File dir = new File(files.getAbsolutePath() + File.separator + "tombstones");
        if(!dir.exists()){
            dir.mkdirs();
        }

        setLogDir(dir.getAbsolutePath());

        return initNative();
    }

    public static void doCrash(int id){
        test(id);
    }

    private static native int initNative();
    private static native void setSystemInfo(String fingerprint, int version, String supportedABI);
    private static native void setLogDir(String dir);

    private static native int test(int id);

}