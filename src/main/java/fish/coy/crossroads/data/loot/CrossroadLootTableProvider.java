package fish.coy.crossroads.data.loot;

import fish.coy.crossroads.data.loot.packs.CrossroadsBlockLoot;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CrossroadLootTableProvider extends LootTableProvider {

    public CrossroadLootTableProvider(PackOutput output, Set<ResourceLocation> locationSet, List<SubProviderEntry> entryList) {
        super(output, locationSet, entryList);
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationcontext) {

    }

    static List<SubProviderEntry> subProviders() {
        return List.of(
                new SubProviderEntry(CrossroadsBlockLoot::new, LootContextParamSets.BLOCK)
        );
    }

    static Set<ResourceLocation> specialTables() {
        return Collections.emptySet();
    }

    public static Factory<CrossroadLootTableProvider> providerFactory() {
        return output -> new CrossroadLootTableProvider(output, specialTables(), subProviders());
    }

}
