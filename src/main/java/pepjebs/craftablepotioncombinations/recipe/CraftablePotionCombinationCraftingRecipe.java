package pepjebs.craftablepotioncombinations.recipe;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import pepjebs.craftablepotioncombinations.CraftablePotionCombinationsMod;

import java.util.ArrayList;
import java.util.List;

public class CraftablePotionCombinationCraftingRecipe extends SpecialCraftingRecipe {

    public CraftablePotionCombinationCraftingRecipe(Identifier id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        return !inventory.containsAny(p -> !p.isOf(Items.POTION) && !p.isEmpty());
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        List<StatusEffectInstance> statusEffects = new ArrayList<>();
        for (int i = 0; i < inventory.size(); i++) {
            var stack = inventory.getStack(i);
            if (stack.isEmpty()) continue;
            statusEffects.addAll(PotionUtil.getPotionEffects(stack));
        }
        return PotionUtil.setCustomPotionEffects(new ItemStack(Items.POTION), statusEffects);
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height > 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CraftablePotionCombinationsMod.CRAFTABLE_POTION_COMBINATION;
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingInventory inventory) {
        DefaultedList<ItemStack> remainder = DefaultedList.of();
        boolean firstPotion = true;
        for (int i = 0; i < inventory.size(); i++) {
            var stack = inventory.getStack(i);
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
