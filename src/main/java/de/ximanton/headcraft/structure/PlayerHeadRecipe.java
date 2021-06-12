package de.ximanton.headcraft.structure;

import de.ximanton.headcraft.Main;
import de.ximanton.headcraft.SkullCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * represents the head recipe for a player that has been online on the server
 */
public class PlayerHeadRecipe extends HeadRecipe {
    private OfflinePlayer player;

    public PlayerHeadRecipe(OfflinePlayer player) {
        super(null, player.getName());
        this.player = player;
        addIngredient(Material.TOTEM_OF_UNDYING.toString(), 1);
        addIngredient(Material.LEATHER_HELMET.toString(), 1);
    }

    @Override
    public ItemStack toGUIItem() throws StringIndexOutOfBoundsException {
        ItemStack item = SkullCreator.itemFromUuid(player.getUniqueId());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + getName() + "'s Head");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        getIngredients().forEach((Material m, Integer i) -> lore.add(ChatColor.DARK_GRAY.toString() + i + "x " + ChatColor.YELLOW + Main.getNiceName(m.toString())));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public ItemStack toInvItem() throws StringIndexOutOfBoundsException {
        ItemStack item = SkullCreator.itemFromUuid(player.getUniqueId());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + getName() + "'s Head");
        item.setItemMeta(meta);
        return item;
    }
}
