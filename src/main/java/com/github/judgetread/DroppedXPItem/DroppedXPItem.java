package main.java.com.github.judgetread.DroppedXPItem;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import main.java.com.github.judgetread.DroppedXPItem.Listeners.DroppedXPItemListener;
import main.java.com.github.judgetread.DroppedXPItem.config.Config;
import main.java.com.github.judgetread.DroppedXPItem.sound.SoundPlayer;

public class DroppedXPItem extends JavaPlugin {
	
	private static DroppedXPItem instance;
	public boolean noErrorsInConfigFiles = true;

	@Override
	public void onEnable() {
		instance = this;

		Config.getInstance();
		if (!noErrorsInConfigFiles) {
			return;
		}

		if (Config.getInstance().getEnabled()) {
			SoundPlayer.getInstance();
			Bukkit.getPluginManager().registerEvents(new DroppedXPItemListener(), this);
		}		
	}

	public static DroppedXPItem getInstance() {
		return instance;
	}
	
}
