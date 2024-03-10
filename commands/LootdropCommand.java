package be.nateoncaprisun.mtcustomlootdrop.commands;

import be.nateoncaprisun.mtcustomlootdrop.MtLootdrop;
import be.nateoncaprisun.mtcustomlootdrop.menus.EditGui;
import be.nateoncaprisun.mtcustomlootdrop.nbteditor.NBTEditor;
import be.nateoncaprisun.mtcustomlootdrop.utils.ItemBuilder;
import be.nateoncaprisun.mtcustomlootdrop.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LootdropCommand implements CommandExecutor {

    private MtLootdrop main;

    public LootdropCommand(MtLootdrop main){
        this.main = main;
        main.getCommand("supplydrop").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player player = (Player)sender;
        if (!player.hasPermission("supplydrop.command")) {
            player.sendMessage(Utils.color("&cJe mist de permissie supplydrop.command!"));
            return false;
        }
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sendHelp(player);
            return true;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("create")) {
            String type = args[1];
            ConfigurationSection lootSection = MtLootdrop.getInstance().getLootFile().getConfig().getConfigurationSection("lootdrop");
            if (lootSection == null) {
                MtLootdrop.getInstance().getLootFile().getConfig().set("lootdrop." + type + ".loot", new ItemStack(Material.DIRT));
                MtLootdrop.getInstance().getLootFile().saveConfig();
                player.sendMessage(Utils.color("&2De lootdrop &a" + type + " &2is aangemaakt!"));
                return true;
            }
            if (lootSection.contains(type)) {
                player.sendMessage(Utils.color("&cDeze lootdrop bestaat al!"));
                return true;
            }
            List<ItemStack> li = new ArrayList<>();
            li.add(new ItemStack(Material.DIRT));
            li.add(new ItemStack(Material.GRAVEL));
            MtLootdrop.getInstance().getLootFile().getConfig().set("lootdrop." + type + ".loot", li);
            MtLootdrop.getInstance().getLootFile().saveConfig();
            player.sendMessage(Utils.color("&2De lootdrop &a" + type + " &2is aangemaakt!"));
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
            String type = args[1];
            ConfigurationSection lootSection = MtLootdrop.getInstance().getLootFile().getConfig().getConfigurationSection("lootdrop");
            if (lootSection == null) {
                player.sendMessage(Utils.color("&cDe lootdrop &4" + type + " &cbestaat niet!"));
                return true;
            }
            if (!lootSection.contains(type)) {
                player.sendMessage(Utils.color("&cDe lootdrop &4" + type + " &cbestaat niet!"));
                return true;
            }
            lootSection.set(type, null);
            MtLootdrop.getInstance().getLootFile().saveConfig();
            player.sendMessage(Utils.color("&cDe lootdrop &4" + type + " &cis verwijdert!"));
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("edit")) {
            String type = args[1];
            ConfigurationSection lootSection = MtLootdrop.getInstance().getLootFile().getConfig().getConfigurationSection("lootdrop");
            if (lootSection == null) {
                player.sendMessage(Utils.color("&cDe lootdrop &4" + type + " &cbestaat niet!"));
                return true;
            }
            if (!lootSection.contains(type)) {
                player.sendMessage(Utils.color("&cDe lootdrop &4" + type + " &cbestaat niet!"));
                return true;
            }
            new EditGui(player, 1, type);
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("get")) {
            String type = args[1];
            ConfigurationSection lootSection = MtLootdrop.getInstance().getLootFile().getConfig().getConfigurationSection("lootdrop");
            if (lootSection == null) {
                player.sendMessage(Utils.color("&cDe lootdrop &4" + type + " &cbestaat niet!"));
                return true;
            }
            if (!lootSection.contains(type)) {
                player.sendMessage(Utils.color("&cDe lootdrop &4" + type + " &cbestaat niet!"));
                return true;
            }
            ItemStack flare = (new ItemBuilder(Material.REDSTONE_TORCH_ON, Integer.valueOf(1))).setColoredName("&2" + type + " &2Supplydrop Flare").toItemStack();
            ItemStack flareDrop = NBTEditor.set(flare, type + "", "lootdrop");
            player.sendMessage(type);
            player.getInventory().addItem(flareDrop);
        }
        return false;
    }

    public void sendHelp(Player player){
        player.sendMessage(Utils.color("&6&m--------------------------"));
        player.sendMessage(Utils.color("&eSupplydrop by NateOnCaprisun"));
        player.sendMessage(Utils.color("&6&m--------------------------"));
        player.sendMessage(Utils.color("&e/supplydrop &6create <type> &7- &6Maak een lootdrop type aan!"));
        player.sendMessage(Utils.color("&e/supplydrop &6delete <type> &7- &6Verwijder een lootdrop!"));
        player.sendMessage(Utils.color("&e/supplydrop &6get <type> &7- &6Krijg een lootdrop flare!"));
        player.sendMessage(Utils.color("&e/supplydrop &6edit <type> &7- &6Edit de loot van een bepaalde type lootdrop!"));
        player.sendMessage(Utils.color("&6&m--------------------------"));
    }

}
