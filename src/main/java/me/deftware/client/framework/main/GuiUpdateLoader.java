package me.deftware.client.framework.main;

import com.google.gson.JsonObject;
import me.deftware.client.framework.wrappers.gui.IGuiButton;
import me.deftware.client.framework.wrappers.gui.IGuiScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;

/**
 * This class describes the gui shown by the EMC freamwork when
 * installed version is too low and update is necessary
 */

public class GuiUpdateLoader extends Screen {

    private JsonObject clientInfo;

    public GuiUpdateLoader(JsonObject clientInfo) {
        this.clientInfo = clientInfo;
    }

    private String get(String node) {
        return clientInfo.get(node).getAsString();
    }

    private int getDouble(String node) {
        return clientInfo.get(node).getAsInt();
    }

    @Override
    public void onInitialized() {
        buttons.clear();
        addButton(new IGuiButton(0,screenWidth / 2 - 100, screenHeight / 4 + 120 + 12 - 30,
                "Update " + (clientInfo.get("updateLinkOverride").getAsBoolean() ? clientInfo.get("name").getAsString()
                        : "EMC")) {
            @Override
            public void onButtonClick(double mouseX, double mouseY) {
                String link = "https://gitlab.com/EMC-Framework/EMC-Installer/tags";
                if (clientInfo.get("updateLinkOverride").getAsBoolean()) {
                    link = clientInfo.get("website").getAsString();
                }
                IGuiScreen.openLink(link);
                MinecraftClient.getInstance().stop();
            }
        });
        addButton(new IGuiButton(0,screenWidth / 2 - 100, screenHeight / 4 + 144 + 12 - 30, "Cancel (Mod won't load)") {
            @Override
            public void onButtonClick(double mouseX, double mouseY) {
                MinecraftClient.getInstance().openScreen(null);
            }
        });
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);
        drawBackground();

        drawStringCentered(fontRenderer, "EMC update required", screenWidth / 2, 21, 16777215);

        drawStringCentered(fontRenderer, "Client info:", screenWidth / 4, 70, 16777215);

        drawStringCentered(fontRenderer, "Name: " + get("name"), screenWidth / 4, 90, 16777215);

        drawStringCentered(fontRenderer, "Version: " + getDouble("version"), screenWidth / 4, 105,
                16777215);

        drawStringCentered(fontRenderer, "Author: " + get("author"), screenWidth / 4, 120, 16777215);

        // Right side

        drawStringCentered(fontRenderer, "Problem description:", screenWidth / 2 + screenWidth / 4, 70, 16777215);

        drawStringCentered(fontRenderer, "Your EMC version is too low", screenWidth / 2 + screenWidth / 4, 90,
                16777215);

        drawStringCentered(fontRenderer, get("name") + " requires " + get("minversion"), screenWidth / 2 + screenWidth / 4, 105,
                16777215);

        super.draw(mouseX, mouseY, partialTicks);
    }

}
