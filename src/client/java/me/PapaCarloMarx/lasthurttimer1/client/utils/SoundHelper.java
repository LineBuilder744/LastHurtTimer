package me.PapaCarloMarx.lasthurttimer1.client.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;

public class SoundHelper {
    public static void playLevelUpSound() {
        // Получаем экземпляр клиента
        MinecraftClient client = MinecraftClient.getInstance();

        // Создаём звуковой экземпляр как UI-звук (не привязан к позиции)
        PositionedSoundInstance sound = PositionedSoundInstance.ui(
                SoundEvents.UI_BUTTON_CLICK.value(), // звук
                1.0f,                             // громкость (0–1)
                1.7f                              // высота тона (0.5–2.0)
        );

        // Воспроизводим через звуковой менеджер
        client.getSoundManager().play(sound);
    }
}
