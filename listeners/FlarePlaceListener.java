package be.nateoncaprisun.mtcustomlootdrop.listeners;

import be.nateoncaprisun.mtcustomlootdrop.MtLootdrop;
import be.nateoncaprisun.mtcustomlootdrop.nbteditor.NBTEditor;
import be.nateoncaprisun.mtcustomlootdrop.utils.LootdropUtils;
import be.nateoncaprisun.mtcustomlootdrop.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class FlarePlaceListener implements Listener {

    private MtLootdrop main;

    public FlarePlaceListener(MtLootdrop main){
        this.main = main;
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void playerInteractEvent(BlockPlaceEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();
        if (block == null)
            return;
        if (block.getType() != Material.REDSTONE_TORCH_ON)
            return;
        ItemStack item = event.getItemInHand();
        if (!NBTEditor.contains(item, "lootdrop" ))
            return;
        event.setCancelled(true);
        String type = NBTEditor.getString(item, "lootdrop" );
        LootdropUtils.armorstandDrop(block.getLocation(), type, Material.valueOf(block.getType().toString()), player);
        if(item.getAmount() == 1) {
            item.setAmount(0);
        } else {
            item.setAmount(item.getAmount() -1);
        }
        main.getGeplaatst().add(player);
    }

}
