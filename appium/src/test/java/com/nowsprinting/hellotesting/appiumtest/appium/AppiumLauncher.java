package com.nowsprinting.hellotesting.appiumtest.appium;


import java.io.File;
import java.io.IOException;

// TODO re-launch appium server if it is hung up.
public class AppiumLauncher {
    private static Process process;
    
    // private constructor
    private AppiumLauncher() {}
    
    public static void launch() {
        if (process != null) {
            return; // already launched
        }
        File classpathRoot = new File(System.getProperty("user.dir"));
        Runtime runtime = Runtime.getRuntime();
        // TODO fixed log path
        File appiumLog = new File(classpathRoot, "appium.log");
        // TODO fixed appium path
        try {
            process = runtime.exec(new String[]{
                    "C:\\Program Files (x86)\\Appium\\node.exe",
                    "C:\\Program Files (x86)\\Appium\\node_modules\\appium\\lib\\server\\main.js", 
                    "--log", 
                    appiumLog.getAbsolutePath(),
                    "--log-level",
                    "debug:debug"});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (process == null) {
            throw new RuntimeException("fail to launch appium server");
        }

        /*
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                stop();
            }
        });*/

        // wait for appium server start
        // TODO should not use fixed value
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void stop() {
        if (process != null) {
            process.destroy();
            process = null;
        }
    }

}
