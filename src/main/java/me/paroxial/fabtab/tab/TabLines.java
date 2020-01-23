package me.paroxial.fabtab.tab;

import org.bukkit.entity.Player;
import me.paroxial.fabtab.utils.TabLinesBuilder;

public interface TabLines {
    TabLinesBuilder getBuilder(Player player);
}
