package main.java.com.github.judgetread.DroppedXPItem.nbt;

import org.bukkit.inventory.ItemStack;

public class NBTItem extends NBTCompound{
    
    private ItemStack bukkitItem;
    
    public NBTItem(ItemStack item) {
        super(null, null);
        bukkitItem = item.clone();
    }

    public Object getCompound() {
        return NBTReflectionUtil.getItemRootNBTTagCompound(NBTReflectionUtil.getNMSItemStack(bukkitItem));
    }
    
    public void setCompound(Object tag) {
        bukkitItem = NBTReflectionUtil.getBukkitItemStack(NBTReflectionUtil.setNBTTag(tag, NBTReflectionUtil.getNMSItemStack(bukkitItem)));
    }
    
    public ItemStack getItem() {
        return bukkitItem;
    }

    @Override
    protected void setItem(ItemStack item) {
        bukkitItem = item;
    }
    
    

}