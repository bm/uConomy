package com.brysonm.uconomy.commands;

        import com.brysonm.uconomy.BalanceUtils;
        import org.bukkit.ChatColor;
        import org.bukkit.Material;
        import org.bukkit.command.Command;
        import org.bukkit.command.CommandExecutor;
        import org.bukkit.command.CommandSender;
        import org.bukkit.entity.Player;
        import org.bukkit.inventory.ItemStack;

public class WithdrawCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {

            Player player = (Player) sender;

            if(args.length == 1) {

                if(!player.hasPermission("uconomy." + cmd.getName().toLowerCase())) {

                    player.sendMessage(ChatColor.RED + "You do not have permission to execute this command!");

                    return true;

                }

                try {

                    int amount = Integer.parseInt(args[0]);

                    double balance = BalanceUtils.getBalance(player.getUniqueId());

                    if(amount > balance) {

                        player.sendMessage(ChatColor.RED + "You tried to withdraw " + amount + " gold, but your bank only has " + balance + ".");

                        return true;

                    }

                    BalanceUtils.withdrawAmount(player.getUniqueId(), (double) amount);

                    player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, amount));

                    player.sendMessage(ChatColor.GRAY + "You have withdrawn " + amount + " gold from your bank account.");

                    return true;

                } catch(NumberFormatException ex) {

                    player.sendMessage(ChatColor.RED + "'" + args[0] + "' is not a number.");

                    return true;

                }

            } else {

                player.sendMessage(ChatColor.RED + "/withdraw <amount>");

                return true;

            }

        }
        return true;
    }

}
