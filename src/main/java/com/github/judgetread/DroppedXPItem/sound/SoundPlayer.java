package main.java.com.github.judgetread.DroppedXPItem.sound;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import main.java.com.github.judgetread.DroppedXPItem.config.Config;

public class SoundPlayer {

	private static SoundPlayer instance;
	private boolean enabled;
	private Sound sound;
	private float volume;
	private float pitch;
	
	private SoundPlayer() {
		this.enabled = Config.getInstance().getSoundEnabled();
		this.sound = Config.getInstance().getSound();
		this.volume = Config.getInstance().getSoundVolume();
		this.pitch = Config.getInstance().getSoundPitch();
	}

	public static SoundPlayer getInstance() {
		if (instance == null) {
			instance = new SoundPlayer();
		}
		return instance;
	}
	
	public void playSound(Player player) {
		if(player.isOnline() && this.enabled) {
			player.playSound(player.getLocation(), this.sound, this.volume, this.pitch);
		}
	}
}
