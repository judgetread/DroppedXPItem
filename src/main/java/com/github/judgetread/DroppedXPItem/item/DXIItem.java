package main.java.com.github.judgetread.DroppedXPItem.item;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import main.java.com.github.judgetread.DroppedXPItem.config.Config;
import main.java.com.github.judgetread.DroppedXPItem.nbt.NBTItem;
import main.java.com.github.judgetread.DroppedXPItem.utils.StrUtils;

public class DXIItem {

	private ItemStack itemStack;
	private ItemMeta itemMeta;
	
	public DXIItem(String playerName, int droppedXP) {
		
		this.itemStack = new ItemStack(Config.getInstance().getItemMaterial());
		this.itemStack.setAmount(Config.getInstance().getItemAmount());
		
		this.itemMeta = this.itemStack.getItemMeta();
		this.itemMeta.setDisplayName(StrUtils.convertText(Config.getInstance().getItemDisplayName(), playerName, droppedXP));
		this.itemMeta.setLore(StrUtils.convertText(Config.getInstance().getItemLore(), playerName, droppedXP));
		
		for(ItemFlag f :ItemFlag.values()) { itemMeta.addItemFlags(f);}
		
		this.itemStack.setItemMeta(itemMeta);
		setXPLevels(droppedXP);
		setDropperName(playerName);
		
	}
	
	private void setXPLevels(int xpLevels) {
		NBTItem nbtI = new NBTItem(this.itemStack);
		nbtI.setInteger("DXB", xpLevels);
		this.itemStack = nbtI.getItem();
	}

	private void setDropperName(String name) {
		NBTItem nbtI = new NBTItem(this.itemStack);
		nbtI.setString("DXB_PLAYER", name);
		this.itemStack = nbtI.getItem();
	}
	
	public ItemStack getDxbItemStack() {
		return itemStack;
	}
	

}
