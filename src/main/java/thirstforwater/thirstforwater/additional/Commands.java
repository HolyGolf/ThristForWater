package thirstforwater.thirstforwater.additional;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import thirstforwater.thirstforwater.ThirstForWater;

import java.util.Objects;

public class Commands implements CommandExecutor {
private final ThirstForWater plugin = ThirstForWater.getPlugin(ThirstForWater.class);
private final events pl = new events();

@Override
public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
	GUICreator GUIcreator = new GUICreator();
	if (sender instanceof Player) {
		if (args.length == 0 || args[0].equals("help")) {
			sender.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "--------------\n" + ChatColor.GOLD + "/tfw help - help\n/tfw reload - reload plugin\n/tfw settings - GUI config settings" + ChatColor.AQUA + "" + ChatColor.BOLD + "\n--------------");
			return true;
		} else if (args[0].equals("reload")) {
			if (sender.isOp() || sender.hasPermission("Thirstforwater.tfw.reload")) {
				plugin.reloadConfig();
				plugin.getServer().getPluginManager().disablePlugin(plugin);
				plugin.getServer().getPluginManager().enablePlugin(plugin);
				sender.sendMessage(ChatColor.GOLD + "[ThirstForWater]: Plugin reloaded");
			} else {
				String txt = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("Nopermission")));
				sender.sendMessage(txt);
			}
			return true;
		} else if (args[0].equals("settings")) {
			if (sender.isOp() || sender.hasPermission("Thirstforwater.tfw.settings")) {
				GUIcreator.open(Objects.requireNonNull(((Player) sender).getPlayer()));
			} else {
				String txt = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("Nopermission")));
				sender.sendMessage(txt);
			}
			return true;
		} else if (args[0].equals("disable")) {
			if (sender.isOp() || sender.hasPermission("Thirstforwater.tfw.settings")) {
				plugin.getConfig().set("Plugin", false);
				pl.savehashmap();
				plugin.getServer().getPluginManager().disablePlugin(plugin);
				plugin.getServer().getPluginManager().enablePlugin(plugin);
				sender.sendMessage(ChatColor.RED + "ThirstForWater disabled");
			} else {
				String txt = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("Nopermission")));
				sender.sendMessage(txt);
			}
		} else if (args[0].equals("enable")) {
			if (sender.isOp() || sender.hasPermission("Thirstforwater.tfw.settings")) {
				plugin.getConfig().set("Plugin", true);
				pl.savehashmap();
				plugin.getServer().getPluginManager().disablePlugin(plugin);
				plugin.getServer().getPluginManager().enablePlugin(plugin);
				sender.sendMessage(ChatColor.RED + "ThirstForWater enabled");
			} else {
				String txt = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("Nopermission")));
				sender.sendMessage(txt);
			}
		} else {
			sender.sendMessage(ChatColor.RED + "[ThirstForWater]: What?!");
			return true;
		}
	}
	return true;
}

}
