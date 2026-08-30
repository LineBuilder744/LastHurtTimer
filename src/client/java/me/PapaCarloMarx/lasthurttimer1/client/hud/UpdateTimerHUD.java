package me.PapaCarloMarx.lasthurttimer1.client.hud;

import me.PapaCarloMarx.lasthurttimer1.client.utils.ConfigManager;
import me.PapaCarloMarx.lasthurttimer1.client.utils.SoundHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class UpdateTimerHUD {
    private static int DURATION_TICKS = ConfigManager.getInstance().getTicksToWait(); // 10 секунд * 20 тиков/сек

    private static boolean running = false;
    private static int ticksElapsed = 0;

    /** Вызвать один раз при инициализации мода. */
    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!running) {
                return;
            }

            double time = Double.parseDouble(TimerHUD.getCurrentText());
            time+=0.05d;
            time = (double) Math.round(time * 100.0) / 100.0;

            String strTime = String.valueOf(time);

            if(strTime.length()<4) {
                strTime = strTime+"0";
            }
            TimerHUD.update(strTime);

            onTick(client);


            ticksElapsed++;
            if (ticksElapsed >= DURATION_TICKS) {
                stop();
                TimerHUD.update(String.valueOf((double) DURATION_TICKS / 20) + "+");
                SoundHelper.playLevelUpSound();
                // естественное завершение через 10 секунд
            }
        });
    }

    /** Запускает "цикл" заново. */
    public static void start() {
        ticksElapsed = 0;
        running = true;
    }

    /** Досрочный выход из цикла — вызвать откуда угодно. */
    public static void stop() {
        running = false;
    }

    public static boolean isRunning() {
        return running;
    }

    private static void onTick(MinecraftClient client) {
        // например: client.player.sendMessage(Text.literal("тик " + ticksElapsed), false);
    }

    public static void loadDURATION_TICKS() {
        DURATION_TICKS = ConfigManager.getInstance().getTicksToWait();
    }
}
