package ru.exept.kEffects;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.*;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.*;

import java.util.*;

public class Main extends JavaPlugin implements Listener {

    private List<Player> mammonths = new ArrayList<>();

    @Override
    public void onEnable() {
        getCommand("skam").setExecutor((sender, command, label, args) -> {
            if(args.length == 1) {
                sender.sendMessage("skam");
                return true;
            }
            return true;
        });
        getCommand("skam").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
                if(sender instanceof Player) {
                    if(args.length == 1) {
                        if(args[0].equals("check")) {
                            if(mammonths.contains((Player)sender)) {
                                sender.sendMessage("mamonth containsed!");
                                return true;
                            }
                            sender.sendMessage("mammonth not founded!");
                            return true;
                        }else if(args[0].equals("menu")) {
                            Inventory inv2 = Bukkit.createInventory((InventoryHolder)null, 27, "§3Эффекты для ударов");
                            ItemStack bow1 = new ItemStack(Material.REDSTONE);
                            ItemMeta meta = bow1.getItemMeta();
                            meta.setDisplayName("§fРедстоун");
                            bow1.setItemMeta(meta);
                            inv2.setItem(4, bow1);
                            ItemStack barrier = new ItemStack(Material.BARRIER);
                            ItemMeta meta1 = barrier.getItemMeta();
                            meta1.setDisplayName("§fОчистить нахуй");
                            barrier.setItemMeta(meta1);
                            inv2.setItem(25, barrier);
                            ((Player)sender).openInventory(inv2);
                        }
                        sender.sendMessage("args skamed");
                        return true;
                    }
                    sender.sendMessage("args skamed");
                    return true;
                }
                sender.sendMessage("skam use only for players!");
                return true;
            }
        });
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll((Plugin)this);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onSkamKrealis(InventoryClickEvent e) {
        if(e.getClickedInventory() == null) return;
        if(!(e.getWhoClicked() instanceof Player)) return;
        Player clown = (Player)e.getWhoClicked();
        if(clown == null) return;
        if(!e.getClickedInventory().equals("§3Эффекты для ударов")) return;
        e.setCancelled(true);

        if(e.getCurrentItem() == null) return;
        ItemStack itemStack = e.getCurrentItem();
        if(!itemStack.hasItemMeta()) return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(!itemMeta.hasDisplayName()) return;
        if(itemMeta.getDisplayName().equals("§fРедстоун")) {
            if(mammonths.contains(clown)) {
                mammonths.remove(clown);
                clown.sendMessage("REDSTONE >> proebali, ti yje v spiske");
            }else {
                mammonths.add(clown);
                clown.sendMessage("REDSTONE >> ti added v spisok krealis");
            }
        }else if(itemMeta.getDisplayName().equals("§fОчистить нахуй")) {
            if(mammonths.contains(clown)) {
                mammonths.remove(clown);
                clown.sendMessage("CLEAR >> ti yje v spiske");
            }else {
                mammonths.add(clown);
                clown.sendMessage("CLEAR >> proebali, ti added v spisok krealis");
            }
        }
        clown.closeInventory();
    }
}
