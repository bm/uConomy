package com.brysonm.uconomy;

import org.bukkit.entity.Player;

public class uConomyAPI {

    public static double getBalance(Player player) {

        double balance = 0;

        try {

            balance = BalanceUtils.getBalance(player.getUniqueId());

        } catch(Exception ex) {

            System.out.println("uConomy is not enabled on your server.");

        }

        return balance;

    }

    public static void deposit(Player player, double amount) {

        try {

            BalanceUtils.depositAmount(player.getUniqueId(), amount);

        } catch(Exception ex) {

            System.out.println("uConomy is not enabled on your server.");

        }

    }

    public static void withdraw(Player player, double amount) {

        try {

            BalanceUtils.withdrawAmount(player.getUniqueId(), amount);

        } catch(Exception ex) {

            System.out.println("uConomy is not enabled on your server.");

        }

    }

}
