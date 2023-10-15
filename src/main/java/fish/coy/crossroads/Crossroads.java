package fish.coy.crossroads;

import com.mojang.logging.LogUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Crossroads.MODID)
public class Crossroads {

    public static final String MODID = "crossroads";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Crossroads() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        Registration.init(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(Registration::addCreative);
        modEventBus.addListener(Crossroads::gatherData);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    static void gatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        dataGenerator.addProvider(event.includeClient(), CrossroadBlockStateProvider.providerFactory(helper));
        dataGenerator.addProvider(event.includeClient(), CrossroadBlockModelProvider.providerFactory(helper));
        dataGenerator.addProvider(event.includeClient(), CrossroadItemModelProvider.providerFactory(helper));
    }

    static class CrossroadBlockStateProvider extends BlockStateProvider {

        public CrossroadBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
            super(output, modid, exFileHelper);
        }

        private void simpleBlockWithItem(Block block) {
            ModelFile model = cubeAll(block);
            simpleBlockWithItem(block, model);
        }

        @Override
        protected void registerStatesAndModels() {
            ResourceLocation stone = Registration.WAYWARD_STONE.getId().withPrefix("block/");
            ResourceLocation brick = Registration.WAYWARD_STONE_BRICKS.getId().withPrefix("block/");

            simpleBlockWithItem(Registration.WAYWARD_STONE_BRICKS.get());

            ModelFile stonemodel = cubeAll(Registration.WAYWARD_STONE.get());
            ModelFile stonemirrormodel = models().singleTexture("wayward_stone_mirrored", mcLoc(ModelProvider.BLOCK_FOLDER + "/cube_mirrored_all"), "all", stone);

            getVariantBuilder(Registration.WAYWARD_STONE.get()).partialState().setModels(
                    new ConfiguredModel(stonemodel),
                    new ConfiguredModel(stonemirrormodel),
                    new ConfiguredModel(stonemodel, 0, 180, false),
                    new ConfiguredModel(stonemirrormodel, 0, 180, false));

            simpleBlockItem(Registration.WAYWARD_STONE.get(), stonemodel);

            stairsBlock(Registration.WAYWARD_STONE_BRICK_STAIRS.get(), brick);
            slabBlock(Registration.WAYWARD_STONE_BRICK_SLAB.get(), brick, brick);
            wallBlock(Registration.WAYWARD_STONE_BRICK_WALL.get(), brick);

            slabBlock(Registration.WAYWARD_STONE_SLAB.get(), stone, stone);

            ModelFile wall = models().wallInventory(Registration.WAYWARD_STONE_BRICK_WALL.getId().getPath() + "_inventory", brick);

            itemModels().withExistingParent(Registration.WAYWARD_STONE_BRICK_STAIRS.getId().getPath(), Registration.WAYWARD_STONE_BRICK_STAIRS.getId().withPrefix("block/"));
            itemModels().withExistingParent(Registration.WAYWARD_STONE_BRICK_SLAB.getId().getPath(), Registration.WAYWARD_STONE_BRICK_SLAB.getId().withPrefix("block/"));
            itemModels().withExistingParent(Registration.WAYWARD_STONE_SLAB.getId().getPath(), Registration.WAYWARD_STONE_SLAB.getId().withPrefix("block/"));

            itemModels().getBuilder(Registration.WAYWARD_STONE_BRICK_WALL.getId().getPath()).parent(wall);
        }

        static Factory<CrossroadBlockStateProvider> providerFactory(ExistingFileHelper helper) {
            return output -> new CrossroadBlockStateProvider(output, Crossroads.MODID, helper);
        }
    }

    static class CrossroadBlockModelProvider extends BlockModelProvider {

        public CrossroadBlockModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
            super(output, modid, existingFileHelper);
        }

        @Override
        protected void registerModels() {

        }

        static Factory<CrossroadBlockModelProvider> providerFactory(ExistingFileHelper helper) {
            return output -> new CrossroadBlockModelProvider(output, Crossroads.MODID, helper);
        }
    }

    static class CrossroadItemModelProvider extends ItemModelProvider {

        public CrossroadItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
            super(output, modid, existingFileHelper);
        }

        @Override
        protected void registerModels() {

        }

        static Factory<CrossroadItemModelProvider> providerFactory(ExistingFileHelper helper) {
            return output -> new CrossroadItemModelProvider(output, Crossroads.MODID, helper);
        }
    }


}
