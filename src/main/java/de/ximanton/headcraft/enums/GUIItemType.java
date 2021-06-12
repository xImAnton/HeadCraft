package de.ximanton.headcraft.enums;

import de.ximanton.headcraft.Main;
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
                meta.setDisplayName(Main.getPlugin().getMessages().getClose());
                meta.setLore(Arrays.asList(" ", Main.getPlugin().getMessages().getCloseTip()));
                break;
            case ARROW_LEFT:
                item = new ItemStack(Material.ARROW);
                meta = item.getItemMeta();
                meta.setDisplayName(Main.getPlugin().getMessages().getGoLeft());
                meta.setLore(Arrays.asList(" ", Main.getPlugin().getMessages().getGoLeftTip()));
                break;
            case ARROW_RIGHT:
                item = new ItemStack(Material.ARROW);
                meta = item.getItemMeta();
                meta.setDisplayName(Main.getPlugin().getMessages().getGoRight());
                meta.setLore(Arrays.asList(" ", Main.getPlugin().getMessages().getGoRightTip()));
                break;
        }
        if (meta != null) item.setItemMeta(meta);
        return item;
    }
}
