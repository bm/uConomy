package com.brysonm.uconomy.commands;

import com.brysonm.uconomy.ItemUtils;
import com.brysonm.uconomy.SaleUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SellCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {

            Player player = (Player) sender;

            if(!player.hasPermission("uconomy." + cmd.getName().toLowerCase())) {

                player.sendMessage(ChatColor.RED + "You do not have permission to execute this command!");

                return true;

            }

            if(args.length == 3) {

                try {

                    int amount = Integer.parseInt(args[0]);

                    if(amount <= 0) {

                        player.sendMessage(ChatColor.RED + "You must use positive integers to sell items.");

                        return true;

                    }

                    Material material = Material.matchMaterial(args[1].toUpperCase());

                    if(material == null) {

                        player.sendMessage(ChatColor.RED + "Item '" + args[1] + "' not found.");

                        return true;

                    }

                    double price = Double.parseDouble(args[2]);

                    int count = 0;

                    for(ItemStack is : player.getInventory().getContents()) {

                        if(is != null && is.getType() == material && is.getData().getData() == 0x0) {

                            count += is.getAmount();

                        }

                    }

                    if(amount > count) {

                        player.sendMessage(ChatColor.RED + "You only have " + count + " " + ItemUtils.toFriendlyName(material) + ".");

                        return true;

                    }

                    double unitPrice = price / amount;

                    for(int i = 0; i < amount; i++) {

                        SaleUtils.constructSale(player, material, unitPrice);

                    }

                    player.getInventory().removeItem(new ItemStack(material, amount));

                    player.sendMessage(ChatColor.GRAY + "You have put " + amount + " " + ItemUtils.toFriendlyName(material) + " on the market for " + price + " gold.");

                    return true;

                } catch(NumberFormatException ex) {

                    player.sendMessage(ChatColor.RED + "/sell <amount> <item> <price>");

                    return true;

                }

            } else {

                player.sendMessage(ChatColor.RED + "/sell <amount> <item> <price>");

                return true;

            }

        }
        return true;
    }

}
