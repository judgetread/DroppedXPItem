package main.java.com.github.judgetread.DroppedXPItem;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.com.github.judgetread.DroppedXPItem.Listeners.DroppedXPItemListener;
import main.java.com.github.judgetread.DroppedXPItem.config.Config;
import main.java.com.github.judgetread.DroppedXPItem.item.DXIItem;
import main.java.com.github.judgetread.DroppedXPItem.sound.SoundPlayer;

public class DroppedXPItem extends JavaPlugin {
	
	private static DroppedXPItem instance;
	public boolean noErrorsInConfigFiles = true;
	private List<DXIItem> dxbItems;

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

	public void debug(String message) {
		final Logger logger = this.getLogger();
		new BukkitRunnable() {
			@Override
			public void run() {
				logger.info(ChatColor.translateAlternateColorCodes('&', "[Debug] " + message));
			}
		}.runTask(this);
	}

	public List<DXIItem> getDxbItems() {
		return dxbItems;
	}

	public void setDxbItems(List<DXIItem> dxbItems) {
		this.dxbItems = dxbItems;
	}
	
}
