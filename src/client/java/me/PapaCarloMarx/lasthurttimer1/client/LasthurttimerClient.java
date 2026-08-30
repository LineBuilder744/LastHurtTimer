package me.PapaCarloMarx.lasthurttimer1.client;

import me.PapaCarloMarx.lasthurttimer1.client.commands.Commands;
import me.PapaCarloMarx.lasthurttimer1.client.events.HurtHandler;

import me.PapaCarloMarx.lasthurttimer1.client.hud.TimerHUD;
import me.PapaCarloMarx.lasthurttimer1.client.hud.UpdateTimerHUD;
import me.PapaCarloMarx.lasthurttimer1.client.utils.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.util.Identifier;

public class LasthurttimerClient implements ClientModInitializer {
    public static final String MOD_ID = "lasthurttimer1";

    @Override
    public void onInitializeClient() {

        ConfigManager configManager = ConfigManager.getInstance();
        HurtHandler.register();
        UpdateTimerHUD.register();
        Commands.register();

        HudElementRegistry.addLast(
                Identifier.of(MOD_ID, "last_hurt_info"),
                (graphics, deltaTracker) -> {
                    try {

                        int x = configManager.getX();
                        int y = configManager.getY();

                        TimerHUD.render(graphics, x, y);
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
        );

    }


}