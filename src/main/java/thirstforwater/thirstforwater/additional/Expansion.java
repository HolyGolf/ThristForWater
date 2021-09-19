package thirstforwater.thirstforwater.additional;
import com.google.common.base.Strings;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import thirstforwater.thirstforwater.ThirstForWater;

import java.util.Objects;

public class Expansion extends PlaceholderExpansion {
private final ThirstForWater plugin;

public Expansion(ThirstForWater plugin){
	this.plugin = plugin;
}

@Override
public boolean canRegister(){
	return true;
}

@Override
public @NotNull String getAuthor(){
	return "HolyGolf";
}


@Override
public @NotNull String getIdentifier(){
	return "thirst";
}


@Override
public @NotNull String getVersion(){
	return "2.0.2";
}

@Override
public String onPlaceholderRequest(Player player, @NotNull String identifier){

	if(player == null){
		return "";
	}
	if(identifier.equals("percents")){
		if (events.list.get(player.getUniqueId()) > 100) {
			return "100";
		} else if (events.list.get(player.getUniqueId()) <= 0) {
			return "0";
		} else {
			return events.list.get(player.getUniqueId()).toString();
		}
	}
	if(identifier.equals("status")) {
		if (events.list.get(player.getUniqueId()) >= 100) {
			return "You are not thirsty";
		} else if (events.list.get(player.getUniqueId()) < 100 && events.list.get(player.getUniqueId()) >= 70) {
			return "Light thirst";
		} else if (events.list.get(player.getUniqueId()) < 70 && events.list.get(player.getUniqueId()) >= 40) {
			return "You are thirsty";
		} else if (events.list.get(player.getUniqueId()) < 40 && events.list.get(player.getUniqueId()) >= 10) {
			return "Intense thirst";
		} else if (events.list.get(player.getUniqueId()) < 10) {
			return "Drink urgently!";
		}
	}
	if(identifier.equals("indicator1")) {
		if (events.list.get(player.getUniqueId()) > 100) {
			return ChatColor.DARK_BLUE + "--------------------";
		} else if (events.list.get(player.getUniqueId()) <= 0) {
			return ChatColor.DARK_RED + "--------------------";
		} else if (events.list.get(player.getUniqueId()) <= 19) {
			return getProgressBar(events.list.get(player.getUniqueId()), 100, 20, '-', '-', ChatColor.RED, ChatColor.DARK_GRAY);
		} else {
			return getProgressBar(events.list.get(player.getUniqueId()), 100, 20, '-', '-', ChatColor.BLUE, ChatColor.DARK_GRAY);
		}
	}
	if(identifier.equals("indicator2")) {
		if (events.list.get(player.getUniqueId()) > 100) {
			return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("PlaceHolder_Indicator2_Indicator_full")));
		} else if (events.list.get(player.getUniqueId()) <= 0) {
			return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("PlaceHolder_Indicator2_Indicator_empty")));
		} else {
			return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("PlaceHolder_Indicator2_Char_before_Indicator"))) + getProgressBar2(events.list.get(player.getUniqueId()), 100, 20) + ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("PlaceHolder_Indicator2_Char_after_Indicator")));
		}
	}

	return null;
}
public String getProgressBar(int current, int max, int totalBars, char symbol1, char symbol2, ChatColor completedColor, ChatColor notCompletedColor) {
	float percent = (float) current / max;
	int progressBars = (int) (totalBars * percent);

	return Strings.repeat("" + completedColor + symbol1, progressBars)
			+ Strings.repeat("" + notCompletedColor + symbol2, totalBars - progressBars);
}
public String getProgressBar2(int current, int max, int totalBars) {
	float percent = (float) current / max;
	int progressBars = (int) (totalBars * percent);
	return Strings.repeat("" + ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("PlaceHolder_Indicator2_Char1"))), progressBars) + Strings.repeat("" + ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("PlaceHolder_Indicator2_Char2"))), totalBars - progressBars);
}
}