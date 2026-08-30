package me.PapaCarloMarx.lasthurttimer1.client.events;

import me.PapaCarloMarx.lasthurttimer1.client.hud.TimerHUD;
import me.PapaCarloMarx.lasthurttimer1.client.hud.UpdateTimerHUD;
import net.minecraft.client.MinecraftClient;


public class HurtHandler {

    private HurtHandler() {}

    public static void register() {
        HurtEvents.PLAYER_HURT.register(HurtHandler::onPlayerHurt);
    }

    private static void onPlayerHurt(net.minecraft.entity.LivingEntity player) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {

            UpdateTimerHUD.stop();
            TimerHUD.update("0.0");
            UpdateTimerHUD.start();

        }
    }
}
