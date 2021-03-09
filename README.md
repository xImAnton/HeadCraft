# HeadCraft
A spigot plugin that lets you customize head recipes for custom heads

## What is this?

This plugin adds a new command /head. It opens a inventory in that you can craft heads. 

## Usage

This plugin has no heads by default, but you can fully customize the recipes using a config file.

When you want to craft a head type /heads in the chat. An gui opens up:

![Example HeadCraft GUI](/src/img/headcraft.png)

To craft a head, just click at it. If you have the correct items in your inventory you'll be given the head.

As you can see also all player heads of all seen players on the server are craftable.

## Adding Recipes

A recipe is notated as json:

```json
{
    "result": "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWZhNThiNGE0MzMwOWM0YWJiNGVkMzU1NDQ4NmZkMWJiOTUyY2MyNmFmMDlkZTZiYmRmNDYxZDMwNDkzMGU4MyJ9fX0=",
    "name": "Candied Apple",
    "ingredients": {
      "HONEYCOMB": 1,
      "APPLE": 1,
      "SUGAR": 1
    }
  }
```

`result` is the minecraft head data, `name` is the shown name in the interface and `ingredients` is an array of materials assigned to their amount.

An array of such objects is stored in the ./plugins/HeadCraft/recipes.json file that is created on first server start.

