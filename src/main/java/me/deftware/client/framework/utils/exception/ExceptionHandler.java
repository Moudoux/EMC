package me.deftware.client.framework.utils.exception;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.crash.CrashReport;

import java.io.File;

public class ExceptionHandler {

    public static void handleCrash(Throwable e, CrashReport report, boolean unexpected, File output) {
        MinecraftClient.getInstance().openScreen(new EMCCrashScreen(e, report, unexpected, output));
    }

}
