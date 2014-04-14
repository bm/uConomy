package com.brysonm.uconomy;

import com.brysonm.uconomy.commands.*;

import org.bukkit.plugin.java.JavaPlugin;


public class uConomy extends JavaPlugin {

    private static uConomy instance;

    private static YMLFactory.YML balancesYML;

    private static YMLFactory.YML salesYML;

    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        balancesYML = YMLFactory.buildYML("balances", this);
        salesYML = YMLFactory.buildYML("sales", this);
        getCommand("balance").setExecutor(new BalanceCommand());
        getCommand("deposit").setExecutor(new DepositCommand());
        getCommand("withdraw").setExecutor(new WithdrawCommand());
        getCommand("sell").setExecutor(new SellCommand());
        getCommand("buy").setExecutor(new BuyCommand());
        getCommand("price").setExecutor(new PriceCommand());

        if(!getConfig().getBoolean("migrated")) {

            SaleUtils.migrateUUIDs();

            getConfig().set("migrated", true);

            saveConfig();

        }

        SaleUtils.loadSales();
    }

    public void onDisable() {
        balancesYML.saveConfig();
        salesYML.saveConfig();
    }

    public static uConomy getInstance() {
        return instance;
    }

    public static YMLFactory.YML getBalancesYML() {
        return balancesYML;
    }

    public static YMLFactory.YML getSalesYML() {
        return salesYML;
    }
}
