package fish.coy.crossroads.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import fish.coy.crossroads.Crossroads;
import fish.coy.crossroads.world.registration.StructureTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;

public class CrossroadStructure extends Structure {
    public static final Codec<CrossroadStructure> CODEC = simpleCodec(CrossroadStructure::new);

    protected CrossroadStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        return onTopOfChunkCenter(context, Heightmap.Types.WORLD_SURFACE_WG, (builder) -> {
            generatePieces(builder, context);
        });
    }

    private static void generatePieces(StructurePiecesBuilder builder, GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        int y = context.chunkGenerator().getFirstFreeHeight(chunkPos.getMinBlockX(), chunkPos.getMinBlockZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
        BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), y, chunkPos.getMinBlockZ());
        builder.addPiece(new CrossroadPiece(context.structureTemplateManager(), blockPos, new ResourceLocation(Crossroads.MODID, "crossroads/crossroad")));
    }

    @Override
    public StructureType<?> type() {
        return StructureTypes.CROSSROAD_TYPE.get();
    }
}
