package be.nateoncaprisun.mtcustomlootdrop.menus;

import be.nateoncaprisun.mtcustomlootdrop.MtLootdrop;
import be.nateoncaprisun.mtcustomlootdrop.utils.PageUtil;
import be.nateoncaprisun.mtcustomlootdrop.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class EditGui {

    public EditGui(Player player, int page, String type) {
        ItemMeta previousMeta, nextMeta;
        Inventory editInv = Bukkit.createInventory(null, 54, Utils.color(type + " Items - " + page));
        List<ItemStack> lootList = (List<ItemStack>) MtLootdrop.getInstance().getLootFile().getConfig().getList("lootdrop." + type + ".loot");
        if (lootList == null) {
            player.openInventory(editInv);
            return;
        }
        for (int i = 45; i < 54; i++) {
            ItemStack glas = new ItemStack(Material.STAINED_GLASS_PANE, 1);
            editInv.setItem(i, glas);
        }
        ItemStack previous = new ItemStack(Material.PAPER);
        if (PageUtil.isPageValid(lootList, page - 1, 45)) {
            previousMeta = previous.getItemMeta();
            previousMeta.setDisplayName(Utils.color("&aVorige Pagina"));
        } else {
            previousMeta = previous.getItemMeta();
            previousMeta.setDisplayName(Utils.color("&cVorige Pagina"));
        }
        previousMeta.setLocalizedName(page + "");
        previous.setItemMeta(previousMeta);
        editInv.setItem(48, previous);
        ItemStack next = new ItemStack(Material.PAPER);
        if (PageUtil.isPageValid(lootList, page + 1, 46)) {
            nextMeta = previous.getItemMeta();
            nextMeta.setDisplayName(Utils.color("&aVolgende Pagina"));
        } else {
            nextMeta = previous.getItemMeta();
            nextMeta.setDisplayName(Utils.color("&cVolgende Pagina"));
        }
        next.setItemMeta(nextMeta);
        editInv.setItem(50, next);
        for (ItemStack is : PageUtil.getPageItems(lootList, page, 45))
            editInv.setItem(editInv.firstEmpty(), is);
        player.openInventory(editInv);
    }

}
