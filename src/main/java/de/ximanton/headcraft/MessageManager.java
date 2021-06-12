package de.ximanton.headcraft;

import org.bukkit.configuration.ConfigurationSection;

public class MessageManager {

    private final String mustBePlayer;
    private final String close;
    private final String closeTip;
    private final String goRight;
    private final String goRightTip;
    private final String goLeft;
    private final String goLeftTip;
    private final String pageChanged;
    private final String craftingSuccess;
    private final String notEnoughItems;

    public MessageManager(ConfigurationSection config) {
        mustBePlayer = config.getString("mustBePlayer", "You have to be a player to use HeadCraft");
        close = config.getString("close", "§cClose");
        closeTip = config.getString("closeTip", "§eClick to close this Window");
        goRight = config.getString("goRight", "§7Go Right");
        goRightTip = config.getString("goRightTip", "§eClick to go right");
        goLeft = config.getString("goLeft", "§7Go Left");
        goLeftTip = config.getString("goLeftTip", "§eClick to go left");
        pageChanged = config.getString("pageChanged", "You are now viewing §ePage %page%");
        craftingSuccess = config.getString("craftingSuccess", "You crafted a §a%head%§7!");
        notEnoughItems = config.getString("notEnoughItems", "You don't have enough Items to craft this Head!");

    }

    public String getNotEnoughItems() {
        return notEnoughItems;
    }

    public String formatCraftingSuccess(String head) {
        return craftingSuccess.replace("%head%", head);
    }

    public String formatPageChanged(int page) {
        return pageChanged.replace("%page%", String.valueOf(page));
    }

    public String getGoRight() {
        return goRight;
    }

    public String getGoRightTip() {
        return goRightTip;
    }

    public String getGoLeft() {
        return goLeft;
    }

    public String getGoLeftTip() {
        return goLeftTip;
    }

    public String getClose() {
        return close;
    }

    public String getCloseTip() {
        return closeTip;
    }

    public String getMustBePlayer() {
        return mustBePlayer;
    }
}
