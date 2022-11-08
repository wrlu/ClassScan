package com.wrlus.classscan;

import android.os.Parcelable;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

import dalvik.system.DexFile;

public class ParcelableScan {
    private static final String TAG = "ParcelScan";

    public static List<String> getAllParcelableClasses() {
        String[] bootClassPath = ParcelableScan.getBootClasspath();
        List<String> parcelableClassNames = new ArrayList<>();
        for (String bootJar : bootClassPath) {
            Log.d(TAG, "Loading classes from " + bootJar);
            Enumeration<String> allClasses = Objects.requireNonNull(getClasses(bootJar));
            while (allClasses.hasMoreElements()) {
                String className = allClasses.nextElement();
                if (isParcelableClass(className)) {
                    parcelableClassNames.add(className);
                }
            }
        }
        return parcelableClassNames;
    }

    public static Enumeration<String> getClasses(String dexPath) {
        try {
            DexFile dexFile = new DexFile(dexPath);
            return dexFile.entries();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] getBootClasspath() {
        return Objects.requireNonNull(System.getenv("BOOTCLASSPATH"))
                .split(":");
    }

    public static boolean isParcelableClass(String className) {
        try {
            Class.forName(className).asSubclass(Parcelable.class);
            return true;
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "ClassNotFoundException: " + className);
            return false;
        } catch (ClassCastException e) {
            return false;
        } catch (Exception | Error e) {
            e.printStackTrace();
            return false;
        }
    }
}
