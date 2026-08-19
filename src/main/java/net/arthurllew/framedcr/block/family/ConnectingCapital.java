package net.arthurllew.framedcr.block.family;

import net.neoforged.neoforge.registries.DeferredBlock;

public class ConnectingCapital {
    public static final ConnectingCapital CAPITAL_DORIC_DOWN = new ConnectingCapital();
    public static final ConnectingCapital CAPITAL_DORIC_UP = new ConnectingCapital();
    public static final ConnectingCapital CAPITAL_CORINTHIAN = new ConnectingCapital();
    public static final ConnectingCapital CORNICE = new ConnectingCapital();
    public static final ConnectingCapital PLINTH = new ConnectingCapital();

    /**
     * Full cube capital block.
     */
    private DeferredBlock<?> fullBlock;
    /**
     * Capital block vertical corner.
     */
    private DeferredBlock<?> verticalCorner;
    /**
     * Capital block vertical slab.
     */
    private DeferredBlock<?> verticalSlab;
    /**
     * Capital block vertical quarter.
     */
    private DeferredBlock<?> verticalQuarter;

    /**
     * Binds class instance with block holders.
     */
    public static void bind(ConnectingCapital blockFamily,
                            DeferredBlock<?> fullBlock, DeferredBlock<?> verticalCorner,
                            DeferredBlock<?> verticalSlab, DeferredBlock<?> verticalQuarter) {
        blockFamily.fullBlock = fullBlock;
        blockFamily.verticalCorner = verticalCorner;
        blockFamily.verticalSlab = verticalSlab;
        blockFamily.verticalQuarter = verticalQuarter;
    }

    /**
     * @return full cube capital block
     */
    public DeferredBlock<?> fullBlock() {
        return fullBlock;
    }

    /**
     * @return capital block vertical corner
     */
    public DeferredBlock<?> verticalCorner() {
        return verticalCorner;
    }

    /**
     * @return capital block vertical slab
     */
    public DeferredBlock<?> verticalSlab() {
        return verticalSlab;
    }

    /**
     * @return capital block vertical quarter
     */
    public DeferredBlock<?> verticalQuarter() {
        return verticalQuarter;
    }
}
