package me.PapaCarloMarx.lasthurttimer1.client.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class TimerHUD {



    public static String getCurrentText() {
        return currentText;
    }

    private static String currentText = "-1"; // Текст по умолчанию

    // Обновляет отображаемый текст
    public static void update(String text) {
        if (text == null) text = "";
        currentText = text;
    }

    // Отрисовывает текст в заданных координатах (левый верхний угол текста)
    public static void render(DrawContext graphics, int x, int y) {
        if (currentText.isEmpty()) return; // не рисуем, если текст пуст

        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;

        // Используем Text.literal для создания текста
        graphics.drawText(textRenderer, Text.literal(currentText), x, y, 0xFFFFFFFF, false);
    }

    // Вспомогательный метод для получения ширины текущего текста (для выравнивания)
    public static int getTextWidth() {
        if (currentText.isEmpty()) return 0;
        return MinecraftClient.getInstance().textRenderer.getWidth(currentText);
    }
}