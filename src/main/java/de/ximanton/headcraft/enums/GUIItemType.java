package de.ximanton.headcraft.enums;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public enum GUIItemType {
    BORDER,
    ARROW_RIGHT,
    ARROW_LEFT,
    CLOSE;

    /**
     * converts the enum values to ItemStack
     * @return the item to be used in the gui
     */
    public ItemStack toItemStack() {
        ItemStack item = null;
        ItemMeta meta = null;
        switch (this) {
            case BORDER:
                item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                meta = item.getItemMeta();
                meta.setDisplayName(" ");
                break;
            case CLOSE:
                item = new ItemStack(Material.BARRIER);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.RED + "Schließen");
                meta.setLore(Arrays.asList(" ", ChatColor.YELLOW + "Klicke, um dieses Fenster zu schließen"));
                break;
            case ARROW_LEFT:
                item = new ItemStack(Material.ARROW);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.GRAY + "Nach links");
                meta.setLore(Arrays.asList(" ", ChatColor.YELLOW + "Klicke, um weiter nach links zu gehen"));
                break;
            case ARROW_RIGHT:
                item = new ItemStack(Material.ARROW);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.GRAY + "Nach rechts");
                meta.setLore(Arrays.asList(" ", ChatColor.YELLOW + "Klicke, um weiter nach rechts zu gehen"));
                break;
        }
        if (meta != null) item.setItemMeta(meta);
        return item;
    }
}
