package de.ximanton.headcraft.structure;

import com.google.common.collect.Iterables;
import de.ximanton.headcraft.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Manages all open crafting interfaces to assign each inventory click to a players interface
 */
public class CraftingManager implements Listener {

    /**
     * All open inventorys mapped to their players
     */
    private HashMap<Player, PlayerCraftingInterface> inventorys;

    public CraftingManager() {
        inventorys = new HashMap<>();
    }

    /**
     * @param player the player to get the crafting interface for
     * @return the players crafting interface or null if none is opened
     */
    public PlayerCraftingInterface getByPlayer(Player player) {
        return inventorys.getOrDefault(player, null);
    }

    /**
     * creates and registers a new crafting interface for the given player
     * @param player the player to assign to the interface
     */
    public void newCraftingInventory(Player player) {
        if (inventorys.containsKey(player)) return;

        PlayerCraftingInterface inv = new PlayerCraftingInterface();
        inventorys.put(player, inv);
        player.openInventory(inv.getGUI());
    }

    /**
     * used to remove the players inventory on inventory close
     * @param e InventoryClickEvent, called by Bukkit
     */
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (getByPlayer((Player) e.getPlayer()) != null) {
            inventorys.remove((Player) e.getPlayer());
        }
    }

    /**
     * used to process clicks in an crafting inventory
     * @param e InventoryClickEvent, called by Bukkit
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) Iterables.getFirst(e.getViewers(), null);
        // check if the player has an open crafting interface
        PlayerCraftingInterface inv = getByPlayer(player);
        if (inv != null) {
            int slot = e.getRawSlot();
            if (slot > 53) return;
            // cancel event to not let players take items out
            e.setCancelled(true);
            // close item clicked
            if (slot == 49) {
                player.closeInventory();
            }
            // right next page item clicked
            else if (slot == 53) {
                int maxPage = (int) Math.ceil(Main.getPlugin().getRecipeManager().getRecipes().size() / PlayerCraftingInterface.headSlots.length + 1);
                if (inv.getPage() >= maxPage) return;
                inv.setPage(inv.getPage() + 1);
                inv.reloadPage();
                player.sendMessage(Main.getPrefix() + Main.getPlugin().getMessages().formatPageChanged(inv.getPage()));
            }
            // left next page item clicked
            else if (slot == 45) {
                if (inv.getPage() < 2) return;
                inv.setPage(inv.getPage() - 1);
                inv.reloadPage();
                player.sendMessage(Main.getPrefix() + Main.getPlugin().getMessages().formatPageChanged(inv.getPage()));
            }
            // when a craftable head is clicked
            else if (isHeadSlot(slot)) {
                // get the recipe for the clicked slot
                HeadRecipe recipe = inv.getRecipeFromSlot(slot);
                if (recipe == null) {
                    return;
                }
                // check if a player has all the items to craft the head
                AtomicBoolean hasItems = new AtomicBoolean(true);
                recipe.getIngredients().forEach((Material ingredient, Integer count) -> {
                    if (!player.getInventory().contains(ingredient, count)) {
                        hasItems.set(false);
                    }
                });
                // if player has all items, craft it
                if (hasItems.get()) {
                    removeItems(player, recipe);
                    player.sendMessage(Main.getPrefix() + Main.getPlugin().getMessages().formatCraftingSuccess(recipe.getName()));
                    try {
                        player.getInventory().addItem(recipe.toInvItem());
                    } catch (StringIndexOutOfBoundsException t) {
                        player.sendMessage(ChatColor.RED + "Error while creating this Head!");
                    }
                } else {
                    player.sendMessage(Main.getPrefix() + Main.getPlugin().getMessages().getNotEnoughItems());
                }
            }
        }
    }

    /**
     * removes all items from a player that are required for the given head recipe
     * @param player the player to remove the items from
     * @param recipe the recipe that indicates the ingredients
     */
    private void removeItems(Player player, HeadRecipe recipe) {
        HashMap<Material, Integer> matsToRemove = recipe.getIngredients();
        for (ItemStack itemStack : player.getInventory()) {
            if (itemStack == null) continue;
            if (matsToRemove.containsKey(itemStack.getType())) {
                int toRemove = matsToRemove.get(itemStack.getType());
                if (itemStack.getAmount() == toRemove) {
                    itemStack.setAmount(0);
                }
                else if (itemStack.getAmount() < toRemove) {
                    int amount = itemStack.getAmount();
                    itemStack.setAmount(0);
                    matsToRemove.put(itemStack.getType(), matsToRemove.get(itemStack.getType()) - amount);
                }
                else if (itemStack.getAmount() > toRemove) {
                    itemStack.setAmount(itemStack.getAmount() - matsToRemove.get(itemStack.getType()));
                }
            }
        }
    }

    /**
     * check if a clicked inventory slot is a head slot
     * @param slot the slot to check
     * @return whether the clicked slot is a head slot or not
     */
    private static boolean isHeadSlot(int slot) {
        boolean contains = false;
        for (int i : PlayerCraftingInterface.headSlots) {
            if (i == slot) {
                contains = true;
                break;
            }
        }
        return contains;
    }
}
