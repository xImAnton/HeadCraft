package de.ximanton.headcraft.structure;


import de.ximanton.headcraft.Main;
import de.ximanton.headcraft.SkullCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * represents a recipe for a head with ingredients
 */
public class HeadRecipe {

    /**
     * hash 64 string, the result
     */
    private String result;
    private HashMap<Material, Integer> ingredients;
    private String name;

    public HeadRecipe(String result, String name) {
        this.result = result;
        this.ingredients = new HashMap<>();
        this.name = name;
    }

    /**
     * converts the head recipe to a item that is shown up in the crafting inventory
     * shows all ingredients in the lore
     * @return the ItemStack to add to the gui
     */
    public ItemStack toGUIItem() {
        ItemStack item = SkullCreator.itemFromBase64(getResult());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName());
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        ingredients.forEach((Material m, Integer i) -> lore.add(ChatColor.DARK_GRAY.toString() + i + "x " + ChatColor.YELLOW + Main.getNiceName(m.toString())));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * converts the head recipe to a item to be given to a player
     * @return the ItemStack to give to the player
     */
    public ItemStack toInvItem() {
        ItemStack item = SkullCreator.itemFromBase64(getResult());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + getName());
        item.setItemMeta(meta);
        return item;
    }

    public String getName() {
        return name;
    }

    public void addIngredient(String mat, int count) {
        ingredients.put(Material.valueOf(mat), count);
    }

    public String getResult() {
        return result;
    }

    public HashMap<Material, Integer> getIngredients() {
        return ingredients;
    }
}
