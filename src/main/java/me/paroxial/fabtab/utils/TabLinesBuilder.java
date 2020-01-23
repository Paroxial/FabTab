package me.paroxial.fabtab.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import me.paroxial.fabtab.FabTab;
import me.paroxial.fabtab.tab.TabList;

public class TabLinesBuilder {
    private final String[] lines = new String[80];
    private final TabList.Version version;

    public TabLinesBuilder(Player player) {
        this.version = FabTab.getTabHandler().getTabList(player).getVersion();

        for (int i = 0; i < lines.length; i++) {
            lines[i] = "";
        }
    }

    private int getPosition(int x, int y) {
        if (x < 1 || y < 1 || x > 4 || y > 20) {
            throw new IllegalArgumentException("Illegal position: " + x + ", " + y);
        }

        return version == TabList.Version.LEGACY ? (x > 3 ? 79 : (x - 1) + ((y - 1) * 3)) : (20 * (x - 1)) + (y - 1);
    }

    public TabLinesBuilder setLine(int x, int y, String entry) {
        if (entry.length() > 32) {
            throw new IllegalArgumentException("Can't have lines longer than 32 characters");
        }

        lines[getPosition(x, y)] = ChatColor.translateAlternateColorCodes('&', entry);
        return this;
    }

    public String[] build() {
        return lines;
    }
}
