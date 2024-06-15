package pepjebs.craftablepotioncombinations;

import net.fabricmc.api.ModInitializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pepjebs.craftablepotioncombinations.recipe.CraftablePotionCombinationCraftingRecipe;

public class CraftablePotionCombinationsMod implements ModInitializer {

    public static final String MOD_ID = "craftablepotioncombinations";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static SpecialRecipeSerializer<CraftablePotionCombinationCraftingRecipe> CRAFTABLE_POTION_COMBINATION;

    @Override
    public void onInitialize() {
        CRAFTABLE_POTION_COMBINATION = Registry.register(Registries.RECIPE_SERIALIZER,
                Identifier.of(MOD_ID, "craftable_potion_combination"),
                new SpecialRecipeSerializer<>(CraftablePotionCombinationCraftingRecipe::new));
        LOGGER.info(Identifier.of(MOD_ID, "craftable_potion_combination"));
    }
}
