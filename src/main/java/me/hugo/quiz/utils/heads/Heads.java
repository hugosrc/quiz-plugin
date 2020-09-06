package me.hugo.quiz.utils.heads;

import org.bukkit.inventory.ItemStack;

public enum Heads {

    QUESTION("YmFkYzA0OGE3Y2U3OGY3ZGFkNzJhMDdkYTI3ZDg1YzA5MTY4ODFlNTUyMmVlZWQxZTNkYWYyMTdhMzhjMWEifX19","question");

    private ItemStack item;
    private String idTag;
    private String prefix = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv";

    private Heads(String texture, String id) {
        item = CustomSkull.createSkull(prefix + texture);
        idTag = id;
    }

    public ItemStack getItemStack() {
        return item;
    }

    public String getName() {
        return idTag;
    }

}
