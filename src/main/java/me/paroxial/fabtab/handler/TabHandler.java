package me.paroxial.fabtab.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import me.paroxial.fabtab.FabTab;
import me.paroxial.fabtab.tab.TabLines;
import me.paroxial.fabtab.tab.TabList;
import me.paroxial.fabtab.tab.TabRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class TabHandler implements Listener {
    private final Map<UUID, TabList> tabLists = new HashMap<>();
    @Getter
    private final FabTab plugin;
    private TabRegistry registry;

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.getScoreboard() == plugin.getServer().getScoreboardManager().getMainScoreboard()) {
            player.setScoreboard(plugin.getServer().getScoreboardManager().getNewScoreboard());
        }

        tabLists.put(player.getUniqueId(), new TabList(player, this));
        updateTab(player);
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        tabLists.remove(event.getPlayer().getUniqueId());
    }

    public void setRegistry(TabRegistry registry) {
        if (this.registry == null) {
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }

        this.registry = registry;
    }

    public TabList getTabList(Player player) {
        return tabLists.get(player.getUniqueId());
    }

    public TabLines getDefaultTabLines(Player player) {
        return registry.getDefaultLines(player);
    }

    public TabLines getTabLines(Player player) {
        return getTabList(player).getLines();
    }

    public void setTabLines(Player player, TabLines lines) {
        getTabList(player).setLines(lines);
        updateTab(player);
    }

    public void updateTab(Player player) {
        getTabList(player).update();
    }

    public void updateAllTabs() {
        tabLists.values().forEach(TabList::update);
    }
}
