package me.PapaCarloMarx.lasthurttimer1.client.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import me.PapaCarloMarx.lasthurttimer1.client.hud.UpdateTimerHUD;
import me.PapaCarloMarx.lasthurttimer1.client.utils.ConfigManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

/**
 * Регистрирует клиентские команды мода:
 *   /lasthurttimer setpos <x> <y>        — задать позицию HUD на экране
 *   /lasthurttimer setTicksToWait <ticks> — задать длительность таймера в тиках
 *
 * Обе команды сразу сохраняют значение в конфиг (через ConfigManager,
 * который автосохраняет при каждом сеттере) и не требуют перезахода —
 * HUD рендерится заново каждый кадр и подтягивает новые значения сам.
 */
public class Commands {

    private Commands() {}

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(
                        literal("lasthurttimer")
                                .then(literal("setpos")
                                        .then(argument("x", IntegerArgumentType.integer())
                                                .then(argument("y", IntegerArgumentType.integer())
                                                        .executes(context -> {
                                                            int x = IntegerArgumentType.getInteger(context, "x");
                                                            int y = IntegerArgumentType.getInteger(context, "y");

                                                            ConfigManager config = ConfigManager.getInstance();
                                                            config.setX(x);
                                                            config.setY(y);

                                                            sendFeedback("Позиция HUD установлена: x=" + x + ", y=" + y);
                                                            return 1;
                                                        })
                                                )
                                        )
                                )
                                .then(literal("setTicksToWait")
                                        .then(argument("ticks", IntegerArgumentType.integer(1))
                                                .executes(context -> {
                                                    int ticks = IntegerArgumentType.getInteger(context, "ticks");

                                                    ConfigManager config = ConfigManager.getInstance();
                                                    config.setTicksToWait(ticks);
                                                    UpdateTimerHUD.loadDURATION_TICKS();
                                                    sendFeedback("Длительность таймера установлена: " + ticks + " тиков");
                                                    return 1;
                                                })
                                        )
                                )
                )
        );
    }

    private static void sendFeedback(String message) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.sendMessage(Text.literal("§a[LastHurtTimer] " + message), false);
        }
    }
}