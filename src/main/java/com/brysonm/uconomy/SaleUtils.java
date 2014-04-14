package com.brysonm.uconomy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class SaleUtils {

    private static List<Sale> sales = new ArrayList<Sale>();

    public static void constructSale(Player player, Material material, double price) {

        Sale sale = new Sale(player.getUniqueId(), material, price);

        sales.add(sale);

    }

    public static void loadSales() {

        FileConfiguration config = uConomy.getSalesYML().getConfig();

        if(!config.contains("sales")) return;

        for(String key : config.getConfigurationSection("sales").getKeys(false)) {

            UUID uuid = UUID.fromString(key);

            UUID playerUUID = UUID.fromString(config.getString("sales." + key + ".player"));

            Material material = Material.getMaterial(config.getString("sales." + key + ".material"));

            double price = config.getDouble("sales." + key + ".price");

            Sale sale = new Sale(uuid, playerUUID, material, price);

            sales.add(sale);

        }

    }

    public static List<Sale> getSales(Material material, int amount, boolean isBuying) {

        List<Sale> list = new ArrayList<Sale>();

        List<Double> prices = new ArrayList<Double>();

        for(Sale sale : sales) {

            if(sale.getMaterial() == material) {

                prices.add(sale.getPrice());

            }

        }

        Collections.sort(prices);

        if(prices.isEmpty()) return list;

        if(prices.size() < amount) {

            for(int i = 0; i < prices.size(); i++) {

                list.add(null);

            }

            return list;

        }

        for(int i = 0; i < amount; i++) {

            list.add(getSale(prices.get(i)));

        }

        if(!isBuying) {

            for(Sale sale : list) {

                sales.add(sale);

            }

        }

        return list;

    }

    public static Sale getSale(double price) {

        Sale sale = null;

        for(Sale s : sales) {

            if(s.getPrice() == price) {

                sale = s;

            }

        }

        sales.remove(sale);

        return sale;

    }

    public static void buyItems(Player player, List<Sale> list) {

        for(Sale sale : list) {

            player.getInventory().addItem(new ItemStack(sale.getMaterial(), 1));

            uConomy.getSalesYML().getConfig().set("sales." + sale.getUUID().toString(), null);

            uConomy.getSalesYML().saveConfig();

            BalanceUtils.withdrawAmount(player.getUniqueId(), sale.getPrice());

            BalanceUtils.depositAmount(sale.getPlayerUUID(), sale.getPrice());

            if(Bukkit.getPlayer(sale.getPlayerUUID()) != null) {

                Bukkit.getPlayer(sale.getPlayerUUID()).sendMessage(ChatColor.GOLD + "A player has bought 1 " + ItemUtils.toFriendlyName(sale.getMaterial()) + " from you for " + sale.getPrice() + " gold. Your new balance is " + BalanceUtils.getBalance(sale.getPlayerUUID()) + ".");

            }

        }

    }

    public static void migrateUUIDs() {

        for(String name : uConomy.getBalancesYML().getConfig().getKeys(false)) {

            double balance = uConomy.getBalancesYML().getConfig().getDouble(name);

            uConomy.getBalancesYML().getConfig().set(name, null);

            uConomy.getBalancesYML().getConfig().set(UUID.fromString(name).toString(), balance);

            uConomy.getBalancesYML().saveConfig();

        }

        for(String saleId : uConomy.getSalesYML().getConfig().getConfigurationSection("sales").getKeys(false)) {

            String name = uConomy.getSalesYML().getConfig().getString("sales." + saleId + ".player");

            uConomy.getSalesYML().getConfig().set("sales." + saleId + ".player", Bukkit.getPlayer(name).getUniqueId().toString());

            uConomy.getSalesYML().saveConfig();

        }

    }

}
