package fish.coy.crossroads.world.registration;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.RegistryObject;

public class CrossroadsCreativeModeTabs {

    public static final RegistryObject<CreativeModeTab> WAYWARD_TAB = CrossroadsRegistries.CREATIVE_MODE_TABS.register("wayward_tab", () -> CreativeModeTab.builder().icon(() -> CrossroadsItems.WAYWARD_STONE_BRICKS.get().getDefaultInstance()).build());

    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == WAYWARD_TAB.getKey()) {
            event.accept(CrossroadsItems.WAYWARD_STONE);
            event.accept(CrossroadsItems.WAYWARD_STONE_SLAB);
            event.accept(CrossroadsItems.WAYWARD_STONE_BRICKS);
            event.accept(CrossroadsItems.WAYWARD_STONE_BRICK_STAIRS);
            event.accept(CrossroadsItems.WAYWARD_STONE_BRICK_SLAB);
            event.accept(CrossroadsItems.WAYWARD_STONE_BRICK_WALL);
        }
    }
}
