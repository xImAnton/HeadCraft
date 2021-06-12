package de.ximanton.headcraft.structure;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.ximanton.headcraft.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * manages all registered crafting recipes
 */
public class RecipeManager {

    private final ArrayList<HeadRecipe> recipes = new ArrayList<>();

    /**
     * reloads the custom added heads from the recipes.json file
     */
    private void reloadCustomHeads() {
        File recipeFile = new File(Main.getPlugin().getDataFolder(), "recipes.json");
        if (!recipeFile.exists()) {
            try {
                recipeFile.createNewFile();
                FileWriter writer = new FileWriter(recipeFile.getPath());
                writer.write("[]");
                writer.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        String json = null;
        try {
            json = Main.readFileContents(recipeFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        recipes.clear();
        JsonArray jsonObject = new JsonParser().parse(json).getAsJsonArray();
        for (JsonElement jsonElement : jsonObject) {
            JsonObject recipe = jsonElement.getAsJsonObject();
            HeadRecipe headRecipe = new HeadRecipe(recipe.get("result").getAsString(), recipe.get("name").getAsString());
            for (Map.Entry<String, JsonElement> ingredient : recipe.get("ingredients").getAsJsonObject().entrySet()) {
                headRecipe.addIngredient(ingredient.getKey(), ingredient.getValue().getAsInt());
            }
            recipes.add(headRecipe);
        }
    }

    /**
     * reloads all player heads of all players that have been online on the server
     */
    private void reloadPlayerHeads() {
        for (OfflinePlayer player : Bukkit.getServer().getOfflinePlayers()) {
            if (player.getName() != null) {
                PlayerHeadRecipe recipe = new PlayerHeadRecipe(player);
                recipes.add(recipe);
            }
        }
    }

    /**
     * public access to reloadCustomHeads and reloadPlayerHeads
     */
    public void reload() {
        reloadCustomHeads();
        reloadPlayerHeads();
    }

    public ArrayList<HeadRecipe> getRecipes() {
        return recipes;
    }
}
