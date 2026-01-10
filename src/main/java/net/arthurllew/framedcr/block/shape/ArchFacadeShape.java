package net.arthurllew.framedcr.block.shape;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.StringRepresentable;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Custom block state attribute.
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public enum ArchFacadeShape implements StringRepresentable {
    ONE("one"),
    TWO_L("two_l"),
    TWO_R("two_r"),
    THREE_L("three_l"),
    THREE_R("three_r"),
    THREE_MIDDLE("three_middle");

    private final String name;

    ArchFacadeShape(String name) {
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
