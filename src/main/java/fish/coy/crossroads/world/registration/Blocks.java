package fish.coy.crossroads.world.registration;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Blocks {

    public static final RegistryObject<Block> WAYWARD_STONE = register("wayward_stone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<SlabBlock> WAYWARD_STONE_SLAB = register("wayward_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(WAYWARD_STONE.get()).forceSolidOn()));

    public static final RegistryObject<Block> WAYWARD_STONE_BRICKS = register("wayward_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(WAYWARD_STONE.get()).forceSolidOn()));
    public static final RegistryObject<SlabBlock> WAYWARD_STONE_BRICK_SLAB = register("wayward_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(WAYWARD_STONE.get()).forceSolidOn()));
    public static final RegistryObject<StairBlock> WAYWARD_STONE_BRICK_STAIRS = register("wayward_stone_brick_stairs", () -> new StairBlock(WAYWARD_STONE_BRICKS.get()::defaultBlockState, BlockBehaviour.Properties.copy(WAYWARD_STONE.get()).forceSolidOn()));
    public static final RegistryObject<WallBlock> WAYWARD_STONE_BRICK_WALL = register("wayward_stone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(WAYWARD_STONE_BRICKS.get()).forceSolidOn()));

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> supplier) {
        return CrossroadsRegistries.BLOCKS.register(name, supplier);
    }


}