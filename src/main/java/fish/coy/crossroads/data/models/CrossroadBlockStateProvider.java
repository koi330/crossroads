package fish.coy.crossroads.data.models;

import fish.coy.crossroads.Crossroads;
import fish.coy.crossroads.world.registration.CrossroadsBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CrossroadBlockStateProvider extends BlockStateProvider {

    public CrossroadBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    private void simpleBlockWithItem(Block block) {
        ModelFile model = cubeAll(block);
        simpleBlockWithItem(block, model);
    }

    @Override
    protected void registerStatesAndModels() {
        ResourceLocation stone = CrossroadsBlocks.WAYWARD_STONE.getId().withPrefix("block/");
        ResourceLocation brick = CrossroadsBlocks.WAYWARD_STONE_BRICKS.getId().withPrefix("block/");

        simpleBlockWithItem(CrossroadsBlocks.WAYWARD_STONE_BRICKS.get());

        ModelFile stonemodel = cubeAll(CrossroadsBlocks.WAYWARD_STONE.get());
        ModelFile stonemirrormodel = models().singleTexture("wayward_stone_mirrored", mcLoc(ModelProvider.BLOCK_FOLDER + "/cube_mirrored_all"), "all", stone);

        getVariantBuilder(CrossroadsBlocks.WAYWARD_STONE.get()).partialState().setModels(
                new ConfiguredModel(stonemodel),
                new ConfiguredModel(stonemirrormodel),
                new ConfiguredModel(stonemodel, 0, 180, false),
                new ConfiguredModel(stonemirrormodel, 0, 180, false));

        simpleBlockItem(CrossroadsBlocks.WAYWARD_STONE.get(), stonemodel);

        stairsBlock(CrossroadsBlocks.WAYWARD_STONE_BRICK_STAIRS.get(), brick);
        slabBlock(CrossroadsBlocks.WAYWARD_STONE_BRICK_SLAB.get(), brick, brick);
        wallBlock(CrossroadsBlocks.WAYWARD_STONE_BRICK_WALL.get(), brick);

        slabBlock(CrossroadsBlocks.WAYWARD_STONE_SLAB.get(), stone, stone);

        ModelFile wall = models().wallInventory(CrossroadsBlocks.WAYWARD_STONE_BRICK_WALL.getId().getPath() + "_inventory", brick);

        itemModels().withExistingParent(CrossroadsBlocks.WAYWARD_STONE_BRICK_STAIRS.getId().getPath(), CrossroadsBlocks.WAYWARD_STONE_BRICK_STAIRS.getId().withPrefix("block/"));
        itemModels().withExistingParent(CrossroadsBlocks.WAYWARD_STONE_BRICK_SLAB.getId().getPath(), CrossroadsBlocks.WAYWARD_STONE_BRICK_SLAB.getId().withPrefix("block/"));
        itemModels().withExistingParent(CrossroadsBlocks.WAYWARD_STONE_SLAB.getId().getPath(), CrossroadsBlocks.WAYWARD_STONE_SLAB.getId().withPrefix("block/"));

        itemModels().getBuilder(CrossroadsBlocks.WAYWARD_STONE_BRICK_WALL.getId().getPath()).parent(wall);
    }

    public static Factory<CrossroadBlockStateProvider> providerFactory(ExistingFileHelper helper) {
        return output -> new CrossroadBlockStateProvider(output, Crossroads.MODID, helper);
    }
}
