package fish.coy.crossroads;

import com.mojang.logging.LogUtils;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

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
        dataGenerator.addProvider(event.includeServer(), CrossroadRecipeProvider.providerFactory());
        dataGenerator.addProvider(event.includeServer(), CrossroadLootTableProvider.providerFactory());
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

    static class CrossroadRecipeProvider extends RecipeProvider {

        public CrossroadRecipeProvider(PackOutput output) {
            super(output);
        }

        @Override
        protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
            BlockFamily wayward_stone_family = new BlockFamily.Builder(Registration.WAYWARD_STONE.get()).slab(Registration.WAYWARD_STONE_SLAB.get()).getFamily();
            BlockFamily wayward_stone_bricks_family = new BlockFamily.Builder(Registration.WAYWARD_STONE_BRICKS.get()).slab(Registration.WAYWARD_STONE_BRICK_SLAB.get()).stairs(Registration.WAYWARD_STONE_BRICK_STAIRS.get()).wall(Registration.WAYWARD_STONE_BRICK_WALL.get()).getFamily();

            generateRecipes(consumer, wayward_stone_family);
            generateRecipes(consumer, wayward_stone_bricks_family);

            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Registration.WAYWARD_STONE_BRICKS.get()).define('#', Registration.WAYWARD_STONE.get()).pattern("##").pattern("##").unlockedBy("has_wayward_stone", has(Registration.WAYWARD_STONE.get())).save(consumer);

        }

        static Factory<CrossroadRecipeProvider> providerFactory() {
            return CrossroadRecipeProvider::new;
        }
    }

    static class CrossroadLootTableProvider extends LootTableProvider {

        public CrossroadLootTableProvider(PackOutput output, Set<ResourceLocation> locationSet, List<SubProviderEntry> entryList) {
            super(output, locationSet, entryList);
        }

        @Override
        protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationcontext) {

        }

        static List<SubProviderEntry> subProviders() {
            return List.of(
                    new SubProviderEntry(() -> new BlockLootSubProvider(explosionResistant(), featureFlagSet()) {
                        @Override
                        protected void generate() {
                            dropSelf(Registration.WAYWARD_STONE.get());
                            dropSelf(Registration.WAYWARD_STONE_BRICKS.get());
                            dropSelf(Registration.WAYWARD_STONE_SLAB.get());
                            dropSelf(Registration.WAYWARD_STONE_BRICK_SLAB.get());
                            dropSelf(Registration.WAYWARD_STONE_BRICK_STAIRS.get());
                            dropSelf(Registration.WAYWARD_STONE_BRICK_WALL.get());
                            add(Registration.WAYWARD_STONE_SLAB.get(), this::createSlabItemTable);
                            add(Registration.WAYWARD_STONE_BRICK_SLAB.get(), this::createSlabItemTable);
                        }

                        @Override
                        protected Iterable<Block> getKnownBlocks() {
                            return Registration.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
                        }
                    }, LootContextParamSets.BLOCK)
            );
        }

        static Set<ResourceLocation> specialTables() {
            return Collections.emptySet();
        }

        static Set<Item> explosionResistant() {
            return Collections.emptySet();
        }

        static FeatureFlagSet featureFlagSet() {
            return FeatureFlags.REGISTRY.allFlags();
        }

        static Factory<CrossroadLootTableProvider> providerFactory() {
            return output -> new CrossroadLootTableProvider(output, specialTables(), subProviders());
        }
    }

}
