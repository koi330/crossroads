package fish.coy.crossroads.world.registration;

import com.mojang.serialization.Codec;
import fish.coy.crossroads.world.level.levelgen.structure.structures.CrossroadStructure;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.RegistryObject;

public class StructureTypes {

    public static final RegistryObject<StructureType<CrossroadStructure>> CROSSROAD_TYPE = register("crossroad", CrossroadStructure.CODEC);

    private static <T extends Structure> RegistryObject<StructureType<T>> register(String name, Codec<T> codec) {
        return CrossroadsRegistries.STRUCTURE_TYPE.register(name, () -> () -> codec);
    }
}
