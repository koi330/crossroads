package fish.coy.crossroads.data.recipes.packs;

import fish.coy.crossroads.world.registration.CrossroadsBlocks;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;

import java.util.function.Consumer;

public class CrossroadRecipeProvider extends RecipeProvider {

    public CrossroadRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        BlockFamily wayward_stone_family = new BlockFamily
                .Builder(CrossroadsBlocks.WAYWARD_STONE.get())
                .slab(CrossroadsBlocks.WAYWARD_STONE_SLAB.get())
                .getFamily();
        BlockFamily wayward_stone_bricks_family = new BlockFamily
                .Builder(CrossroadsBlocks.WAYWARD_STONE_BRICKS.get())
                .slab(CrossroadsBlocks.WAYWARD_STONE_BRICK_SLAB.get())
                .stairs(CrossroadsBlocks.WAYWARD_STONE_BRICK_STAIRS.get())
                .wall(CrossroadsBlocks.WAYWARD_STONE_BRICK_WALL.get())
                .getFamily();

        generateRecipes(consumer, wayward_stone_family);
        generateRecipes(consumer, wayward_stone_bricks_family);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, CrossroadsBlocks.WAYWARD_STONE_BRICKS.get())
                .define('#', CrossroadsBlocks.WAYWARD_STONE.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_wayward_stone", has(CrossroadsBlocks.WAYWARD_STONE.get()))
                .save(consumer);
    }

    public static Factory<CrossroadRecipeProvider> providerFactory() {
        return CrossroadRecipeProvider::new;
    }
}
