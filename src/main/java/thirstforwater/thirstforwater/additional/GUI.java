package thirstforwater.thirstforwater.additional;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUI {
	private final UUID uuid;
	private final Inventory inv;
	private final Map<Integer, GUIAction> actions;
	public static Map<UUID, GUI> inventoriesByUUID = new HashMap<>();
	public static Map<UUID, UUID> openInventories = new HashMap<>();


public GUI(int invSize, String invName) {
	uuid = UUID.randomUUID();
	inv = Bukkit.createInventory(null, invSize, invName);
	actions = new HashMap<>();
	inventoriesByUUID.put(getUuid(), this);
}

public UUID getUuid() {
	return uuid;
}

public Inventory getInventory() {
	return inv;
}

public interface GUIAction {
	void click(Player player);
}

public void setItem(int slot, ItemStack stack, String name, GUIAction action){
	ItemMeta meta = stack.getItemMeta();
	assert meta != null;
	meta.setDisplayName(name);
	stack.setItemMeta(meta);
	inv.setItem(slot, stack);
	if (action != null){
		actions.put(slot, action);
	}
}

	public void open(Player p){
	p.openInventory(inv);
	openInventories.put(p.getUniqueId(), getUuid());
}

public static Map<UUID, GUI> getInventoriesByUUID() {
	return inventoriesByUUID;
}

public static Map<UUID, UUID> getOpenInventories() {
	return openInventories;
}

public Map<Integer, GUIAction> getActions() {
	return actions;
}

public void delete(){
	for (Player p : Bukkit.getOnlinePlayers()){
		UUID u = openInventories.get(p.getUniqueId());
		if (u.equals(getUuid())){
			p.closeInventory();
		}
	}
	inventoriesByUUID.remove(getUuid());
}

}
