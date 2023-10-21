package fish.coy.crossroads.world.level.levelgen.structure.structures;

import com.mojang.logging.LogUtils;
import fish.coy.crossroads.world.registration.StructurePieceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.slf4j.Logger;

public class CrossroadPiece extends TemplateStructurePiece {
    private static final Logger LOGGER = LogUtils.getLogger();

    public CrossroadPiece(StructureTemplateManager manager, BlockPos blockPos, ResourceLocation location) {
        super(StructurePieceTypes.CROSSROAD_PIECE.get(), 0, manager, location, location.toString(), new StructurePlaceSettings(), blockPos);
    }

    public CrossroadPiece(StructureTemplateManager manager, CompoundTag tag) {
        super(StructurePieceTypes.CROSSROAD_PIECE.get(), tag, manager, (location) -> new StructurePlaceSettings());
    }

    @Override
    protected void handleDataMarker(String p_226906_, BlockPos p_226907_, ServerLevelAccessor p_226908_, RandomSource p_226909_, BoundingBox p_226910_) {

    }
}
