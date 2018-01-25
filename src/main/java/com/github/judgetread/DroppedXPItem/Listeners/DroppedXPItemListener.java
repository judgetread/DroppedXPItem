package main.java.com.github.judgetread.DroppedXPItem.Listeners;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import main.java.com.github.judgetread.DroppedXPItem.DroppedXPItem;
import main.java.com.github.judgetread.DroppedXPItem.config.Config;
import main.java.com.github.judgetread.DroppedXPItem.item.DXIItem;
import main.java.com.github.judgetread.DroppedXPItem.nbt.NBTItem;
import main.java.com.github.judgetread.DroppedXPItem.sound.SoundPlayer;
import main.java.com.github.judgetread.DroppedXPItem.utils.StrUtils;

public class DroppedXPItemListener implements Listener {

	private static DroppedXPItem plugin = DroppedXPItem.getInstance();

	@EventHandler
	public void onDXIPlayerDeath(PlayerDeathEvent event) {
		if ((event.getEntity() instanceof Player)) {
			Player player = event.getEntity();
			int droppedEXP = event.getDroppedExp();

			if (!player.hasPermission("DropXPBottle.bypass")) {

				if (Config.getInstance().getEnabled() && droppedEXP >= Config.getInstance().getMiniumXP()) {

					if (Config.getInstance().getMaximumXP() >= 0 && droppedEXP > Config.getInstance().getMaximumXP()) {
						droppedEXP = Config.getInstance().getMaximumXP();
					}

					DXIItem dxbItem = new DXIItem(player.getName(), droppedEXP);
					Item item = player.getWorld().dropItem(player.getLocation(), dxbItem.getDxbItemStack());

					if (Config.getInstance().getShowCustomName()) {
						item.setCustomName(StrUtils.convertText(Config.getInstance().getCustomNameText(),
								player.getName(), droppedEXP));
						item.setCustomNameVisible(true);
					}

					event.setDroppedExp(0);
				}
			}
		}
	}

	@EventHandler
	public void onDXIPlayerUse(PlayerInteractEvent event) {

		if (Config.getInstance().getEnabled() && event.hasItem() && event.getPlayer().hasPermission("DropXPBottle.awardxp")) {
			if (getXPTag(event.getItem()) > 0) {
				run(event.getPlayer(), event.getItem());

				event.setCancelled(true);
				event.getPlayer().getInventory().remove(event.getItem());
			}
		}
	}

	@EventHandler
	public void onDXIPlayerPickUp(EntityPickupItemEvent event) {

		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();

			if (Config.getInstance().getEnabled() && Config.getInstance().getAwardXPOnPickup() && player.hasPermission("DropXPBottle.awardxp")) {
				if (getXPTag(event.getItem().getItemStack()) > 0) {
					run(player, event.getItem().getItemStack());

					event.getItem().setItemStack(null);
					event.setCancelled(true);
				}
			}

		}
	}

	private void run(Player player, ItemStack itemStack) {
		giveXP(player, itemStack);
		chatMessage(player, itemStack);
		sound(player);
	}

	private void giveXP(Player player, ItemStack itemStack) {
		player.giveExp(getXPTag(itemStack));
	}

	private void sound(Player player) {
		if (Config.getInstance().getEnabled() && Config.getInstance().getSoundEnabled()) {
			SoundPlayer.getInstance().playSound(player);
		}
	}

	private void chatMessage(Player player, ItemStack itemStack) {
		if (Config.getInstance().getEnabled() && Config.getInstance().getChatEnabled()) {

			for (String str : Config.getInstance().getChatMessage()) {
				player.sendMessage(StrUtils.convertText(str, getNameTag(itemStack), getXPTag(itemStack)));
			}

		}
	}

	private int getXPTag(ItemStack itemStack) {
		NBTItem nbti = new NBTItem(itemStack);
		return nbti.getInteger("DXB");
	}

	private String getNameTag(ItemStack itemStack) {
		NBTItem nbti = new NBTItem(itemStack);
		return nbti.getString("DXB_PLAYER");
	}

}
