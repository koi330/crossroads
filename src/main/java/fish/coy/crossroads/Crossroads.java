package fish.coy.crossroads;

import com.mojang.logging.LogUtils;
import fish.coy.crossroads.data.loot.CrossroadLootTableProvider;
import fish.coy.crossroads.data.models.CrossroadBlockModelProvider;
import fish.coy.crossroads.data.models.CrossroadBlockStateProvider;
import fish.coy.crossroads.data.models.CrossroadItemModelProvider;
import fish.coy.crossroads.data.recipes.packs.CrossroadRecipeProvider;
import fish.coy.crossroads.data.tags.CrossroadBlockTagsProvider;
import fish.coy.crossroads.world.registration.CrossroadsCreativeModeTabs;
import fish.coy.crossroads.world.registration.CrossroadsRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

@Mod(Crossroads.MODID)
public class Crossroads {

    public static final String MODID = "crossroads";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Crossroads() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CrossroadsRegistries.init(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(CrossroadsCreativeModeTabs::addCreative);
        modEventBus.addListener(Crossroads::gatherData);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    static void gatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> future = event.getLookupProvider();

        dataGenerator.addProvider(event.includeClient(), CrossroadBlockStateProvider.providerFactory(helper));
        dataGenerator.addProvider(event.includeClient(), CrossroadBlockModelProvider.providerFactory(helper));
        dataGenerator.addProvider(event.includeClient(), CrossroadItemModelProvider.providerFactory(helper));
        dataGenerator.addProvider(event.includeServer(), CrossroadRecipeProvider.providerFactory());
        dataGenerator.addProvider(event.includeServer(), CrossroadLootTableProvider.providerFactory());
        dataGenerator.addProvider(event.includeServer(), CrossroadBlockTagsProvider.providerFactory(future, helper));
    }

}
