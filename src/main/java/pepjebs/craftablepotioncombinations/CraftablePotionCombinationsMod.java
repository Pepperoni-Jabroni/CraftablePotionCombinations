package pepjebs.craftablepotioncombinations;

import net.fabricmc.api.ModInitializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import pepjebs.craftablepotioncombinations.recipe.CraftablePotionCombinationCraftingRecipe;

public class CraftablePotionCombinationsMod implements ModInitializer {

    public static final String MOD_ID = "craftablepotioncombinations";

    public static SpecialCraftingRecipe.SpecialRecipeSerializer<CraftablePotionCombinationCraftingRecipe>
            CRAFTABLE_POTION_COMBINATION;

    @Override
    public void onInitialize() {
        CRAFTABLE_POTION_COMBINATION = Registry.register(
                Registries.RECIPE_SERIALIZER,
                Identifier.of(MOD_ID, "craftable_potion_combination"),
                new SpecialCraftingRecipe.SpecialRecipeSerializer<>(
                        CraftablePotionCombinationCraftingRecipe::new
                )
        );
    }
}
