//basically combining
//  https://bukkit.org/threads/storing-hidden-data-in-itemstacks-using-colors-persistent-no-nms.319970/
//with
//  https://gist.github.com/filoghost/f53ecb7b014c40b66bdc
//with some modifications
package m3hTools;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.bukkit.entity.Player;

import java.lang.System;
import java.util.List;

public class RC {
	private static String SEQUENCE_HEADER = "" + ChatColor.RESET + ChatColor.UNDERLINE + ChatColor.RESET;
	private static String SEQUENCE_FOOTER = "" + ChatColor.RESET + ChatColor.ITALIC + ChatColor.RESET;

	public static String NotifyColour = "" + ChatColor.RESET;

	public static long[] unitValues = { //no ms
		1000, //seconds
		60, //min
		60, //hour
		24, //day
	};
	public static String[] unitNames = {
		"millisecond",
		"second",
		"minute",
		"hour",
		"day",
	};

	public static void startCooldown(ItemStack item, long seconds) {
		startMSCooldown(item, seconds * 1000);
	}
	public static void startMSCooldown(ItemStack item, long milliseconds) {
		long endEpoch = System.currentTimeMillis() + milliseconds;
		if (hasCooldown(item)) {
			removeCooldown(item);
		}
		addCooldown(item, endEpoch);
	}
	public static boolean cooldownFinished(ItemStack item) {
		if (!hasCooldown(item)) { return true; }
		return getRemainingCooldown(item) == 0;
	}
	public static void notifyCooldown(Player player, ItemStack item) {
		long cd = getRemainingCooldown(item);
		int i;
		for (i = 0; i < unitValues.length; i++) {
			if (cd / unitValues[i] < 1) {
				break;
			}
			cd = cd / unitValues[i];
		}
		player.sendMessage(NotifyColour + Long.toString(cd) + " " + unitNames[i] + (
					cd == 1 ? "": "s"
					) + " until you can use \"" + item.getItemMeta().getDisplayName() + NotifyColour + "\" again");
	}
	public static boolean doEverything(Player player, ItemStack item, long seconds) {
		return doEverythingMS(player, item, seconds * 1000);
	}
	public static boolean doEverythingMS(Player player, ItemStack item, long milliseconds) {
		if (cooldownFinished(item)) {
			if (item.getAmount() > 1) { //deal with having a stackable relic base item
				ItemStack i = new ItemStack(item);
				i.setAmount(1);
				item.setAmount(item.getAmount() - 1);
				startMSCooldown(i, milliseconds);
				player.getInventory().addItem(i);
			} else {
				startMSCooldown(item, milliseconds);
			}
			return true;
		} else {
			notifyCooldown(player, item);
			return false;
		}
	}

	public static boolean hasCooldown(ItemStack item) {
		List<String> lore = item.getItemMeta().getLore();
		if (lore != null && lore.size() > 0) {
			return lore.get(0).contains(SEQUENCE_HEADER) && lore.get(0).contains(SEQUENCE_FOOTER);
		}
		return false;
	}
	public static void removeCooldown(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		String loreo = lore.get(0);

		int start = loreo.indexOf(SEQUENCE_HEADER);
		int end = loreo.indexOf(SEQUENCE_FOOTER);
		if (start < 0 || end < 0) { return; }

		loreo = loreo.substring(0, start) + loreo.substring(end + SEQUENCE_FOOTER.length()); //remove the bit between header and footer
		lore.set(0, loreo);
		meta.setLore(lore);
		item.setItemMeta(meta);
	}
	public static void addCooldown(ItemStack item, long endEpoch) { addCooldown(item, endEpoch,
			item.getItemMeta().getLore().get(0).length() //where to stick the string in
	); }
	public static void addCooldown(ItemStack item, long endEpoch, int pos) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		if (lore == null) { return; }

		String loreo = lore.get(0);

		loreo = loreo.substring(0, pos) + SEQUENCE_HEADER + longToColours(endEpoch) + SEQUENCE_FOOTER + loreo.substring(pos);

		lore.set(0, loreo);
		meta.setLore(lore);
		item.setItemMeta(meta);
	}
	public static String longToColours(long l) {
		String endEpoch = Long.toString(l);
		StringBuilder output = new StringBuilder(); //this is only here cuz intellij didn't like it being += in a loop
		for (char ch : endEpoch.toCharArray()) {
			output.append(ChatColor.COLOR_CHAR);
			output.append(ch);
		}
		return output.toString();
	}
	public static long getCooldownEpoch(ItemStack item) {
		if (!hasCooldown(item)) { return 0; }
		String loreo = item.getItemMeta().getLore().get(0);

		int start = loreo.indexOf(SEQUENCE_HEADER);
		int end = loreo.indexOf(SEQUENCE_FOOTER);
		if (start < 0 || end < 0) { return 0; }

		String cooldown = loreo.substring(start + SEQUENCE_HEADER.length(), end);
		StringBuilder endEpoch = new StringBuilder(); //this too
		for (int i = 1; i < cooldown.length(); i += 2) {
			endEpoch.append(cooldown.charAt(i));
		}
		return Long.valueOf(endEpoch.toString());
	}
	public static long getRemainingCooldown(ItemStack item) {
		long currentTime = System.currentTimeMillis();
		long endTime = getCooldownEpoch(item);

		if (currentTime > endTime) {
			return 0;
		} else {
			return endTime - currentTime;
		}
	}
}
