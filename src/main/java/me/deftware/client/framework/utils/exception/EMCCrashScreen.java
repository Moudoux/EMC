package me.deftware.client.framework.utils.exception;

import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.utils.ChatColor;
import me.deftware.client.framework.utils.render.RenderUtils;
import me.deftware.client.framework.wrappers.gui.IGuiButton;
import me.deftware.client.framework.wrappers.gui.IGuiScreen;
import me.deftware.client.framework.wrappers.render.IFontRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.crash.CrashReport;
import org.apache.commons.io.FileUtils;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class EMCCrashScreen extends IGuiScreen {

    private CrashReport report;
    private Throwable e;
    private boolean unexpected, render = false;
    private String status = "";

    protected EMCCrashScreen(Throwable e, CrashReport report, boolean unexpected) {
        this.report = report;
        this.e = e;
        this.unexpected = unexpected;
        if (report.asString().contains("Rendering item")) {
            render = true;
        }
        Bootstrap.CRASHED = true;
    }

    @Override
    protected void onInitGui() {
        this.clearButtons();
        this.addButton(new IGuiButton(0, getIGuiScreenWidth() / 2 - 100, getIGuiScreenHeight() - 28, 98, 20, ChatColor.GREEN + "Try to fix it") {
            @Override
            public void onButtonClick(double v, double v1) {
                try {
                    if (render) {
                        status = "Please delete " + ChatColor.BOLD + "\".minecraft/assets/\"" + ChatColor.RESET + " and restart the game";
                    } else {
                        if (!System.getProperty("EMCDir", "null").equalsIgnoreCase("null")) {
                            String basePath = System.getProperty("EMCDir") + File.separator;
                            Arrays.stream(Objects.requireNonNull(new File(basePath).listFiles())).forEach((file) -> {
                                if (!file.getName().contains("EMC")) {
                                    try {
                                        FileUtils.writeStringToFile(new File(file.getAbsolutePath() + ".delete"), "Delete mod", "UTF-8");
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                        }
                        Arrays.stream(Objects.requireNonNull(Bootstrap.EMC_ROOT.listFiles())).forEach((file) -> {
                            if (file.getName().endsWith(".jar")) {
                                try {
                                    FileUtils.writeStringToFile(new File(file.getAbsolutePath() + ".delete"), "Delete mod", "UTF-8");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                        try {
                            FileUtils.deleteDirectory(Bootstrap.EMC_CONFIGS);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    status = "Applied fixes, please restart the game";
                } catch (Exception ex) {
                    status = "Failed to fix, please send a screenshot of this to deftware#0001";
                }
            }
        });
        this.addButton(new IGuiButton(1, getIGuiScreenWidth() / 2 + 2,

                        getIGuiScreenHeight() - 28, 98, 20, ChatColor.RED + "Close game") {
                    @Override
                    public void onButtonClick(double v, double v1) {
                        System.exit(-1);
                    }
                });
    }

    @Override
    protected void onDraw(int mouseX, int mouseY, float partialTicks) {
        this.drawIDefaultBackground();
        IFontRenderer.drawCenteredString(ChatColor.BOLD + "Uh oh! Crashed" + (unexpected ? " (Unexpected)" : " (Reported)"), getIGuiScreenWidth() / 2, 20, 16777215);
        IFontRenderer.drawCenteredString("EMCDir: " + System.getProperty("EMCDir", "Not set"), getIGuiScreenWidth() / 2, 40, 16777215);
        IFontRenderer.drawCenteredString("MCDir: " + System.getProperty("MCDir", "Not set"), getIGuiScreenWidth() / 2, 40 + IFontRenderer.getFontHeight() + 2, 16777215);

        int thickness = 2;
        // Top
        RenderUtils.drawRect(20, 65, getIGuiScreenWidth() - 20, 65 + thickness, Color.WHITE);
        // Left
        RenderUtils.drawRect(20, 65, 20 + thickness, getIGuiScreenHeight() - 50, Color.WHITE);
        // Right
        RenderUtils.drawRect(getIGuiScreenWidth() - 20, 65, getIGuiScreenWidth() - 20 + thickness, getIGuiScreenHeight() - 48, Color.WHITE);
        // Bottom
        RenderUtils.drawRect(20, getIGuiScreenHeight() - 50, getIGuiScreenWidth() - 20, getIGuiScreenHeight() - 50 + thickness, Color.WHITE);

        boolean flag = false;
        int y = 70;
        for (String s : report.getCauseAsString().split("at ")) {
            int x = 25;
            if (flag) {
                x += 10;
            }
            IFontRenderer.drawString(s.trim(), x, y, 0xFFFFFF);
            y += IFontRenderer.getFontHeight() + 2;
            flag = true;
        }

        IFontRenderer.drawCenteredString(status, getIGuiScreenWidth() / 2, getIGuiScreenHeight() - 28 - IFontRenderer.getFontHeight() - 2, 16777215);
    }

    @Override
    protected void onUpdate() {
        GLFW.glfwSetWindowSize(MinecraftClient.getInstance().getWindow().getHandle(), 1366, 768);
    }

    @Override
    protected void onKeyPressed(int keyCode, int action, int modifiers) {

    }

    @Override
    protected void onMouseReleased(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    protected void onMouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    protected void onGuiResize(int w, int h) {

    }

}
