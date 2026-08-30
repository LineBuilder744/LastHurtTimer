package me.PapaCarloMarx.lasthurttimer1.client.utils;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConfigManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigManager.class);
    private static final String CONFIG_FILE_NAME = "LastHitTimerConfig.yaml";
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir().resolve(CONFIG_FILE_NAME);

    // Значения по умолчанию
    private static final int DEFAULT_TICKS_TO_WAIT = 200;
    private static final int DEFAULT_X = 5;
    private static final int DEFAULT_Y = 10;

    // Текущие значения (заполняются дефолтными, пока не загружены)
    private int ticksToWait = DEFAULT_TICKS_TO_WAIT;
    private int x = DEFAULT_X;
    private int y = DEFAULT_Y;

    private static ConfigManager instance;

    private ConfigManager() {
        load(); // загружаем при создании, но без автосохранения
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    /**
     * Загружает конфиг из файла. Если файл отсутствует или повреждён,
     * оставляет значения по умолчанию (файл НЕ создаётся автоматически).
     */
    public void load() {
        if (!Files.exists(CONFIG_PATH)) {
            LOGGER.info("Config file not found, using default values.");
            return;
        }

        try (InputStream inputStream = Files.newInputStream(CONFIG_PATH)) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);

            if (data == null) {
                LOGGER.warn("Config file is empty, using defaults.");
                return;
            }

            this.ticksToWait = getInt(data, "ticksToWait", DEFAULT_TICKS_TO_WAIT);
            this.x = getInt(data, "x", DEFAULT_X);
            this.y = getInt(data, "y", DEFAULT_Y);

            LOGGER.info("Config loaded: ticksToWait={}, x={}, y={}", ticksToWait, x, y);
        } catch (Exception e) {
            LOGGER.error("Failed to load config, using defaults.", e);
        }
    }

    /**
     * Сохраняет текущие значения в файл. При необходимости создаёт папку config.
     */
    public void save() {
        try {
            // Убедимся, что папка config существует
            Files.createDirectories(CONFIG_PATH.getParent());
        } catch (IOException e) {
            LOGGER.error("Failed to create config directory.", e);
            return;
        }

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(options);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("ticksToWait", ticksToWait);
        data.put("x", x);
        data.put("y", y);

        try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
            yaml.dump(data, writer);
            LOGGER.info("Config saved: ticksToWait={}, x={}, y={}", ticksToWait, x, y);
        } catch (IOException e) {
            LOGGER.error("Failed to save config.", e);
        }
    }

    /**
     * Перезагружает конфиг из файла (без сохранения).
     */
    public void reload() {
        load();
    }

    // --- Геттеры и сеттеры ---

    public int getTicksToWait() {
        return ticksToWait;
    }

    public void setTicksToWait(int ticksToWait) {
        this.ticksToWait = ticksToWait;
        save(); // сохраняем при изменении
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        save();
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        save();
    }

    // --- Вспомогательные методы ---

    @SuppressWarnings("unchecked")
    private int getInt(Map<String, Object> map, String key, int defaultValue) {
        Object value = map.get(key);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return defaultValue;
    }
}