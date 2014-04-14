package com.brysonm.uconomy.commands;

import com.brysonm.uconomy.BalanceUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DepositCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {

            Player player = (Player) sender;

            if(!player.hasPermission("uconomy." + cmd.getName().toLowerCase())) {

                player.sendMessage(ChatColor.RED + "You do not have permission to execute this command!");

                return true;

            }

            if(args.length == 1) {

                try {

                    int amount = Integer.parseInt(args[0]);

                    int count = 0;

                    for(ItemStack is : player.getInventory().getContents()) {

                        if(is != null && is.getType() == Material.GOLD_INGOT) {

                            count += is.getAmount();

                        }

                    }

                    if(amount > count) {

                        player.sendMessage(ChatColor.RED + "You tried to deposit " + amount + " gold, but you only have " + count + ".");

                        return true;

                    }

                    BalanceUtils.depositAmount(player.getUniqueId(), (double) amount);

                    player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, amount));

                    player.sendMessage(ChatColor.GRAY + "You have deposited " + amount + " gold into your bank account.");

                    return true;

                } catch(NumberFormatException ex) {

                    player.sendMessage(ChatColor.RED + "'" + args[0] + "' is not a number.");

                    return true;

                }

            } else {

                player.sendMessage(ChatColor.RED + "/deposit <amount>");

                return true;

            }

        } else {

            if(args.length == 2) {

                Player player = Bukkit.getPlayer(args[0]);

                int amount = Integer.parseInt(args[1]);

                BalanceUtils.depositAmount(player.getUniqueId(), (double) amount);

            }

        }
        return true;
    }

}
