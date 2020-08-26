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
            String text = this.textField.getText();
            StringReader reader = new StringReader(text);
            if (reader.canRead() && text.startsWith(CommandRegister.getCommandTrigger())) {
                for (int triggerLength = 0; triggerLength < Math.min(CommandRegister.getCommandTrigger().length(), text.length()); triggerLength++) {
                    reader.skip();
                }
                CommandDispatcher<ServerCommandSource> dispatcher = CommandRegister.getDispatcher();
                this.parse = dispatcher.parse(reader, MinecraftClient.getInstance().player.getCommandSource());
                if (!this.completingSuggestions) {
                    StringReader subReader = new StringReader(text.substring(0, Math.min(text.length(), this.textField.getCursor())));
                    if (subReader.canRead()) {
                        for (int triggerLength = 0; triggerLength < CommandRegister.getCommandTrigger().length(); triggerLength++) {
                            subReader.skip();
                        }
                        ParseResults<ServerCommandSource> parseResults = dispatcher.parse(subReader, MinecraftClient.getInstance().player.getCommandSource());
                        this.pendingSuggestions = dispatcher.getCompletionSuggestions(parseResults);
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
