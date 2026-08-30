package me.PapaCarloMarx.lasthurttimer1.client.mixin;

import me.PapaCarloMarx.lasthurttimer1.client.events.HurtEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "onDamaged", at = @At("HEAD"))
    private void lasthurttimer$onDamaged(DamageSource damageSource, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        MinecraftClient client = MinecraftClient.getInstance();

        // Реагируем только на удары, полученные ЛОКАЛЬНЫМ игроком.
        if (client.player != null && self == client.player) {
            HurtEvents.PLAYER_HURT.invoker().onPlayerHurt(self);
        }
    }
}

