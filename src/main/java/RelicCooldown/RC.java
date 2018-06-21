//basically combining
//  https://bukkit.org/threads/storing-hidden-data-in-itemstacks-using-colors-persistent-no-nms.319970/
//with
//  https://gist.github.com/filoghost/f53ecb7b014c40b66bdc
//with some modifications
package RelicCooldown;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.System;
import java.util.List;

public class RC {
	private static final String SEQUENCE_HEADER = "" + ChatColor.RESET + ChatColor.UNDERLINE + ChatColor.RESET;
	private static final String SEQUENCE_FOOTER = "" + ChatColor.RESET + ChatColor.ITALIC + ChatColor.RESET;

	public static void startCooldown(ItemStack item, long seconds) {
		long endEpoch = System.currentTimeMillis() + (seconds * 1000);
		if (hasCooldown(item)) {
			removeCooldown(item);
		}
		addCooldown(item, endEpoch);
	}
	public static boolean cooldownFinished(ItemStack item) {
		if (!hasCooldown(item)) { return true; }
		return getRemainingCooldown(item) == 0;
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
