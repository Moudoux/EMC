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
import net.minecraft.text.TextComponent;
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
    private String field_18973;

    @Shadow
    private int field_2387;
    @Shadow
    private ParseResults<CommandSource> parseResults;
    @Shadow
    private CompletableFuture<Suggestions> suggestionsFuture;
    @Shadow
    private boolean suggestionsTemporarilyDisabled;

    @Shadow
    public abstract String getRenderText(String p_195130_1_, int p_195130_2_);

    @Shadow
    public abstract void onChatFieldChanged(String p_195128_2_);

    @Shadow
    public abstract void updateSuggestionsAndExceptions();

    @Shadow
    public abstract void updateCommand();

    protected MixinGuiChat(TextComponent textComponent_1) {
        super(textComponent_1);
    }

    /**
     * @Author Deftware
     * @reason
     */
    @Overwrite
    public void init() {
        MinecraftClient.getInstance().keyboard.enableRepeatEvents(true);
        this.field_2387 = MinecraftClient.getInstance().inGameHud.getChatHud().method_1809().size();
        this.chatField = new InternalGuiTextField(((IMixinGuiScreen) this).getFont(), 4, ((Screen) (Object) this).height - 12, ((Screen) (Object) this).width - 4, 12);
        this.chatField.setMaxLength(256);
        this.chatField.setHasBorder(false);
        this.chatField.setText(this.field_18973);
        this.chatField.setRenderTextProvider(this::getRenderText);
        this.chatField.setChangedListener(this::onChatFieldChanged);
        this.children.add(this.chatField);
        this.updateCommand();
        this.setInitialFocus(this.chatField);
    }

    @Inject(method = "updateCommand", at = @At("RETURN"), cancellable = true)
    private void injectCustomSuggestions(CallbackInfo ci) {
        String string_1 = this.chatField.getText();
        StringReader stringReader_1 = new StringReader(string_1);
        if (stringReader_1.canRead() && string_1.startsWith((String) CommandRegister.getCommandTrigger())) {
            for (int triggerLength = 0; triggerLength < Math.min(CommandRegister.getCommandTrigger().length(), string_1.length()); triggerLength++) {
                stringReader_1.skip();
            }
            CommandDispatcher<CommandSource> commandDispatcher_1 = CommandRegister.getDispatcher();
            this.parseResults = commandDispatcher_1.parse(stringReader_1, this.minecraft.player.networkHandler.getCommandSource());
            if (!this.suggestionsTemporarilyDisabled) {
                StringReader stringReader_2 = new StringReader(string_1.substring(0, Math.min(string_1.length(), this.chatField.getCursor())));
                if (stringReader_2.canRead()) {
                    for (int triggerLength = 0; triggerLength < CommandRegister.getCommandTrigger().length(); triggerLength++) {
                        stringReader_2.skip();
                    }
                    ParseResults<CommandSource> parseResults_1 = commandDispatcher_1.parse(stringReader_2, this.minecraft.player.networkHandler.getCommandSource());
                    this.suggestionsFuture = commandDispatcher_1.getCompletionSuggestions(parseResults_1);
                    this.suggestionsFuture.thenRun(() -> {
                        if (this.suggestionsFuture.isDone()) {
                            this.updateSuggestionsAndExceptions();
                        }
                    });
                }
            }
        }
    }

}