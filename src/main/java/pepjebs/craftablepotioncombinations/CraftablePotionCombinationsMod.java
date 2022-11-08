package pepjebs.craftablepotioncombinations;

import net.fabricmc.api.ModInitializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import pepjebs.craftablepotioncombinations.recipe.CraftablePotionCombinationCraftingRecipe;

public class CraftablePotionCombinationsMod implements ModInitializer {

    public static final String MOD_ID = "craftablepotioncombinations";
    public static SpecialRecipeSerializer<CraftablePotionCombinationCraftingRecipe> CRAFTABLE_POTION_COMBINATION;

    @Override
    public void onInitialize() {
        CRAFTABLE_POTION_COMBINATION = Registry.register(Registry.RECIPE_SERIALIZER,
                new Identifier(MOD_ID, "craftable_potion_combination"),
                new SpecialRecipeSerializer<>(CraftablePotionCombinationCraftingRecipe::new));
    }
}
