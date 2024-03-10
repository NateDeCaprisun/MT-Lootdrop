package be.nateoncaprisun.mtcustomlootdrop;

import be.nateoncaprisun.mtcustomlootdrop.commands.LootdropCommand;
import be.nateoncaprisun.mtcustomlootdrop.listeners.ArmorStandInteract;
import be.nateoncaprisun.mtcustomlootdrop.listeners.EditGuiClickListener;
import be.nateoncaprisun.mtcustomlootdrop.listeners.FlarePlaceListener;
import be.nateoncaprisun.mtcustomlootdrop.listeners.LootdropOpenListener;
import be.nateoncaprisun.mtcustomlootdrop.utils.ConfigurationFile;
import be.nateoncaprisun.mtcustomlootdrop.utils.Utils;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class MtLootdrop extends JavaPlugin {

    private static @Getter MtLootdrop instance;

    private @Getter ConfigurationFile lootFile;

    private @Getter HashMap<Location, String> lootdropCrate = new HashMap<>();
    private @Getter HashMap<Location, Player> Bezig = new HashMap<>();
    private @Getter ArrayList<Player> geplaatst = new ArrayList<>();
    private @Getter ArrayList<ArmorStand> armorstand = new ArrayList<>();


    @Override
    public void onEnable() {
        getLogger().info("LootDropPlugin has been enabled!");
        getLogger().info("For questions dm NateOnCaprisun/eddiefreddie");

        instance = this;

        lootFile = new ConfigurationFile(this, "loot.yml", true);
        lootFile.saveConfig();

        saveDefaultConfig();

        new LootdropCommand(this);

        new EditGuiClickListener(this);
        new FlarePlaceListener(this);
        new LootdropOpenListener(this);
        new ArmorStandInteract(this);

        String type = "dezelootdropmagnietweg";

        ConfigurationSection lootSection = MtLootdrop.getInstance().getLootFile().getConfig().getConfigurationSection("lootdrop");
        if (lootSection == null) {
            MtLootdrop.getInstance().getLootFile().getConfig().set("lootdrop." + type + ".loot", new ItemStack(Material.DIRT));
            MtLootdrop.getInstance().getLootFile().saveConfig();
            return;
        }
        if (lootSection.contains(type)) {
            return;
        }
        List<ItemStack> li = new ArrayList<>();
        li.add(new ItemStack(Material.DIRT));
        li.add(new ItemStack(Material.GRAVEL));
        MtLootdrop.getInstance().getLootFile().getConfig().set("lootdrop." + type + ".loot", li);
        MtLootdrop.getInstance().getLootFile().saveConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("LootDropPlugin has been disabled!");
        getLogger().info("For questions dm NateOnCaprisun/eddiefreddie");
    }
}
