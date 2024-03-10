package be.nateoncaprisun.mtcustomlootdrop.listeners;

import be.nateoncaprisun.mtcustomlootdrop.MtLootdrop;
import be.nateoncaprisun.mtcustomlootdrop.utils.Utils;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class LootdropOpenListener implements Listener{
    private MtLootdrop main;
    public LootdropOpenListener(MtLootdrop main){
        this.main = main;
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void armorStandInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if (block == null)
            return;
        if (!block.getType().equals(Material.GRAY_GLAZED_TERRACOTTA))
            return;
        if (!MtLootdrop.getInstance().getLootdropCrate().containsKey(block.getLocation()))
            return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!(main.getBezig().get(block.getLocation()) == player)  && main.getBezig().containsKey(block.getLocation(block.getLocation()))){
            player.sendMessage(Utils.color("&cIemand anders is deze lootdrop al aan het openen!"));
            return;
        }
        String type = MtLootdrop.getInstance().getLootdropCrate().get(block.getLocation());
        player.sendMessage(type);
        main.getBezig().put(block.getLocation(), player);
        Gui lootGui = Gui.gui()
                .title(Component.text(Utils.color("&2Lootdrop Loot")))
                .rows(6)
                .disableItemPlace()
                .create();
        ArrayList<ItemStack> lootList = (ArrayList<ItemStack>) main.getLootFile().getConfig().getList("lootdrop." + type + ".loot");
        if (lootList == null) {
            player.sendMessage(Utils.color("&cDe crate is leeg! Meld dit bij een staff lid!"));
            block.setType(Material.AIR);
            return;
        }
        for (int i = 0; i < this.main.getConfig().getInt("Loot-Max-Items"); i++) {
            Random randSlot = new Random();
            int slot = randSlot.ints(0, 53).findAny().getAsInt();
            ItemStack randomItem = lootList.get((new Random()).nextInt(lootList.size()));
            GuiItem item = new GuiItem(randomItem);
            lootGui.setItem(slot, item);
        }
        lootGui.open(player);
        new BukkitRunnable(){
            @Override
            public void run(){
                main.getBezig().remove(block.getLocation(), player);
                block.setType(Material.AIR);
            }
        }.runTaskLater(main, 20L*7);
        MtLootdrop.getInstance().getLootdropCrate().remove(block.getLocation(), type);
    }
}
