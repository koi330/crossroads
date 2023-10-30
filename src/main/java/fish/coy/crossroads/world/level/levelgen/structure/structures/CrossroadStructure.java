package fish.coy.crossroads.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fish.coy.crossroads.Crossroads;
import fish.coy.crossroads.world.registration.CrossroadsStructureTypes;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;

public class CrossroadStructure extends Structure {
    public static final Codec<CrossroadStructure> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(settingsCodec(instance), Codec.BOOL.fieldOf("gate").forGetter(structure -> {
            return structure.gate;
        })).apply(instance, CrossroadStructure::new);
    });
    public final boolean gate;

    protected CrossroadStructure(StructureSettings settings, boolean gate) {
        super(settings);
        this.gate = gate;
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        return onTopOfChunkCenter(context, Heightmap.Types.WORLD_SURFACE_WG, (builder) -> {
            generatePieces(builder, context);
        });
    }

    private void generatePieces(StructurePiecesBuilder builder, GenerationContext context) {
        WorldgenRandom random = context.random();
        ChunkPos chunkPos = context.chunkPos();
        int y = context.chunkGenerator().getFirstFreeHeight(chunkPos.getMinBlockX(), chunkPos.getMinBlockZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
        BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), y, chunkPos.getMinBlockZ());
        Rotation rotation = Util.getRandom(Rotation.values(), random);
        if (this.gate) {
            builder.addPiece(new CrossroadPiece(context.structureTemplateManager(), blockPos, rotation, new ResourceLocation(Crossroads.MODID, "crossroads/gate_1")));
        } else {
            builder.addPiece(new CrossroadPiece(context.structureTemplateManager(), blockPos, rotation, new ResourceLocation(Crossroads.MODID, "crossroads/crossroad")));
        }
    }

    @Override
    public StructureType<?> type() {
        return CrossroadsStructureTypes.CROSSROAD_TYPE.get();
    }
}
