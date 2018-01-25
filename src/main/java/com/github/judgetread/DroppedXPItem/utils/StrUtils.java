package main.java.com.github.judgetread.DropXPBottles.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.ChatColor;

public class StrUtils {
	public static String colorize(String str) {
		return str == null ? str : ChatColor.translateAlternateColorCodes('&', str);
	}

	public static String unicodize(String str) {
		return str == null ? str : StringEscapeUtils.unescapeJava(str);
	}

	public static String convertText(String str, String playerName, int droppedXP) {
		str = str.replaceAll("@playername", playerName);
		str = str.replaceAll("@droppedxp", String.valueOf(droppedXP));
		str = colorize(str);
		str = unicodize(str);
		return str;
	}

	public static ArrayList<String> convertText(ArrayList<String> stringList, String playerName, int droppedXP) {
		ArrayList<String> convertedList = new ArrayList<String>();
		for (String str : stringList) {
			String newString = str;
			newString = newString.replaceAll("@playername", playerName);
			newString = newString.replaceAll("@droppedxp", String.valueOf(droppedXP));
			newString = colorize(newString);
			newString = unicodize(newString);
			convertedList.add(newString);
		}
		return convertedList;
	}

	public static List<String> convertText(List<String> stringList, String playerName, int droppedXP) {
		List<String> convertedList = new ArrayList<String>();
		for (String str : stringList) {
			String newString = str;
			newString = newString.replaceAll("@playername", playerName);
			newString = newString.replaceAll("@droppedxp", String.valueOf(droppedXP));
			newString = colorize(newString);
			newString = unicodize(newString);
			convertedList.add(newString);
		}
		return convertedList;
	}

}
