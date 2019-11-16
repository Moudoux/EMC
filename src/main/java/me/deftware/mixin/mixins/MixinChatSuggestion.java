package me.deftware.mixin.mixins;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import me.deftware.client.framework.command.CommandRegister;
import me.deftware.mixin.imp.IMixinChatSuggestion;
import net.minecraft.class_4717;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.server.command.CommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.CompletableFuture;

@Mixin(class_4717.class)
public abstract class MixinChatSuggestion implements IMixinChatSuggestion {

    @Shadow
    private boolean field_21614;
    
    @Shadow
    @Final
    public TextFieldWidget field_21599;
    
    @Shadow
    public ParseResults<CommandSource> field_21610;
    
    @Shadow
    public CompletableFuture<Suggestions> field_21611;

    @Shadow
    public abstract void method_23937();

    private boolean inject = false;

    @Override
    public void setInject(boolean status) {
        inject = status;
    }

    @Inject(method = "method_23934", at = @At("RETURN"), cancellable = true)
    public void method_23934(CallbackInfo ci) {
        if (inject) {
            String string_1 = this.field_21599.getText();
            StringReader stringReader_1 = new StringReader(string_1);
            if (stringReader_1.canRead() && string_1.startsWith((String) CommandRegister.getCommandTrigger())) {
                for (int triggerLength = 0; triggerLength < Math.min(CommandRegister.getCommandTrigger().length(), string_1.length()); triggerLength++) {
                    stringReader_1.skip();
                }
                CommandDispatcher<CommandSource> commandDispatcher_1 = CommandRegister.getDispatcher();
                this.field_21610 = commandDispatcher_1.parse(stringReader_1, MinecraftClient.getInstance().player.networkHandler.getCommandSource());
                if (!this.field_21614) {
                    StringReader stringReader_2 = new StringReader(string_1.substring(0, Math.min(string_1.length(), this.field_21599.getCursor())));
                    if (stringReader_2.canRead()) {
                        for (int triggerLength = 0; triggerLength < CommandRegister.getCommandTrigger().length(); triggerLength++) {
                            stringReader_2.skip();
                        }
                        ParseResults<CommandSource> parseResults_1 = commandDispatcher_1.parse(stringReader_2, MinecraftClient.getInstance().player.networkHandler.getCommandSource());
                        this.field_21611 = commandDispatcher_1.getCompletionSuggestions(parseResults_1);
                        this.field_21611.thenRun(() -> {
                            if (this.field_21611.isDone()) {
                                this.method_23937();
                            }
                        });
                    }
                }
            }
        }
    }
    
}
