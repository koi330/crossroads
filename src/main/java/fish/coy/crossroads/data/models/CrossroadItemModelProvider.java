package fish.coy.crossroads.data.models;

import fish.coy.crossroads.Crossroads;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class CrossroadItemModelProvider extends ItemModelProvider {

    public CrossroadItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }

    public static Factory<CrossroadItemModelProvider> providerFactory(ExistingFileHelper helper) {
        return output -> new CrossroadItemModelProvider(output, Crossroads.MODID, helper);
    }
}
