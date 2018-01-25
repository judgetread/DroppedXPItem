package main.java.com.github.judgetread.DroppedXPItem.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.base.Charsets;

import main.java.com.github.judgetread.DroppedXPItem.DroppedXPItem;

/**
 * Auto update config file.
 * Check current config with resource config, add and remove keys to match.
 */
abstract class AutoUpdateConfigLoader extends ConfigLoader {
	
	/* plugin instance */
	private static DroppedXPItem plugin = DroppedXPItem.getInstance();
	
    /**
     * Constructor with file path and file name
     * 
     * @param relativePath filepath of config
     * @param fileName filename of config
     */
    public AutoUpdateConfigLoader(String relativePath, String fileName) {
        super(relativePath, fileName);
    }

    /**
     * Constructor with only file name, defaults to plugin main  directory.
     * 
     * @param fileName filename of config
     */
    public AutoUpdateConfigLoader(String fileName) {
        super(fileName);
    }

    /**
     * Replace missing  keys in config or remove old keys. 
     */
    @Override
    protected void loadFile() {
        super.loadFile();
        FileConfiguration internalConfig = YamlConfiguration.loadConfiguration(getResourceAsReader(fileName));

        Set<String> configKeys = config.getKeys(true);
        Set<String> internalConfigKeys = internalConfig.getKeys(true);

        boolean needSave = false;

        Set<String> oldKeys = new HashSet<String>(configKeys);
        oldKeys.removeAll(internalConfigKeys);

        Set<String> newKeys = new HashSet<String>(internalConfigKeys);
        newKeys.removeAll(configKeys);

        if (!newKeys.isEmpty() || !oldKeys.isEmpty()) {
            needSave = true;
        }

        for (String key : oldKeys) {
            plugin.debug("Detected potentially unused key: " + key);
        }

        for (String key : newKeys) {
            plugin.debug("Adding new key: " + key + " = " + internalConfig.get(key));
            config.set(key, internalConfig.get(key));
        }

        if (needSave) {
            String output = config.saveToString();

            output = output.replace("  ", "    ");

            while (output.replaceAll("[//s]", "").startsWith("#")) {
                output = output.substring(output.indexOf('\n', output.indexOf('#')) + 1);
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(plugin.getResource(fileName)));
                LinkedHashMap<String, String> comments = new LinkedHashMap<String, String>();
                String temp = "";

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("#")) {
                        temp += line + "\n";
                    }
                    else if (line.contains(":")) {
                        line = line.substring(0, line.indexOf(":") + 1);
                        if (!temp.isEmpty()) {
                            if(comments.containsKey(line)) {
                                int index = 0;
                                while(comments.containsKey(line + index)) {
                                    index++;
                                }
                                
                                line = line + index;
                            }

                            comments.put(line, temp);
                            temp = "";
                        }
                    }
                }

                // Dump to the new one
                HashMap<String, Integer> indexed = new HashMap<String, Integer>();
                for (String key : comments.keySet()) {
                    String actualkey = key.substring(0, key.indexOf(":") + 1);

                    int index = 0;
                    if(indexed.containsKey(actualkey)) {
                        index = indexed.get(actualkey);
                    }
                    boolean isAtTop = !output.contains("\n" + actualkey);
                    index = output.indexOf((isAtTop ? "" : "\n") + actualkey, index);

                    if (index >= 0) {
                        output = output.substring(0, index) + "\n" + comments.get(key) + output.substring(isAtTop ? index : index + 1);
                        indexed.put(actualkey, index + comments.get(key).length() + actualkey.length() + 1);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            try {
                String saveName = fileName;
                if (!plugin.getConfig().getBoolean("General.Config_Update_Overwrite", true)) {
                    saveName += ".new";
                }

                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(plugin.getDataFolder(), saveName)));
                writer.write(output);
                writer.flush();
                writer.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
	public InputStreamReader getResourceAsReader(String fileName) {
		InputStream in = plugin.getResource(fileName);
		return in == null ? null : new InputStreamReader(in, Charsets.UTF_8);
	}
}