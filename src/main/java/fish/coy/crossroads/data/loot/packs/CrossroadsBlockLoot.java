package fish.coy.crossroads.data.loot.packs;

import fish.coy.crossroads.world.registration.CrossroadsBlocks;
import fish.coy.crossroads.world.registration.CrossroadsRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collections;
import java.util.Set;

public class CrossroadsBlockLoot extends BlockLootSubProvider {
    public CrossroadsBlockLoot() {
        super(explosionResistant(), featureFlagSet());
    }

    @Override
    protected void generate() {
        dropSelf(CrossroadsBlocks.WAYWARD_STONE.get());
        dropSelf(CrossroadsBlocks.WAYWARD_STONE_BRICKS.get());
        dropSelf(CrossroadsBlocks.WAYWARD_STONE_SLAB.get());
        dropSelf(CrossroadsBlocks.WAYWARD_STONE_BRICK_SLAB.get());
        dropSelf(CrossroadsBlocks.WAYWARD_STONE_BRICK_STAIRS.get());
        dropSelf(CrossroadsBlocks.WAYWARD_STONE_BRICK_WALL.get());
        add(CrossroadsBlocks.WAYWARD_STONE_SLAB.get(), this::createSlabItemTable);
        add(CrossroadsBlocks.WAYWARD_STONE_BRICK_SLAB.get(), this::createSlabItemTable);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return CrossroadsRegistries.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    static Set<Item> explosionResistant() {
        return Collections.emptySet();
    }

    static FeatureFlagSet featureFlagSet() {
        return FeatureFlags.REGISTRY.allFlags();
    }
}
