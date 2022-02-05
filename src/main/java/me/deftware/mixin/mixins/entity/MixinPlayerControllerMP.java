package me.deftware.mixin.mixins.entity;

import me.deftware.client.framework.entity.EntityHand;
import me.deftware.client.framework.event.events.EventAttackEntity;
import me.deftware.client.framework.event.events.EventBlockBreakingCooldown;
import me.deftware.client.framework.event.events.EventBlockUpdate;
import me.deftware.client.framework.event.events.EventItemUse;
import me.deftware.client.framework.global.GameKeys;
import me.deftware.client.framework.global.GameMap;
import me.deftware.client.framework.math.position.DoubleBlockPosition;
import me.deftware.client.framework.network.packets.CPacketUseEntity;
import me.deftware.client.framework.registry.BlockRegistry;
import me.deftware.client.framework.registry.ItemRegistry;
import me.deftware.client.framework.render.camera.entity.CameraEntityMan;
import me.deftware.mixin.imp.IMixinPlayerControllerMP;
import me.deftware.mixin.imp.IMixinPlayerInteractEntityC2SPacket;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.network.Packet;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinPlayerControllerMP implements IMixinPlayerControllerMP {

    @Shadow
    private boolean breakingBlock;

    @Shadow private int blockBreakingCooldown;

    @Inject(method = "getReachDistance", at = @At(value = "RETURN"), cancellable = true)
    private void onGetReachDistance(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(GameMap.INSTANCE.get(GameKeys.BLOCK_REACH_DISTANCE, cir.getReturnValue()));
    }

    @Inject(method = "hasExtendedReach", at = @At(value = "TAIL"), cancellable = true)
    private void onHasExtendedReach(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(GameMap.INSTANCE.get(GameKeys.EXTENDED_REACH, cir.getReturnValue()));
    }

    @Redirect(method = "attackEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V"))
    private void onSendAttackEntityPacket(ClientPlayNetworkHandler clientPlayNetworkHandler, Packet<?> packet) {
        ((IMixinPlayerInteractEntityC2SPacket) packet).setActionType(CPacketUseEntity.Type.ATTACK);
        clientPlayNetworkHandler.sendPacket(packet);
    }

    @Inject(method = "attackEntity", at = @At("HEAD"), cancellable = true)
    public void attackEntity(PlayerEntity player, Entity target, CallbackInfo ci) {
        if (target == null || target == player || (CameraEntityMan.isActive() && target == CameraEntityMan.fakePlayer)) {
            ci.cancel();
        } else {
            EventAttackEntity event = new EventAttackEntity(player, target).broadcast();
            if (event.isCanceled())
                ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "interactEntity", cancellable = true)
    private void interactEntity(PlayerEntity player, Entity target, Hand hand, CallbackInfoReturnable<ActionResult> info) {
        if (target == null || target == player) {
            info.setReturnValue(ActionResult.FAIL);
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "interactEntityAtLocation", cancellable = true)
    public void interactEntityAtLocation(PlayerEntity player, Entity entity, EntityHitResult hitResult, Hand hand, CallbackInfoReturnable<ActionResult> ci) {
        if (entity == null || entity == player) {
            ci.setReturnValue(ActionResult.FAIL);
            ci.cancel();
        }
    }

    @Redirect(method = "updateBlockBreakingProgress", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;blockBreakingCooldown:I", opcode = 181))
    private void onUpdateBlockBreaking(ClientPlayerInteractionManager clientPlayerInteractionManager, int value) {
        EventBlockBreakingCooldown event = new EventBlockBreakingCooldown(value).broadcast();
        blockBreakingCooldown = event.getCooldown();
    }

    @Override
    public void setPlayerHittingBlock(boolean state) {
        this.breakingBlock = state;
    }

    @Redirect(method = "interactItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;"))
    private TypedActionResult<ItemStack> onItemUse(ItemStack instance, World world, PlayerEntity user, Hand hand) {
        Item item = instance.getItem();
        TypedActionResult<ItemStack> result = instance.use(world, user, hand);

        new EventItemUse(
                ItemRegistry.INSTANCE.getItem(item),
                EntityHand.of(hand)
        ).broadcast();

        return result;
    }

    @Redirect(method = "breakBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onBreak(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/player/PlayerEntity;)V"))
    private void onBlockBreak(Block block, World world, BlockPos pos, BlockState state, PlayerEntity player) {
        block.onBreak(world, pos, state, player);
        new EventBlockUpdate(EventBlockUpdate.State.Break,
                DoubleBlockPosition.fromMinecraftBlockPos(pos),
                BlockRegistry.INSTANCE.getBlock(block),
                EntityHand.MainHand
        ).broadcast();
    }

    /*
    @Redirect(method = "breakBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onBroken(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V"))
    private void onBlockBroken(Block block, WorldAccess world, BlockPos pos, BlockState state) {
        block.onBroken(world, pos, state);
        new EventBlockUpdate(EventBlockUpdate.State.Broken,
                DoubleBlockPosition.fromMinecraftBlockPos(pos),
                BlockRegistry.INSTANCE.getBlock(block),
                EntityHand.MainHand
        ).broadcast();
    }
     */

    @Redirect(method = "interactBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;useOnBlock(Lnet/minecraft/item/ItemUsageContext;)Lnet/minecraft/util/ActionResult;"))
    private ActionResult onBlockPlace(ItemStack instance, ItemUsageContext context) {
        Item item = instance.getItem();
        ActionResult result = instance.useOnBlock(context);

        if (result.isAccepted() && item instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();
            BlockPos pos = context.getBlockPos().offset(context.getSide());

            new EventBlockUpdate(
                    EventBlockUpdate.State.Place,
                    DoubleBlockPosition.fromMinecraftBlockPos(pos),
                    BlockRegistry.INSTANCE.getBlock(block),
                    EntityHand.of(context.getHand())
            ).broadcast();
        }
        return result;
    }

}
