package tk.melosh.troldehvalp.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tk.melosh.troldehvalp.Utils;

import java.util.ArrayList;
import java.util.List;


public class ItemManager {

    public static ItemStack createGun(Material material, int range, int size, int damage) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add(Utils.format_msg(String.format("&3Range: %s", range)));
        lore.add(Utils.format_msg(String.format("&4Size: %s", size)));
        lore.add(Utils.format_msg(String.format("&5Damage: %s", damage)));

        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

}
