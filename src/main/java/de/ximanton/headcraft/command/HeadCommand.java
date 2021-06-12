package de.ximanton.headcraft.command;

import de.ximanton.headcraft.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * CommandExecutor for the /head command that is used to craft heads
 */
public class HeadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.getPrefix() + Main.getPlugin().getMessages().getMustBePlayer());
            return false;
        }
        Main.getPlugin().getCraftingManager().newCraftingInventory((Player) sender);
        return true;
    }
}
