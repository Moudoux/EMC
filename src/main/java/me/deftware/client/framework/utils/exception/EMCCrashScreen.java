package me.deftware.client.framework.utils.exception;

import me.deftware.client.framework.main.EMCMod;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.utils.ChatColor;
import me.deftware.client.framework.wrappers.gui.IGuiButton;
import me.deftware.client.framework.wrappers.gui.IGuiScreen;
import me.deftware.client.framework.wrappers.gui.list.StringList;
import me.deftware.client.framework.wrappers.render.IFontRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.util.crash.CrashReport;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;


public class EMCCrashScreen extends IGuiScreen {

    private StringList list;
    private final CrashReport report;
    private final Throwable e;
    private final boolean unexpected;
    private final File output;

    public EMCCrashScreen(Throwable e, CrashReport report, boolean unexpected, File output) {
        this.report = report;
        this.output = output;
        this.e = e;
        this.unexpected = unexpected;
        Bootstrap.CRASH_COUNT += 1;
        Bootstrap.CRASHED = true;
    }

    @Override
    protected void onInitGui() {
        this.clearButtons();
        this.children.clear();
        list = new StringList(width, height, 43, height - 32, IFontRenderer.getFontHeight() + 2, false);
        this.children.add(list);
        if (report != null) {
            String[] cause = report.getCauseAsString().split("at ");
            list.children().add(new StringList.StringEntry(cause[0].trim()));
            for (int i = 1; i < cause.length; i++) {
                list.children().add(new StringList.StringEntry("   at " + cause[i].trim()));
            }
        }
        this.addButton(new IGuiButton(0, this.width / 2 - 155, this.height - 29, 150, 20, ChatColor.GREEN + "Try to fix") {
            @Override
            public void onButtonClick(double mouseX, double mouseY) {
                Bootstrap.logger.warn("Resetting EMC...");
                // Reset EMC
                Bootstrap.reset();
                // Delete Fabric mods
                if (!System.getProperty("EMCDir", "null").equalsIgnoreCase("null")) {
                    Bootstrap.logger.warn("Scheduling Fabric mods for deletion");
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
                // Delete EMC mods
                Bootstrap.logger.warn("Deleting EMC mods");
                Arrays.stream(Objects.requireNonNull(Bootstrap.EMC_ROOT.listFiles())).forEach((file) -> {
                    if (file.getName().endsWith(".jar")) {
                       if (!file.delete()) {
                            Bootstrap.logger.error("Could not delete {}", file.getName());
                       }
                    }
                });
                // Delete configs
                Bootstrap.logger.warn("Deleting EMC mod configs");
                try {
                    FileUtils.deleteDirectory(Bootstrap.EMC_CONFIGS);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                // Initialize again
                Bootstrap.logger.warn("Re-Initializing EMC bootstrap");
                Bootstrap.CRASHED = false;
                Bootstrap.init();
                MinecraftClient.getInstance().openScreen(new TitleScreen());
                Bootstrap.getMods().values().forEach(EMCMod::postInit);
            }
        });
        this.addButton(new IGuiButton(1, this.width / 2 - 155 + 160, this.height - 29, 150, 20, ChatColor.RED + "Close game") {
            @Override
            public void onButtonClick(double mouseX, double mouseY) {
                System.exit(-1);
            }
        });
    }

    @Override
    protected void onDraw(int mouseX, int mouseY, float partialTicks) {
        list.render(mouseX, mouseY, partialTicks);
        IFontRenderer.drawCenteredString(ChatColor.BOLD + "Uh oh! Crashed" + (unexpected ? " (Unexpected)" : " (Reported)"), getIGuiScreenWidth() / 2, 20, 16777215);
    }

    @Override
    protected void onUpdate() {

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
