package de.ximanton.headcraft.structure;

import de.ximanton.headcraft.Main;
import de.ximanton.headcraft.enums.GUIItemType;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * represents a open crafting inventory for a player
 */
public class PlayerCraftingInterface {

    public static int[] headSlots = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

    private Inventory inv;
    private int page = 1;
    private final HashMap<Integer, HeadRecipe> recipeSlots = new HashMap<>();

    public PlayerCraftingInterface() {
        this.inv = createCraftingInventory();
        reloadPage();
    }

    /**
     * create the inventory and add gui items
     * @return the inventory that has been added
     */
    private Inventory createCraftingInventory() {
        inv = Bukkit.createInventory(null, 54, "HeadCrafting");
        ItemStack borderItem = GUIItemType.BORDER.toItemStack();
        for (int i : new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 46, 47, 48, 50, 51, 52}) {
            inv.setItem(i, borderItem);
        }
        inv.setItem(45, GUIItemType.ARROW_LEFT.toItemStack());
        inv.setItem(53, GUIItemType.ARROW_RIGHT.toItemStack());
        inv.setItem(49, GUIItemType.CLOSE.toItemStack());
        return inv;
    }

    public HeadRecipe getRecipeFromSlot(int slot) {
        return recipeSlots.getOrDefault(slot, null);
    }

    public void setPage(int page) {
        this.page = page;
    }

    /**
     * reloads the current page, needs to be called after PlayerCraftingInterface#setPage
     */
    public void reloadPage() {
        recipeSlots.clear();
        int i = 0;
        for (int slot : headSlots) {
            int recipeIndex = (page-1) * headSlots.length + i;
            if (recipeIndex >= Main.getPlugin().getRecipeManager().getRecipes().size()) {
                inv.setItem(slot, null);
                continue;
            }
            HeadRecipe recipe = Main.getPlugin().getRecipeManager().getRecipes().get((recipeIndex));
            try {
                inv.setItem(slot, recipe.toGUIItem());
                recipeSlots.put(slot, recipe);
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Error while creating head " + recipe.getName());
            }
            i++;
        }
    }

    public Inventory getGUI() {
        return inv;
    }

    public int getPage() {
        return page;
    }
}
