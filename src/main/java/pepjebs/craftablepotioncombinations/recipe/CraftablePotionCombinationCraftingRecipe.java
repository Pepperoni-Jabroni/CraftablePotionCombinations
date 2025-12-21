package pepjebs.craftablepotioncombinations.recipe;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import pepjebs.craftablepotioncombinations.CraftablePotionCombinationsMod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CraftablePotionCombinationCraftingRecipe extends SpecialCraftingRecipe {

    public CraftablePotionCombinationCraftingRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        return input.getStacks().stream().anyMatch(p -> p.isOf(Items.POTION) && !p.isEmpty())
                && input.getStacks().stream().noneMatch(p -> !p.isOf(Items.POTION) && !p.isEmpty());
    }

    @Override
    public ItemStack craft(CraftingRecipeInput inventory, RegistryWrapper.WrapperLookup lookup) {
        List<StatusEffectInstance> statusEffects = new ArrayList<>();
        int colorR = 0;
        int colorG = 0;
        int colorB = 0;
        int potionCount = 0;
        ItemStack potion = null;
        for (int i = 0; i < inventory.size(); i++) {
            var stack = inventory.getStackInSlot(i);
            if (stack.isEmpty() || !stack.getComponents().contains(DataComponentTypes.POTION_CONTENTS)) continue;
            Collection<StatusEffectInstance> collection = new ArrayList<>();
            stack.get(DataComponentTypes.POTION_CONTENTS).getEffects().iterator().forEachRemaining(collection::add);
            statusEffects.addAll(collection);
            int stackColor = stack.get(DataComponentTypes.POTION_CONTENTS).getColor();
            colorR += stackColor >> 16;
            colorG += stackColor >> 8 & 0xFF;
            colorB += stackColor & 0xFF;
            ++potionCount;
            potion = stack;
        }
        ItemStack result = potion.copy();
        result.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(
                Optional.empty(), Optional.of((colorR / potionCount << 16) +
                (colorG / potionCount << 8) +
                (colorB / potionCount)), statusEffects, Optional.empty()));
        return result;
    }

    @Override
    public RecipeSerializer<CraftablePotionCombinationCraftingRecipe> getSerializer(){
        return CraftablePotionCombinationsMod.CRAFTABLE_POTION_COMBINATION;
    }

    @Override
    public DefaultedList<ItemStack> getRecipeRemainders(CraftingRecipeInput inventory) {
        DefaultedList<ItemStack> remainder = DefaultedList.of();
        boolean firstPotion = true;
        for (int i = 0; i < inventory.size(); i++) {
            var stack = inventory.getStackInSlot(i);
            if (stack.isOf(Items.POTION) && !firstPotion) {
                remainder.add(new ItemStack(Items.GLASS_BOTTLE));
            } else if (stack.isOf(Items.POTION)) {
                remainder.add(ItemStack.EMPTY);
                firstPotion = false;
            } else {
                remainder.add(ItemStack.EMPTY);
            }
        }
        return remainder;
    }
}
