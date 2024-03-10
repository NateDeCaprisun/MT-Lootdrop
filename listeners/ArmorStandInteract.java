package be.nateoncaprisun.mtcustomlootdrop.listeners;

import be.nateoncaprisun.mtcustomlootdrop.MtLootdrop;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class ArmorStandInteract implements Listener {

    private MtLootdrop main;

    public ArmorStandInteract(MtLootdrop main){
        this.main = main;
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void entityInteractEvent(PlayerInteractAtEntityEvent event){
        if (!(event.getRightClicked() instanceof ArmorStand)) return;
        if (!main.getArmorstand().contains((ArmorStand) event.getRightClicked())) return;
        event.setCancelled(true);
    }

}
