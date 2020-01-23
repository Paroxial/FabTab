package me.paroxial.fabtab.utils;

import com.comphenix.protocol.wrappers.EnumWrappers.NativeGameMode;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

import java.util.UUID;

@UtilityClass
public class TabUtil {
    public static final String
            TEXTURE_VALUE = "eyJ0aW1lc3RhbXAiOjE1MTE3Mzk5MDc1MzksInByb2ZpbGVJZCI6IjIzZDE4YjNhN2E1NjQyM2E4NDZmZGJlNGVjYjJmNzJmIiwicHJvZmlsZU5hbWUiOiJHZW1pbml4UGxheXMiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2E1NmUyMWE5MzYyODE1ZDJiNzJiNjg5ZTc2NmZhZmQzYmVlY2U5OTRjM2QxMDI3ODg3ZjM3MmEyZjkyOWZmMyJ9fX0=",
            TEXTURE_SIGNATURE = "IOC895nkomyPq/eU8RNbWV543JMrY6we0XPyqaZ4i1mFW+wtv6GRx6fB6/N8QM+FgF9l6lqoADeij8tbJoqvmOYp4zvcE0B3zVmlH9si61V//6uAxzYTZNZUymKENI9rTv6PS9YasvnN2ybcARe0P+C9tVPE1rUcyL6PUObW9vew3yT9XVRJDuv5NEySOWHr+q+tG7xuOH5c+1h1HX+Lnmpg/lMqJvkfNbBGcVtbvcyHUCslwx0b6o03AbJ+lfPyRJ4S4VB9X0UJFSC6aGG5vGijGYatrwcCBB1HKqRVyF0AzVZ4rNmDeHGBvXDWwrYAF0K8Bny4QBHQUctJKCiYVF5hk6gkQPABxKsMDMqe3tK6Zs7riI28L1JXGxjG4EsnsG9r+bWawNrJXUJnLxD3vG4Wq7EXVBwTKt3a5SzV5MtWVHwQ66ROQCOjgIc/BHgFwQkEk01S08u1zH3PECqgcWnFyUQeq/ujIuxftz5i0NS2YiMLXAevx1jGavOl330FXaKWJ6j4RTaUVO7c8iPLo1kr4p+pcrIVdGjDSLYjI1N4R3M3EmKipcrOzqj6MPDU/qFRKYPKFcIf6Yt5IYnSUzC86piomaiks/A13YbhyIij/DWMu9tGZZgf4r1Ev/kprHJDRSM/1uwAZAkUgk0qVha/vIu8DhqtI8EGbvibM5o=";

    public static PlayerInfoData createInfo(UUID uuid, String name, String displayName) {
        NativeGameMode gameMode = NativeGameMode.SURVIVAL;
        WrappedChatComponent component = WrappedChatComponent.fromText(displayName);
        WrappedGameProfile profile = new WrappedGameProfile(uuid, name);

        profile.getProperties().put(
                "textures",
                WrappedSignedProperty.fromValues(
                        "textures",
                        TEXTURE_VALUE,
                        TEXTURE_SIGNATURE
                )
        );

        return new PlayerInfoData(profile, 0, gameMode, component);
    }

    public static PlayerInfoData createInfo(Player player) {
        NativeGameMode gameMode = NativeGameMode.fromBukkit(player.getGameMode());
        WrappedChatComponent component = WrappedChatComponent.fromText(player.getDisplayName());
        WrappedGameProfile profile = WrappedGameProfile.fromPlayer(player);

        return new PlayerInfoData(profile, player.spigot().getPing(), gameMode, component);
    }
}
