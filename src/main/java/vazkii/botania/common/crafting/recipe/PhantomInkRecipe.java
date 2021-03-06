/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 *
 * File Created @ [Mar 17, 2015, 5:21:07 PM (GMT)]
 */
package vazkii.botania.common.crafting.recipe;

import javax.annotation.Nonnull;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import vazkii.botania.api.item.IPhantomInkable;
import vazkii.botania.common.item.ModItems;

public class PhantomInkRecipe implements IRecipe {

	@Override
	public boolean matches(@Nonnull InventoryCrafting var1, @Nonnull World var2) {
		boolean foundInk = false;
		boolean foundItem = false;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty()) {
				if(stack.getItem() == ModItems.phantomInk && !foundInk)
					foundInk = true;
				else if(!foundItem) {
					if(stack.getItem() instanceof IPhantomInkable && stack.getItem().getContainerItem(stack).isEmpty())
						foundItem = true;
					else return false;
				} else return false;
			}
		}

		return foundInk && foundItem;
	}

	@Nonnull
	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
		ItemStack item = ItemStack.EMPTY;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty() && stack.getItem() instanceof IPhantomInkable && item.isEmpty())
				item = stack;
		}

		IPhantomInkable inkable = (IPhantomInkable) item.getItem();
		ItemStack copy = item.copy();
		inkable.setPhantomInk(copy, !inkable.hasPhantomInk(item));
		return copy;
	}

	@Override
	public int getRecipeSize() {
		return 10;
	}

	@Nonnull
	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}

	@Nonnull
	@Override
	public NonNullList<ItemStack> getRemainingItems(@Nonnull InventoryCrafting inv) {
		return ForgeHooks.defaultRecipeGetRemainingItems(inv);
	}
}
