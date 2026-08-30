package me.PapaCarloMarx.lasthurttimer1.client.events;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;

/**
 * Кастомные клиентские события, связанные с получением урона локальным игроком.
 */
public final class HurtEvents {

    private HurtEvents() {}

    /**
     * Срабатывает ПОСТФАКТУМ каждый раз, когда локальный игрок получает удар
     * (от игрока, моба, снаряда и т.д.) — вне зависимости от того, сколько
     * реального урона было нанесено (важен сам факт удара).
     */
    public static final Event<PlayerHurt> PLAYER_HURT = EventFactory.createArrayBacked(
            PlayerHurt.class,
            listeners -> (player) -> {
                for (PlayerHurt listener : listeners) {
                    listener.onPlayerHurt(player);
                }
            }
    );

    @FunctionalInterface
    public interface PlayerHurt {
        void onPlayerHurt(LivingEntity player);
    }
}
