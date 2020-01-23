package me.paroxial.fabtab.tab;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import protocolsupport.api.ProtocolSupportAPI;
import me.paroxial.fabtab.handler.TabHandler;
import me.paroxial.fabtab.utils.DataUtil;
import me.paroxial.fabtab.utils.TabUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class TabList {
    @Getter
    private final Version version;
    private final Player player;

    @Getter
    @Setter
    private TabLines lines;
    private List<PlayerInfoData> cachedInfoList;

    public TabList(Player player, TabHandler handler) {
        this.player = player;
        this.version = ProtocolSupportAPI.getProtocolVersion(player).getId() > 5 ? Version.CURRENT : Version.LEGACY;

        List<PlayerInfoData> onlineInfoList = new ArrayList<>();

        for (Player online : handler.getPlugin().getServer().getOnlinePlayers()) {
            onlineInfoList.add(TabUtil.createInfo(online));
        }

        sendInfoPacket(player, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER, onlineInfoList);

        List<PlayerInfoData> blankInfoList = new ArrayList<>();

        for (int i = 0; i < 80; i++) {
            blankInfoList.add(TabUtil.createInfo(DataUtil.getUUIDByIndex(i), DataUtil.getTeamNameByIndex(i), DataUtil.getEntryNameByIndex(i)));
        }

        cachedInfoList = blankInfoList;

        sendInfoPacket(player, EnumWrappers.PlayerInfoAction.ADD_PLAYER, blankInfoList);
        sendInfoPacket(player, EnumWrappers.PlayerInfoAction.ADD_PLAYER, onlineInfoList);

        lines = handler.getDefaultTabLines(player);
    }

    public void update() {
        List<PlayerInfoData> lineInfoList = new ArrayList<>();
        String[] lines = this.lines.getBuilder(player).build();
        int i = 0;

        for (String line : lines) {
            PlayerInfoData cachedInfo = cachedInfoList.get(i);
            EnumWrappers.NativeGameMode gameMode = cachedInfo.getGameMode();
            WrappedGameProfile profile = cachedInfo.getProfile();
            WrappedChatComponent component;

            if (line.isEmpty()) {
                component = WrappedChatComponent.fromText(DataUtil.getEntryNameByIndex(i));
            } else {
                component = WrappedChatComponent.fromText(line);
            }

            PlayerInfoData newInfo = new PlayerInfoData(profile, 0, gameMode, component);

            if (newInfo != cachedInfo) {
                lineInfoList.add(newInfo);
            }

            i++;
        }

        cachedInfoList = lineInfoList;

        if (version == Version.CURRENT) {
            sendInfoPacket(player, EnumWrappers.PlayerInfoAction.UPDATE_DISPLAY_NAME, lineInfoList);
        } else {
            Scoreboard scoreboard = player.getScoreboard();

            for (int j = 0; j < 80; j++) {
                String teamName = DataUtil.getTeamNameByIndex(j);

                if (scoreboard.getTeam(teamName) == null) {
                    scoreboard.registerNewTeam(teamName);
                }

                Team team = scoreboard.getTeam(teamName);

                String entryName = DataUtil.getEntryNameByIndex(j);

                if (!team.hasEntry(entryName)) {
                    team.addEntry(entryName);
                }

                String entry = lines[j];
                String prefix;
                String suffix;

                if (entry.length() > 16) {
                    String section = ChatColor.RESET.toString().substring(0, 1);
                    int endIndex = entry.substring(15, 16).equals(section) ? 15 : 16;

                    prefix = entry.substring(0, endIndex);
                    suffix = ChatColor.getLastColors(prefix) + entry.substring(endIndex);
                } else {
                    prefix = entry;
                    suffix = "";
                }

                team.setPrefix(prefix);
                team.setSuffix(suffix);
            }

            player.setScoreboard(scoreboard);
        }
    }

    private void sendInfoPacket(Player player, EnumWrappers.PlayerInfoAction action, List<PlayerInfoData> data) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);

        packet.getPlayerInfoAction().write(0, action);
        packet.getPlayerInfoDataLists().write(0, data);

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public enum Version {
        LEGACY,
        CURRENT
    }
}
