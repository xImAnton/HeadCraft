package de.ximanton.headcraft;

import de.ximanton.headcraft.command.HeadCommand;
import de.ximanton.headcraft.structure.CraftingManager;
import de.ximanton.headcraft.structure.RecipeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Scanner;

/**
 * A spigot plugin for crafting customized heads
 * thanks to Dean B for the SkullCreator library
 * @author xImAnton_
 */
public final class Main extends JavaPlugin {

    private static Main INSTANCE;

    private RecipeManager recipeManager;
    private CraftingManager craftingManager;

    public Main() {
        INSTANCE = this;
        this.recipeManager = new RecipeManager();
        this.craftingManager = new CraftingManager();
    }

    public static Main getPlugin() {
        return INSTANCE;
    }

    /**
     * Called by Bukkit on plugin enable
     * Creates a config if none exists, reads recipes from file and registers events and commands
     */
    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        getRecipeManager().reload();
        Bukkit.getPluginManager().registerEvents(craftingManager, this);
        getCommand("heads").setExecutor(new HeadCommand());
    }

    public RecipeManager getRecipeManager() {
        return recipeManager;
    }

    public CraftingManager getCraftingManager() {
        return craftingManager;
    }

    /**
     * Reads a file and returns its content as String
     * @param f the file to read
     * @return the contents of the file
     * @throws FileNotFoundException when the file is not found
     */
    public static String readFileContents(File f) throws FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner s = new Scanner(f);
        while (s.hasNextLine()) {
            stringBuilder.append(s.nextLine());
        }
        s.close();
        return stringBuilder.toString();
    }

    @Override
    public void onDisable() {
    }

    /**
     * @return The chat prefix for this plugin
     */
    public static String getPrefix() {
        return ChatColor.DARK_GRAY + "» " + ChatColor.BLUE + "Head" + ChatColor.AQUA + "Craft" + ChatColor.GRAY + ": ";
    }

    /**
     * embellishes the name of an Enum, replaces underscore with space and capitalises the name
     * @param s the Enum#toString result
     * @return the embellished name
     */
    public static String getNiceName(String s) {
        String oldName = s.replace("_", " ").toLowerCase();
        StringBuilder newName = new StringBuilder();
        Scanner lineScan = new Scanner(oldName);
        while (lineScan.hasNext()) {
            String word = lineScan.next();
            newName.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }
        return newName.toString();
    }
}
