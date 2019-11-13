package me.deftware.mixin.mixins;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import me.deftware.client.framework.command.CommandRegister;
import me.deftware.mixin.components.InternalGuiTextField;
import me.deftware.mixin.imp.IMixinGuiScreen;
import net.minecraft.class_4717;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.server.command.CommandSource;
import net.minecraft.text.LiteralText;
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
    private String originalChatText;

    @Shadow
    private int messageHistorySize;

    //@Shadow
    private ParseResults<CommandSource> parseResults;

    //@Shadow
    private CompletableFuture<Suggestions> suggestionsFuture;

    //@Shadow
    private boolean completingSuggestion;

    //@Shadow
    public abstract String getRenderText(String p_195130_1_, int p_195130_2_);

    @Shadow
    public abstract void method_23945(String p_195128_2_);

    @Shadow
    public class_4717 field_21616;

    protected MixinGuiChat(LiteralText textComponent_1) {
        super(textComponent_1);
    }

    /**
     * @author Deftware
     * @reason
     */
    @Overwrite
    public void init() {
        MinecraftClient.getInstance().keyboard.enableRepeatEvents(true);
        this.messageHistorySize = MinecraftClient.getInstance().inGameHud.getChatHud().getMessageHistory().size();
        this.chatField = new InternalGuiTextField(((IMixinGuiScreen) this).getFont(), 4, ((Screen) (Object) this).height - 12, ((Screen) (Object) this).width - 4, 12);
        this.chatField.setMaxLength(256);
        this.chatField.setHasBorder(false);
        this.chatField.setText(this.originalChatText);
        //this.chatField.setRenderTextProvider(this::getRenderText);
        this.chatField.setChangedListener(this::method_23945);
        this.children.add(this.chatField);
        this.field_21616 = new class_4717(this.minecraft, this, this.chatField, this.font, true, false, 1, 10, true, -805306368);
        this.field_21616.method_23934();
        this.setInitialFocus(this.chatField);
    }
//Lnet/minecraft/class_4717;method_23934()V
    @Inject(method = "method_23934", at = @At(value = "RETURN", target = "Lnet/minecraft/class_4717;method_23934()V"), cancellable = true)
    private void injectCustomSuggestions(CallbackInfo ci) {
        String string_1 = this.chatField.getText();
        StringReader stringReader_1 = new StringReader(string_1);
        if (stringReader_1.canRead() && string_1.startsWith((String) CommandRegister.getCommandTrigger())) {
            for (int triggerLength = 0; triggerLength < Math.min(CommandRegister.getCommandTrigger().length(), string_1.length()); triggerLength++) {
                stringReader_1.skip();
            }
            CommandDispatcher<CommandSource> commandDispatcher_1 = CommandRegister.getDispatcher();
            this.parseResults = commandDispatcher_1.parse(stringReader_1, this.minecraft.player.networkHandler.getCommandSource());
            if (!this.completingSuggestion) {
                StringReader stringReader_2 = new StringReader(string_1.substring(0, Math.min(string_1.length(), this.chatField.getCursor())));
                if (stringReader_2.canRead()) {
                    for (int triggerLength = 0; triggerLength < CommandRegister.getCommandTrigger().length(); triggerLength++) {
                        stringReader_2.skip();
                    }
                    ParseResults<CommandSource> parseResults_1 = commandDispatcher_1.parse(stringReader_2, this.minecraft.player.networkHandler.getCommandSource());
                    this.suggestionsFuture = commandDispatcher_1.getCompletionSuggestions(parseResults_1);
                    this.suggestionsFuture.thenRun(() -> {
                        if (this.suggestionsFuture.isDone()) {
                            this.field_21616.method_23937();
                        }
                    });
                }
            }
        }
    }

}