package thirstforwater.thirstforwater;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.SingleLineChart;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import thirstforwater.thirstforwater.additional.*;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public final class ThirstForWater extends JavaPlugin implements Listener {

@Override
public void onEnable() {
	getServer().getPluginManager().registerEvents(this, this);
	this.getLogger().info("ThirstForWater on!");
	loadConfig();
	getServer().getPluginManager().registerEvents(new events(), this);
	getServer().getPluginManager().registerEvents(new GUIListener(), this);
	Objects.requireNonNull(this.getCommand("tfw")).setExecutor(new Commands());
	LoadEvents();
	Metrics();
	if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
		new Expansion(this).register();
		this.getLogger().info("Placeholders registered.");
	}
	Logger logger = this.getLogger();
	new UpdateChecker(this, 84634).getVersion(version -> {
		if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
			logger.info("There is not a new update available.");
		} else {
			logger.info("New version " + version + " available at https://www.spigotmc.org/resources/thirstforwater.84634/");
		}
	});
}

public void Metrics(){
	int pluginId = 9655;
	Metrics metrics = new Metrics(this, pluginId);
	metrics.addCustomChart(new SingleLineChart("players", () -> Bukkit.getOnlinePlayers().size()));
	metrics.addCustomChart(new SingleLineChart("servers", () -> 1));
}


public void LoadEvents() {
	if (getConfig().getBoolean("Plugin")) {
		if (getConfig().getBoolean("CustomRecipe")) {
			new events().addRecipe();
		}
		new events().thirst();
		new events().monitor();
		new events().loadhashmap();
		new events().loadWorlds();
		new events().loadWorlds_sprint();
	}
}

@Override
public void onDisable() {
	this.getLogger().info("ThirstForWater off!");
	new events().savehashmap();
}

public void loadConfig() {
	File config = new File(getDataFolder() + File.separator + "config.yml");
	if (!config.exists()) {
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
	}
}
}
