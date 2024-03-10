package be.nateoncaprisun.mtcustomlootdrop.listeners;

import be.nateoncaprisun.mtcustomlootdrop.MtLootdrop;
import be.nateoncaprisun.mtcustomlootdrop.menus.EditGui;
import be.nateoncaprisun.mtcustomlootdrop.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class EditGuiClickListener implements Listener {

    public MtLootdrop main;

    public EditGuiClickListener(MtLootdrop main){
        this.main = main;
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void editGuiClick(InventoryClickEvent event){
        Player player = (Player)event.getWhoClicked();
        if (!event.getInventory().getTitle().contains(Utils.color("Items -")))
            return;
        event.setCancelled(true);
        String type = event.getInventory().getTitle().split(" Items ")[0];
        player.sendMessage(type);
        ItemStack clicked = event.getCurrentItem();
        if (clicked.getType().equals(Material.AIR))
            return;
        if (clicked == null)
            return;
        int page = Integer.parseInt(event.getInventory().getItem(48).getItemMeta().getLocalizedName());
        if (clicked.getType().equals(Material.STAINED_GLASS_PANE))
            return;
        String name = clicked.getItemMeta().getDisplayName();
        ArrayList<ItemStack> lootList = (ArrayList<ItemStack>)this.main.getLootFile().getConfig().getList("lootdrop." + type + ".loot");
        if (name == null) {
            if (lootList == null) {
                lootList = new ArrayList<>();
                lootList.add(clicked);
                this.main.getLootFile().getConfig().set("lootdrop." + type + ".loot", lootList);
                this.main.getLootFile().saveConfig();
                new EditGui(player, 1, type);
                return;
            }
            if (lootList.contains(clicked)) {
                lootList.remove(clicked);
                this.main.getLootFile().getConfig().set("lootdrop." + type + ".loot", lootList);
                this.main.getLootFile().saveConfig();
                new EditGui(player, 1, type);
                return;
            }
            lootList.add(clicked);
            this.main.getLootFile().getConfig().set("lootdrop." + type + ".loot", lootList);
            this.main.getLootFile().saveConfig();
            new EditGui(player, 1, type);
            return;
        }
        if (name.equals(Utils.color("&cVolgende Pagina")))
            return;
        if (name.equals(Utils.color("&cVorige Pagina")))
            return;
        if (name.equals(Utils.color("&aVolgende Pagina"))) {
            new EditGui(player, page + 1, type);
            return;
        }
        if (name.equals(Utils.color("&aVorige Pagina"))) {
            new EditGui(player, page - 1, type);
            return;
        }
        if (lootList == null) {
            lootList = new ArrayList<>();
            lootList.add(clicked);
            this.main.getLootFile().getConfig().set("lootdrop." + type + ".loot", lootList);
            this.main.getLootFile().saveConfig();
            new EditGui(player, 1, type);
            return;
        }
        if (lootList.contains(clicked)) {
            lootList.remove(clicked);
            this.main.getLootFile().getConfig().set("lootdrop." + type + ".loot", lootList);
            this.main.getLootFile().saveConfig();
            new EditGui(player, 1, type);
            return;
        }
        lootList.add(clicked);
        this.main.getLootFile().getConfig().set("lootdrop." + type + ".loot", lootList);
        this.main.getLootFile().saveConfig();
        new EditGui(player, 1, type);
    }

}
