package me.deftware.mixin.mixins;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import me.deftware.client.framework.command.CommandRegister;
import me.deftware.mixin.imp.IMixinChatSuggestion;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.CommandSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.CompletableFuture;

@Mixin(CommandSuggestor.class)
public abstract class MixinChatSuggestion implements IMixinChatSuggestion {

    @Shadow
    private boolean completingSuggestions;
    
    @Shadow
    @Final
    private TextFieldWidget textField;
    
    @Shadow
    private ParseResults<ServerCommandSource> parse;
    
    @Shadow
    private CompletableFuture<Suggestions> pendingSuggestions;

    @Shadow
    protected abstract void show();

    private boolean inject = false;

    @Override
    public void setInject(boolean status) {
        inject = status;
    }

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "refresh", at = @At("RETURN"), cancellable = true)
    public void refresh(CallbackInfo ci) {
        if (inject) {
            String string_1 = this.textField.getText();
            StringReader stringReader_1 = new StringReader(string_1);
            if (stringReader_1.canRead() && string_1.startsWith(CommandRegister.getCommandTrigger())) {
                for (int triggerLength = 0; triggerLength < Math.min(CommandRegister.getCommandTrigger().length(), string_1.length()); triggerLength++) {
                    stringReader_1.skip();
                }
                CommandDispatcher<ServerCommandSource> commandDispatcher_1 = CommandRegister.getDispatcher();
                this.parse = commandDispatcher_1.parse(stringReader_1, MinecraftClient.getInstance().player.getCommandSource());
                if (!this.completingSuggestions) {
                    StringReader stringReader_2 = new StringReader(string_1.substring(0, Math.min(string_1.length(), this.textField.getCursor())));
                    if (stringReader_2.canRead()) {
                        for (int triggerLength = 0; triggerLength < CommandRegister.getCommandTrigger().length(); triggerLength++) {
                            stringReader_2.skip();
                        }
                        ParseResults<ServerCommandSource> parseResults_1 = commandDispatcher_1.parse(stringReader_2, MinecraftClient.getInstance().player.getCommandSource());
                        this.pendingSuggestions = commandDispatcher_1.getCompletionSuggestions(parseResults_1);
                        this.pendingSuggestions.thenRun(() -> {
                            if (this.pendingSuggestions.isDone()) {
                                this.show();
                            }
                        });
                    }
                }
            }
        }
    }
    
}
