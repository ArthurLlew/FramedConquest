package net.arthurllew.framedcr.block.shape;

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

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}
