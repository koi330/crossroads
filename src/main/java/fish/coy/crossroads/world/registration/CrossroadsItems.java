package fish.coy.crossroads.world.registration;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class CrossroadsItems {
    public static final RegistryObject<BlockItem> WAYWARD_STONE = registerBlock(CrossroadsBlocks.WAYWARD_STONE);
    public static final RegistryObject<BlockItem> WAYWARD_STONE_SLAB = registerBlock(CrossroadsBlocks.WAYWARD_STONE_SLAB);

    public static final RegistryObject<BlockItem> WAYWARD_STONE_BRICKS = registerBlock(CrossroadsBlocks.WAYWARD_STONE_BRICKS);
    public static final RegistryObject<BlockItem> WAYWARD_STONE_BRICK_SLAB = registerBlock(CrossroadsBlocks.WAYWARD_STONE_BRICK_SLAB);
    public static final RegistryObject<BlockItem> WAYWARD_STONE_BRICK_STAIRS = registerBlock(CrossroadsBlocks.WAYWARD_STONE_BRICK_STAIRS);
    public static final RegistryObject<BlockItem> WAYWARD_STONE_BRICK_WALL = registerBlock(CrossroadsBlocks.WAYWARD_STONE_BRICK_WALL);

    public static final RegistryObject<Item> WAYWARD_BRICK = registerItem("wayward_brick");

    private static RegistryObject<BlockItem> registerBlock(RegistryObject<? extends Block> registryObject) {
        return registerBlock(registryObject.getId().getPath(), registryObject);
    }

    private static RegistryObject<BlockItem> registerBlock(String name, RegistryObject<? extends Block> registryObject) {
        return registerItem(name, () -> new BlockItem(registryObject.get(), new Item.Properties()));
    }

    private static RegistryObject<Item> registerItem(String name) {
        return CrossroadsRegistries.ITEMS.register(name, () -> new Item(new Item.Properties()));
    }
    private static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> supplier) {
        return CrossroadsRegistries.ITEMS.register(name, supplier);
    }

}
