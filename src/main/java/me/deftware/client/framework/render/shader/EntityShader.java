package me.deftware.client.framework.render.shader;

import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.chat.ChatMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Deftware
 */
public class EntityShader {

	private static @Getter @Setter Predicate<Entity> entityPredicate = entity -> true;
	private static @Getter @Setter boolean storage = false, entities = false, items = false;
	private static @Setter Supplier<Boolean> activeSupplier = () -> false;
	private static @Getter @Setter Shader shader;

	public static boolean shouldRun() {
		return activeSupplier.get() && shader != null;
	}

	/*
		Default types
	 */

	public static void selectiveEntityTypePredicate(BiPredicate<String, Boolean> validator) {
		entityPredicate = entity ->
				entities && entity instanceof LivingEntity && validator.test(new ChatMessage().fromText(
					entity.getType().getName()).toString(false), entity instanceof PlayerEntity);
	}

}
