package be.nateoncaprisun.mtcustomlootdrop.utils;

import be.nateoncaprisun.mtcustomlootdrop.MtLootdrop;
import be.nateoncaprisun.mtcustomlootdrop.nbteditor.NBTEditor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class LootdropUtils {

    public static void armorstandDrop(Location location, String type, Material mat, Player player){
        Location loc = location.add(0, 30, 0);
        ArmorStand armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        MtLootdrop.getInstance().getArmorstand().add(armorStand);
        if (!MtLootdrop.getInstance().getGeplaatst().contains(player)){
            armorStand.remove();
            return;
        }
        MtLootdrop.getInstance().getGeplaatst().remove(player);
        if (mat != Material.REDSTONE_TORCH_ON) return;
        Bukkit.broadcastMessage(Utils.color(""));
        Bukkit.broadcastMessage(Utils.color("&e&lSideLine&4&lMT &8Lootdrop Melding"));
        Bukkit.broadcastMessage(Utils.color(""));
        Bukkit.broadcastMessage(Utils.color("&8x: &f" +(int)location.getX()));
        Bukkit.broadcastMessage(Utils.color("&8y: &f" +(int)location.getY()));
        Bukkit.broadcastMessage(Utils.color("&8z: &f" +(int)location.getZ()));
        Bukkit.broadcastMessage(Utils.color(""));
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        ItemStack loot = NBTEditor.set(new ItemStack(Material.GOLD_SPADE), "supplydrop", "mdev");
        armorStand.setHelmet(loot);
        new BukkitRunnable(){
            @Override
            public void run(){
                if (!armorStand.getLocation().subtract(0, 0.1, 0).getBlock().getType().equals(Material.AIR)){
                    armorStand.setGravity(true);
                    cancel();
                    makeDrop(armorStand.getLocation(), type);
                    armorStand.remove();
                    MtLootdrop.getInstance().getArmorstand().remove(armorStand);
                }
                Location loc = armorStand.getLocation().subtract(0, 0.05, 0);
                loc.setYaw(loc.getYaw()+1);
                armorStand.teleport(loc);
            }
        }.runTaskTimer(MtLootdrop.getInstance(), 1L, 0);
    }

    public static void makeDrop(Location loc, String type){
        Block block = loc.getBlock();
        block.setType(Material.GRAY_GLAZED_TERRACOTTA);
        MtLootdrop.getInstance().getLootdropCrate().put(block.getLocation(), type);
    }

}
