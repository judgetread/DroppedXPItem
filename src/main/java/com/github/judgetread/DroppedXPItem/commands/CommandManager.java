package main.java.com.github.judgetread.DroppedXPItem.commands;

import org.bukkit.command.PluginCommand;

import main.java.com.github.judgetread.DroppedXPItem.DroppedXPItem;

public class CommandManager {

	private static DroppedXPItem plugin = DroppedXPItem.getInstance();
	private static CommandManager instance;

	public CommandManager() {
		registerCommands();
	}
	
	public static CommandManager getInstance() {
		if (instance == null) {
			instance = new CommandManager();
		}
		return instance;
	}

	private static void registerCommands() {
		PluginCommand command = plugin.getCommand("dropxpbottles");
		command.setExecutor(new MainCommand());
   }

}
