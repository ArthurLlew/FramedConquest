package net.arthurllew.framedcr.block.properties;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.StringRepresentable;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Custom block state attribute.
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public enum SphereShape implements StringRepresentable {
    EGG("egg"),
    SMALL("small"),
    LARGE("large");

    private final String name;

    SphereShape(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
