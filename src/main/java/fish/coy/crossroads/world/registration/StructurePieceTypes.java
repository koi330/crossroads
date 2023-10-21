package fish.coy.crossroads.world.registration;

import fish.coy.crossroads.world.level.levelgen.structure.structures.CrossroadPiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.registries.RegistryObject;

public class StructurePieceTypes {

    public static final RegistryObject<StructurePieceType> CROSSROAD_PIECE = setTemplatePieceId(CrossroadPiece::new, "crossroad");


    private static RegistryObject<StructurePieceType> setFullContextPieceId(StructurePieceType type, String name) {
        return CrossroadsRegistries.STRUCTURE_PIECE.register(name, () -> type);
    }

    private static RegistryObject<StructurePieceType> setPieceId(StructurePieceType.ContextlessType type, String name) {
        return setFullContextPieceId(type, name);
    }

    private static RegistryObject<StructurePieceType> setTemplatePieceId(StructurePieceType.StructureTemplateType type, String name) {
        return setFullContextPieceId(type, name);
    }
}
