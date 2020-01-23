package me.paroxial.fabtab.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.UUID;

@UtilityClass
public class DataUtil {
    private static final UUID[] TEAM_UUIDS = new UUID[80];
    private static final String[] TEAM_NAMES = new String[80];
    private static final String[] ENTRY_NAMES = new String[80];

    static {
        int t1 = 0;
        int t2 = 0;

        for (int i = 0; i < 80; i++) {
            // adding UUIDs
            String uuidString = "00000000-0000-0000-0000-000000000" + i;
            UUID uuid = UUID.fromString(uuidString);

            TEAM_UUIDS[i] = uuid;

            // adding team names
            String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

            TEAM_NAMES[i] = "!" + t2 + alphabet[t1];

            if (t1 >= alphabet.length - 1) {
                t1 = 0;
                t2++;
            } else {
                t1++;
            }
        }

        int i = 0;

        for (ChatColor first : ChatColor.values()) {
            if (i >= 80) {
                break;
            }

            ENTRY_NAMES[i++] = first.toString() + ChatColor.RESET;

            for (ChatColor second : ChatColor.values()) {
                if (i >= 80) {
                    break;
                }

                ENTRY_NAMES[i++] = first.toString() + second + ChatColor.RESET;

                for (ChatColor third : ChatColor.values()) {
                    if (i >= 80) {
                        break;
                    }

                    ENTRY_NAMES[i++] = first.toString() + second + third + ChatColor.RESET;
                }
            }
        }
    }

    public static UUID getUUIDByIndex(int index) {
        return TEAM_UUIDS[index];
    }

    public static String getTeamNameByIndex(int index) {
        return TEAM_NAMES[index];
    }

    public static String getEntryNameByIndex(int index) {
        return ENTRY_NAMES[index];
    }
}
