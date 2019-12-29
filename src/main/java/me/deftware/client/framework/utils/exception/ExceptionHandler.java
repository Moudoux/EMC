package me.deftware.client.framework.utils.exception;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.crash.CrashReport;

public class ExceptionHandler {

    public static void handleCrash(Throwable e, CrashReport report, boolean unexpected) {
        MinecraftClient.getInstance().openScreen(new EMCCrashScreen(e, report, unexpected));
    }

}
