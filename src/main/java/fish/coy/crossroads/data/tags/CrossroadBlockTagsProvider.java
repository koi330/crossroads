package fish.coy.crossroads.data.tags;

import fish.coy.crossroads.Crossroads;
import fish.coy.crossroads.world.registration.CrossroadsBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class CrossroadBlockTagsProvider extends BlockTagsProvider {


    public CrossroadBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, String modid, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, completableFuture, modid, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(Tags.Blocks.STONE).add(
                CrossroadsBlocks.WAYWARD_STONE.get()
        );
        tag(BlockTags.STONE_BRICKS).add(
                CrossroadsBlocks.WAYWARD_STONE_BRICKS.get()
        );
        tag(BlockTags.SLABS).add(
                CrossroadsBlocks.WAYWARD_STONE_SLAB.get(),
                CrossroadsBlocks.WAYWARD_STONE_BRICK_SLAB.get()
        );
        tag(BlockTags.STAIRS).add(
                CrossroadsBlocks.WAYWARD_STONE_BRICK_STAIRS.get()
        );
        tag(BlockTags.WALLS).add(
                CrossroadsBlocks.WAYWARD_STONE_BRICK_WALL.get()
        );
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                CrossroadsBlocks.WAYWARD_STONE.get(),
                CrossroadsBlocks.WAYWARD_STONE_BRICKS.get(),
                CrossroadsBlocks.WAYWARD_STONE_SLAB.get(),
                CrossroadsBlocks.WAYWARD_STONE_BRICK_SLAB.get(),
                CrossroadsBlocks.WAYWARD_STONE_BRICK_STAIRS.get()
        );
    }

    public static DataProvider.Factory<CrossroadBlockTagsProvider> providerFactory(CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper helper) {
        return output -> new CrossroadBlockTagsProvider(output, completableFuture, Crossroads.MODID, helper);
    }
}
