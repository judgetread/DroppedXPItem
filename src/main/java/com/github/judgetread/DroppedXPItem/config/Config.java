package main.java.com.github.judgetread.DroppedXPItem.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;

/** @formatter:off */
public class Config extends AutoUpdateConfigLoader {

	private static Config instance;

	private Config() {
		super("config.yml");
		validate();
	}

	public static Config getInstance() {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}

	@Override
	protected boolean validateKeys() {
		List<String> reason = new ArrayList<String>();
		
		if(config.getInt("Settings.Item.Amount") < 1 || config.getInt("Settings.Item.Amount") > 64 ) {
			reason.add("Settings.Item.Amount: Invalid amount entered. Must be between 1 & 64");
		}
		
		if(!validateMaterial(config.getString("Settings.Item.Material"))) {reason.add("Settings.Item.Material: Invalid material name entered.");}
		
		boolean foundSound = false;
			for(Sound s :Sound.values()) {
				if(s.toString().equalsIgnoreCase(config.getString("Settings.Sounds.Sound"))) {
					foundSound = true;
				}
			}
		if(!foundSound) {reason.add("Settings.Sounds.Sound: Invalid sound name entered.");}
		
		
		return noErrorsInConfig(reason);
	}
	
	private boolean validateMaterial(String material) {
		boolean foundMaterial = false;
		for(Material m :Material.values()) {
			if(m.toString().equalsIgnoreCase(material)) {
				foundMaterial = true;
			}
		}
		return foundMaterial;
	}
	
	@Override
	protected void loadKeys() {
	}
	

	public Boolean getEnabled() { return config.getBoolean("Settings.Enabled", true);}
	public Boolean getShowCustomName() { return config.getBoolean("Settings.Show Custom Name", true); }
	public String getCustomNameText() { return config.getString("Settings.Custom Name Text","DropXPBottle");}	
	public Boolean getAwardXPOnPickup() { return config.getBoolean("Settings.Award XP on pickup", true); }
	public Integer getMiniumXP() { return config.getInt("Settings.Minimum XP", 1);}
	public Integer getMaximumXP() { return config.getInt("Settings.Maximum XP", -1);}
	
	public Material getItemMaterial() { return Material.valueOf(config.getString("Settings.Item.Material", "EXP_BOTTLE"));}
	public Integer getItemAmount() { return config.getInt("Settings.Item.Amount", 1);}
	public String getItemDisplayName() { return config.getString("Settings.Item.Display Name","");}	
	public List<String> getItemLore() { return config.getStringList("Settings.Item.Lore");}	
	
	public Boolean getSoundEnabled() { return config.getBoolean("Settings.Sounds.Enabled", true); }
	public Sound getSound() { return Sound.valueOf(config.getString("Settings.Sounds.Sound", "ENTITY_GHAST_SCREAM")); }
	public float getSoundVolume() { return (float) config.getDouble("Settings.Sounds.Volume", 1.0);}
	public float getSoundPitch() { return (float) config.getDouble("Settings.Sounds.Pitch", 1.0);}
	
	public Boolean getChatEnabled() { return config.getBoolean("Settings.Chat.Enabled");}
	public List<String> getChatMessage() { return config.getStringList("Settings.Chat.XP Awarded Message");}
	
}

	