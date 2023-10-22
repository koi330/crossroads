package fish.coy.crossroads.data.models;

import fish.coy.crossroads.Crossroads;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CrossroadBlockModelProvider extends BlockModelProvider {

    public CrossroadBlockModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }

    public static Factory<CrossroadBlockModelProvider> providerFactory(ExistingFileHelper helper) {
        return output -> new CrossroadBlockModelProvider(output, Crossroads.MODID, helper);
    }
}
