package me.paroxial.fabtab;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import me.paroxial.fabtab.handler.TabHandler;

public class FabTab extends JavaPlugin {

    @Getter
    private static TabHandler tabHandler;

    @Override
    public void onEnable() {
        if (getServer().getMaxPlayers() < 60) {
            getLogger().severe("Max players must be greater than or equal to 60! Disabling now...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        tabHandler = new TabHandler(this);
    }

    @Override
    public void onDisable() {
        if (tabHandler != null) {
            tabHandler = null;
        }
    }
}
