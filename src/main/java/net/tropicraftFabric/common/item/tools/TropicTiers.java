package net.tropicraftFabric.common.item.tools;

import net.tropicraftFabric.common.item.TropicItemTier;
import net.tropicraftFabric.common.registry.TropicraftItems;

public enum TropicTiers{
    BAMBOO(new TropicItemTier(0,110, 1F, 6, 1.2F, TropicraftItems.BAMBOO_STICK)),
    ZIRCON(new TropicItemTier(1,200, 1F, 14, 4.5F, TropicraftItems.ZIRCON)),
    EUDIALYTE(new TropicItemTier(2,750, 2F, 14, 6.5F, TropicraftItems.EUDIALYTE)),
    ZIRCONIUM(new TropicItemTier(3,1800, 3F, 10, 8.5f, TropicraftItems.ZIRCONIUM));

    public final TropicItemTier defaultTier;

    TropicTiers(TropicItemTier defaultTier) {
        this.defaultTier = defaultTier;
    }

    public TropicItemTier getDefaultTier() {
        return this.defaultTier;
    }
}

