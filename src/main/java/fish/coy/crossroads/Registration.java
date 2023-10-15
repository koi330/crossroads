package fish.coy.crossroads;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Registration {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Crossroads.MODID);
    public static final RegistryObject<Block> WAYWARD_STONE = BLOCKS.register("wayward_stone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static final RegistryObject<Block> WAYWARD_STONE_BRICKS = BLOCKS.register("wayward_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(WAYWARD_STONE.get()).forceSolidOn()));
    public static final RegistryObject<SlabBlock> WAYWARD_STONE_BRICK_SLAB = BLOCKS.register("wayward_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(WAYWARD_STONE.get()).forceSolidOn()));
    public static final RegistryObject<StairBlock> WAYWARD_STONE_BRICK_STAIRS = BLOCKS.register("wayward_stone_brick_stairs", () -> new StairBlock(WAYWARD_STONE_BRICKS.get()::defaultBlockState, BlockBehaviour.Properties.copy(WAYWARD_STONE.get()).forceSolidOn()));
    public static final RegistryObject<SlabBlock> WAYWARD_STONE_SLAB = BLOCKS.register("wayward_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(WAYWARD_STONE.get()).forceSolidOn()));
    public static final RegistryObject<WallBlock> WAYWARD_STONE_BRICK_WALL = BLOCKS.register("wayward_stone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(WAYWARD_STONE_BRICKS.get()).forceSolidOn()));

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Crossroads.MODID);
    public static final RegistryObject<BlockItem> WAYWARD_STONE_BRICKS_ITEM = ITEMS.register("wayward_stone_bricks", () -> new BlockItem(WAYWARD_STONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> WAYWARD_STONE_BRICK_STAIRS_ITEM = ITEMS.register("wayward_stone_brick_stairs", () -> new BlockItem(WAYWARD_STONE_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> WAYWARD_STONE_BRICK_SLAB_ITEM = ITEMS.register("wayward_stone_brick_slab", () -> new BlockItem(WAYWARD_STONE_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> WAYWARD_STONE_ITEM = ITEMS.register("wayward_stone", () -> new BlockItem(WAYWARD_STONE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> WAYWARD_STONE_SLAB_ITEM = ITEMS.register("wayward_stone_slab", () -> new BlockItem(WAYWARD_STONE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> WAYWARD_STONE_BRICK_WALL_ITEM = ITEMS.register("wayward_stone_brick_wall", () -> new BlockItem(WAYWARD_STONE_BRICK_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> WAYWARD_BRICK = ITEMS.register("wayward_brick", () -> new Item(new Item.Properties()));

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Crossroads.MODID);
    public static final RegistryObject<CreativeModeTab> WAYWARD_TAB = CREATIVE_MODE_TABS.register("wayward_tab", () -> CreativeModeTab.builder().icon(() -> WAYWARD_STONE_BRICKS_ITEM.get().getDefaultInstance()).build());

    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPE = DeferredRegister.create(Registries.STRUCTURE_TYPE, Crossroads.MODID);
    public static final RegistryObject<StructureType<CrossroadStructure>> CROSSROAD_TYPE = STRUCTURE_TYPE.register("crossroad", supplyStructureType(CrossroadStructure.CODEC));

    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE = DeferredRegister.create(Registries.STRUCTURE_PIECE, Crossroads.MODID);
    public static final RegistryObject<StructurePieceType> CROSSROAD_PIECE = STRUCTURE_PIECE.register("crossroad", supplyStructurePiece(CrossroadPiece::new));

    public static void init(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        STRUCTURE_TYPE.register(modEventBus);
        STRUCTURE_PIECE.register(modEventBus);
    }

    static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == WAYWARD_TAB.getKey()) {
            event.accept(WAYWARD_STONE_ITEM);
            event.accept(WAYWARD_STONE_SLAB_ITEM);
            event.accept(WAYWARD_STONE_BRICKS_ITEM);
            event.accept(WAYWARD_STONE_BRICK_STAIRS_ITEM);
            event.accept(WAYWARD_STONE_BRICK_SLAB_ITEM);
            event.accept(WAYWARD_STONE_BRICK_WALL_ITEM);
        }
    }

    private static <T extends Structure> Supplier<StructureType<T>> supplyStructureType(Codec<T> codec) {
        return () -> () -> codec;
    }

    private static Supplier<StructurePieceType> supplyStructurePiece(StructurePieceType.StructureTemplateType type) {
        return () -> type;
    }
}
