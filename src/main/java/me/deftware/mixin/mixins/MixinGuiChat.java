package me.deftware.mixin.mixins;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import me.deftware.client.framework.command.CommandRegister;
import me.deftware.mixin.components.InternalGuiTextField;
import me.deftware.mixin.imp.IMixinGuiScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.ingame.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.server.command.CommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("ALL")
@Mixin(ChatScreen.class)
public abstract class MixinGuiChat extends Screen {

    @Shadow
    protected TextFieldWidget chatField;

    @Shadow
    private String field_2384;

    @Shadow
    private int field_2387;
    @Shadow
    private ParseResults<CommandSource> field_2388;
    @Shadow
    private CompletableFuture<Suggestions> field_2386;
    @Shadow
    private boolean field_2378;

    @Shadow
    public abstract String method_2102(String p_195130_1_, int p_195130_2_);

    @Shadow
    public abstract void method_2111(int p_195128_1_, String p_195128_2_);

    @Shadow
    public abstract void method_2116();

    @Shadow
    public abstract void method_2110();

    /**
     * @Author Deftware
     * @reason
     */
    @Overwrite
    public void onInitialized() {
        MinecraftClient.getInstance().keyboard.enableRepeatEvents(true);
        this.field_2387 = MinecraftClient.getInstance().inGameHud.getHudChat().method_1809().size();
        this.chatField = new InternalGuiTextField(0, ((IMixinGuiScreen) this).getFontRenderer(), 4, ((Screen) (Object) this).height - 12, ((Screen) (Object) this).width - 4, 12);
        this.chatField.setMaxLength(256);
        this.chatField.setHasBorder(false);
        this.chatField.setFocused(true);
        this.chatField.setText(this.field_2384);
        this.chatField.method_1856(false);
        this.chatField.method_1854(this::method_2102);
        this.chatField.setChangedListener(this::method_2111);
        this.listeners.add(this.chatField);
        this.method_2110();
    }

    @Inject(method = "method_2110", at = @At("RETURN"), cancellable = true)
    private void injectCustomSuggestions(CallbackInfo ci) {
        String string_1 = this.chatField.getText();
        StringReader stringReader_1 = new StringReader(string_1);
        if (stringReader_1.canRead() && string_1.startsWith((String) CommandRegister.getCommandTrigger())) {
            for (int triggerLength = 0; triggerLength < Math.min(CommandRegister.getCommandTrigger().length(), string_1.length()); triggerLength++) {
                stringReader_1.skip();
            }
            CommandDispatcher<CommandSource> commandDispatcher_1 = CommandRegister.getDispatcher();
            this.field_2388 = commandDispatcher_1.parse(stringReader_1, this.client.player.networkHandler.getCommandSource());
            if (!this.field_2378) {
                StringReader stringReader_2 = new StringReader(string_1.substring(0, Math.min(string_1.length(), this.chatField.getCursor())));
                if (stringReader_2.canRead()) {
                    for (int triggerLength = 0; triggerLength < CommandRegister.getCommandTrigger().length(); triggerLength++) {
                        stringReader_2.skip();
                    }
                    ParseResults<CommandSource> parseResults_1 = commandDispatcher_1.parse(stringReader_2, this.client.player.networkHandler.getCommandSource());
                    this.field_2386 = commandDispatcher_1.getCompletionSuggestions(parseResults_1);
                    this.field_2386.thenRun(() -> {
                        if (this.field_2386.isDone()) {
                            this.method_2116();
                        }
                    });
                }
            }
        }
    }

}