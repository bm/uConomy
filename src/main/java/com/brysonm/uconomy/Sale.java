package com.brysonm.uconomy;

import org.bukkit.Material;

import java.util.UUID;

public class Sale {

    private UUID uuid;

    private UUID playerUUID;

    private Material material;

    private double price;

    public Sale(UUID playerUUID, Material material, double price) {

        this.playerUUID = playerUUID;

        this.material = material;

        this.price = price;

        this.uuid = UUID.randomUUID();

        this.save();

    }

    public Sale(UUID uuid, UUID playerUUID, Material material, double price) {

        this.uuid = uuid;

        this.playerUUID = playerUUID;

        this.material = material;

        this.price = price;

    }

    public UUID getUUID() {
        return uuid;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public Material getMaterial() {
        return material;
    }

    public double getPrice() {
        return price;
    }

    public void save() {

        uConomy.getSalesYML().getConfig().set("sales." + uuid.toString() + ".player", playerUUID.toString());

        uConomy.getSalesYML().getConfig().set("sales." + uuid.toString() + ".material", material.name());

        uConomy.getSalesYML().getConfig().set("sales." + uuid.toString() + ".price", price);

        uConomy.getSalesYML().saveConfig();

    }

}
