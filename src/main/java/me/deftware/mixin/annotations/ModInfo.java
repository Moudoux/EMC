package me.deftware.mixin.annotations;

import me.deftware.client.framework.main.EMCMod;

import javax.annotation.Nonnull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SuppressWarnings("unused")
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface ModInfo {
    @Nonnull String name();

    @Nonnull String website();

    @Nonnull String author();

    @Nonnull Class<? extends EMCMod> main();

    boolean updateLinkOverride();

    double minversion();

    double version();
}
