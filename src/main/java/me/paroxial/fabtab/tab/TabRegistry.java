package me.paroxial.fabtab.tab;

import org.bukkit.entity.Player;

public interface TabRegistry {
    TabLines getDefaultLines(Player player);
}
