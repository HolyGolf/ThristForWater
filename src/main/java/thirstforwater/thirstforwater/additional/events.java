package thirstforwater.thirstforwater.additional;

import com.google.common.base.Strings;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import thirstforwater.thirstforwater.ThirstForWater;

import java.util.*;

import static org.bukkit.Bukkit.getServer;
import static org.bukkit.event.block.Action.RIGHT_CLICK_AIR;

public class events implements Listener {
	private final ThirstForWater plugin = ThirstForWater.getPlugin(ThirstForWater.class);
	public static HashMap<UUID, Integer> list = new HashMap<>();
	public static HashMap<String, Integer> worlds = new HashMap<>();
	public static HashMap<UUID, Double> sprint = new HashMap<>();
	public static HashMap<String, Integer> worlds_sprint = new HashMap<>();
	public static HashMap<UUID, Double> time = new HashMap<>();
	public static int idt;
	public static int idt2;


public void thirst() {
	idt = getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if ((p.getGameMode() != GameMode.CREATIVE) && (p.getGameMode() != GameMode.SPECTATOR) && (!p.hasPermission("Thirstforwater.noThirst"))) {
				if (!sprint.containsKey(p.getUniqueId())) {
					sprint.put(p.getUniqueId(), 0.0);
					if (plugin.getConfig().getBoolean("debug") && p.hasPermission("Thirstforwater.noThirst.debug")) {
						p.sendMessage(ChatColor.GRAY + "Sprint put me");
					}
				} else {
					if (p.isSprinting()) {
						if (worlds_sprint.containsKey(p.getWorld().getName())) {
							if (p.hasPermission("Thirstforwater.tfw.vip")) {
								double spr = sprint.get(p.getUniqueId()) + 0.5;
								sprint.replace(p.getUniqueId(), spr);
								if (plugin.getConfig().getBoolean("debug") && p.hasPermission("Thirstforwater.noThirst.debug")) {
									p.sendMessage(ChatColor.GREEN + "Sprint +0.5 second");
								}
							} else {
								double spr = sprint.get(p.getUniqueId()) + 1;
								sprint.replace(p.getUniqueId(), spr);
								if (plugin.getConfig().getBoolean("debug") && p.hasPermission("Thirstforwater.noThirst.debug")) {
									p.sendMessage(ChatColor.GREEN + "Sprint +1 second");
								}
							}
						}
					} else {
						sprint.replace(p.getUniqueId(), 0.0);
						if (plugin.getConfig().getBoolean("debug") && p.hasPermission("Thirstforwater.noThirst.debug")) {
							p.sendMessage(ChatColor.GREEN + "Sprint 0, no sprinting");
						}
					}
				}
				if (!time.containsKey(p.getUniqueId())) {
					time.put(p.getUniqueId(), 0.0);
					if (plugin.getConfig().getBoolean("debug") && p.hasPermission("Thirstforwater.noThirst.debug")) {
						p.sendMessage(ChatColor.GRAY + "Time put me");
					}
				} else {
					if (worlds.containsKey(p.getWorld().getName())) {
						if (p.hasPermission("Thirstforwater.tfw.vip")) {
							Double th = time.get(p.getUniqueId()) + 0.5;
							time.replace(p.getUniqueId(), th);
							if (plugin.getConfig().getBoolean("debug") && p.hasPermission("Thirstforwater.noThirst.debug")) {
								p.sendMessage(ChatColor.GREEN + "Time +0.5");
							}
						} else {
							Double th = time.get(p.getUniqueId()) + 1.0;
							time.replace(p.getUniqueId(), th);
							if (plugin.getConfig().getBoolean("debug") && p.hasPermission("Thirstforwater.noThirst.debug")) {
								p.sendMessage(ChatColor.GREEN + "Time +1");
							}
						}
					}
				}
				if (worlds_sprint.containsKey(p.getWorld().getName()) && sprint.get(p.getUniqueId()) > 1) {
					if (time.get(p.getUniqueId()) >= worlds_sprint.get(p.getWorld().getName())) {
						int thir = list.get(p.getUniqueId()) - 1;
						list.replace(p.getUniqueId(), thir);
						time.replace(p.getUniqueId(), 0.0);
						if (plugin.getConfig().getBoolean("debug") && p.hasPermission("Thirstforwater.noThirst.debug")) {
							p.sendMessage(ChatColor.RED + "Thirst -1, sprint: " + list.get(p.getUniqueId()));
						}
					}
				} else {
					if (worlds.containsKey(p.getWorld().getName())) {
						if (time.get(p.getUniqueId()) >= worlds.get(p.getWorld().getName())) {
							int thir = list.get(p.getUniqueId()) - 1;
							list.replace(p.getUniqueId(), thir);
							time.replace(p.getUniqueId(), 0.0);
							if (plugin.getConfig().getBoolean("debug") && p.hasPermission("Thirstforwater.noThirst.debug")) {
								p.sendMessage(ChatColor.RED + "Thirst -1: " + list.get(p.getUniqueId()));
							}
						}
					}
				}
				if (plugin.getConfig().getBoolean("messages") && !plugin.getConfig().getBoolean("Actionbar")) {
					if (list.get(p.getUniqueId()) > 100) {
						String txt = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("HighWaterMessage")));
						messag(p, txt);
					} else if (list.get(p.getUniqueId()) <= 19) {
						String txt = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("LowWaterMessage")));
						messag(p, txt);
					}
				}
				if (list.get(p.getUniqueId()) <= 0) {
					if (p.getHealth() > 0 && list.get(p.getUniqueId()) <= 0) {
						p.damage(plugin.getConfig().getInt("Damage"));
					}
				}
			}
		}
	}, 0L, 20);
}


public void messag(Player p, String message) {
	p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
}

public void monitor() {
	idt2 = getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if ((p.getGameMode() != GameMode.CREATIVE) && (p.getGameMode() != GameMode.SPECTATOR) && (!p.hasPermission("Thirstforwater.noThirst")) && (worlds.containsKey(p.getWorld().getName()))) {
				if (plugin.getConfig().getBoolean("Actionbar")) {
					if (list.get(p.getUniqueId()) <= 0) {
						String message = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("Indicator_empty")));
						messag(p, message);
					} else if (list.get(p.getUniqueId()) > 100) {
						String message = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("Indicator_full")));
						messag(p, message);
					} else {
						messag(p, ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("Char_before_Indicator"))) + getProgressBar(list.get(p.getUniqueId()), 100, 20) + ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("Char_after_Indicator"))));
					}
				}
			}
		}
	}, 0L, 5L);
}

public String getProgressBar(int current, int max, int totalBars) {
	float percent = (float) current / max;
	int progressBars = (int) (totalBars * percent);
	return Strings.repeat("" + ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("Char1"))), progressBars) + Strings.repeat("" + ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("Char2"))), totalBars - progressBars);
}

@EventHandler
public void onPlayerJoin(PlayerJoinEvent player) {
	if (!list.containsKey(player.getPlayer().getUniqueId())) {
		list.put(player.getPlayer().getUniqueId(), 110);
	}
	if (player.getPlayer().isOp() || player.getPlayer().hasPermission("Thirstforwater.tfw.settings")) {
		new UpdateChecker(plugin, 84634).getVersion(version -> {
			if (!plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
				player.getPlayer().sendMessage(ChatColor.GOLD + "[ThirstForWater]: New version " + version + " available at https://www.spigotmc.org/resources/thirstforwater.84634/");
			}
		});
	}
}

@EventHandler
public void sprint(PlayerMoveEvent event) {
	if (event.getPlayer().isSprinting() && list.get(event.getPlayer().getUniqueId()) <= 19 && plugin.getConfig().getBoolean("Sprint") && (worlds.containsKey(event.getPlayer().getWorld().getName()))) {
		event.setCancelled(true);
		event.getPlayer().setSprinting(false);
	}
}

@EventHandler
public void sprinting(PlayerToggleSprintEvent event) {
	if (event.getPlayer().isSprinting() && list.get(event.getPlayer().getUniqueId()) <= 19 && plugin.getConfig().getBoolean("Sprint") && (worlds.containsKey(event.getPlayer().getWorld().getName()))) {
		event.setCancelled(true);
		event.getPlayer().setSprinting(false);
	}
}

// #sad_coding_day

@EventHandler
public void onClose(InventoryCloseEvent e){
	Player player = (Player) e.getPlayer();
	UUID playerUUID = player.getUniqueId();

	GUI.openInventories.remove(playerUUID);
}

@EventHandler
public void onQuit(PlayerQuitEvent e){
	Player player = e.getPlayer();
	UUID playerUUID = player.getUniqueId();

	GUI.openInventories.remove(playerUUID);
}

@EventHandler
public void onPlayerRespawn(PlayerRespawnEvent player){
	list.replace(player.getPlayer().getUniqueId(), 110);
	if (plugin.getConfig().getBoolean("debug") && player.getPlayer().hasPermission("Thirstforwater.noThirst.debug")) {
		player.getPlayer().sendMessage("Water restored");
	}
}

// I don't know how this works, but this works. Nice.
@EventHandler
public void onPlayerEvent(PlayerItemConsumeEvent event) {
	if (event.getItem().getType() == Material.POTION && event.getItem().hasItemMeta() && Objects.requireNonNull(event.getItem().getItemMeta()).hasDisplayName() && event.getItem().getItemMeta().hasLore() && Objects.requireNonNull(event.getItem().getItemMeta().getLore()).contains(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("WaterLore")))) && event.getItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("WaterName"))))) {
		if ((list.get(event.getPlayer().getUniqueId()) + plugin.getConfig().getInt("WaterRecoveryClearWater")) <= 110) {
			int gg = list.get(event.getPlayer().getUniqueId()) + plugin.getConfig().getInt("WaterRecoveryClearWater");
			list.replace(event.getPlayer().getUniqueId(), gg);
			if (plugin.getConfig().getBoolean("debug") && event.getPlayer().hasPermission("Thirstforwater.noThirst.debug")) {
				event.getPlayer().sendMessage("Added water, clear water");
			}
		} else {
			list.replace(event.getPlayer().getUniqueId(), 110);
			if (plugin.getConfig().getBoolean("debug") && event.getPlayer().hasPermission("Thirstforwater.noThirst.debug")) {
				event.getPlayer().sendMessage("Added water, full, clear water");
			}
		}
	} else {
		event.getItem();
		if (event.getItem().getItemMeta() != null && event.getItem().getItemMeta() instanceof PotionMeta) {
			PotionType potionType = ((PotionMeta) event.getItem().getItemMeta()).getBasePotionData().getType();
			if (potionType == PotionType.WATER) {
				Random r = new Random();
				int ran = r.nextInt(101);
				if (ran <= plugin.getConfig().getInt("Poisoning bottle chance")) {
					event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.POISON, (plugin.getConfig().getInt("Poisoning duration") * 20 ), 1));
					if (plugin.getConfig().getBoolean("debug") && event.getPlayer().hasPermission("Thirstforwater.noThirst.debug")) {
						event.getPlayer().sendMessage("Poisoning");
					}
				}
				if ((list.get(event.getPlayer().getUniqueId()) + plugin.getConfig().getInt("WaterRecoveryBottle")) <= 110) {
					int gg = list.get(event.getPlayer().getUniqueId()) + plugin.getConfig().getInt("WaterRecoveryBottle");
					list.replace(event.getPlayer().getUniqueId(), gg);
					if (plugin.getConfig().getBoolean("debug") && event.getPlayer().hasPermission("Thirstforwater.noThirst.debug")) {
						event.getPlayer().sendMessage("Added water, bottle");
					}
				} else {
					list.replace(event.getPlayer().getUniqueId(), 110);
					if (plugin.getConfig().getBoolean("debug") && event.getPlayer().hasPermission("Thirstforwater.noThirst.debug")) {
						event.getPlayer().sendMessage("Added water, full, bottle");
					}
				}
			}
		}
	}
}


public void addRecipe() {
	ItemStack water = new ItemStack(Material.POTION, 1, (byte)0);
	Material wat = water.getType();
	ItemMeta meta = water.getItemMeta();

	ArrayList<String> lore = new ArrayList<>();

	lore.add(" ");
	lore.add(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("WaterLore"))));
	lore.add(" ");
	assert meta != null;
	meta.setLore(lore);
	meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("WaterName"))));
	water.setItemMeta(meta);

	FurnaceRecipe waters = new FurnaceRecipe(water, wat);
	Bukkit.getServer().addRecipe(waters);
}

@EventHandler
public void onInteract(PlayerInteractEvent event) {
	Action action = event.getAction();
	Player player = event.getPlayer();
	if (event.getItem() == null && (action.equals(Action.RIGHT_CLICK_BLOCK) || event.getAction() == RIGHT_CLICK_AIR) && player.isSneaking()) {
		List<Block> lineOfSight = event.getPlayer().getLineOfSight(null, 5);
		for (Block b : lineOfSight) {
			if (b.getType() == Material.WATER) {
				Random r = new Random();
				int ran = r.nextInt(101);
				if (ran <= plugin.getConfig().getInt("Poisoning water chance")) {
					event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.POISON, (plugin.getConfig().getInt("Poisoning duration") * 20), 1));
					if (plugin.getConfig().getBoolean("debug") && event.getPlayer().hasPermission("Thirstforwater.noThirst.debug")) {
						event.getPlayer().sendMessage("Poisoning");
					}
				}
				if ((list.get(event.getPlayer().getUniqueId()) + (plugin.getConfig().getInt("WaterRecoveryWater"))/2) <= 110) {
					int gg = list.get(event.getPlayer().getUniqueId()) + (plugin.getConfig().getInt("WaterRecoveryWater")/2);
					list.replace(event.getPlayer().getUniqueId(), gg);
					if (plugin.getConfig().getBoolean("debug") && event.getPlayer().hasPermission("Thirstforwater.noThirst.debug")) {
						event.getPlayer().sendMessage("Added water, water");
					}
				} else {
					list.replace(event.getPlayer().getUniqueId(), 110);
					if (plugin.getConfig().getBoolean("debug") && event.getPlayer().hasPermission("Thirstforwater.noThirst.debug")) {
						event.getPlayer().sendMessage("Added water, full, water");
					}
				}
				break;
			}
		}
	}
}

public void loadhashmap(){
	if (!plugin.getConfig().getStringList("data").isEmpty()) {
		for (String rawData : plugin.getConfig().getStringList("data")) {
			String[] raw = rawData.split(":");
			list.put(UUID.fromString(raw[0]), Integer.valueOf(raw[1]));
		}
		if (plugin.getConfig().getBoolean("debug")) {
			Bukkit.broadcastMessage("Hashmap loaded");
		}
	}
}

public void loadWorlds(){
	if (!plugin.getConfig().getStringList("Enabled_Worlds").isEmpty()) {
		for (String rawData : plugin.getConfig().getStringList("Enabled_Worlds")) {
			String[] raw = rawData.split(":");
			worlds.put(raw[0], Integer.valueOf(raw[1]));
		}
		if (plugin.getConfig().getBoolean("debug")) {
			Bukkit.broadcastMessage("Hashmap Worlds loaded");
		}
	}
}

public void loadWorlds_sprint(){
	if (!plugin.getConfig().getStringList("SprintRate_Worlds").isEmpty()) {
		for (String rawData : plugin.getConfig().getStringList("SprintRate_Worlds")) {
			String[] raw = rawData.split(":");
			worlds_sprint.put(raw[0], Integer.valueOf(raw[1]));
		}
		if (plugin.getConfig().getBoolean("debug")) {
			Bukkit.broadcastMessage("Hashmap Worlds Sprint loaded");
		}
	}
}

public void savehashmap(){
	List<String> hashmapData = new ArrayList<>();
	for(UUID uuid : list.keySet()) {
		String data = uuid.toString() + ":" + list.get(uuid);
		hashmapData.add(data);
	}
	if (plugin.getConfig().getBoolean("debug")) {
		Bukkit.broadcastMessage("Hashmap saved");
	}
	plugin.reloadConfig();
	plugin.getConfig().set("data", hashmapData);
	plugin.saveConfig();
}
}