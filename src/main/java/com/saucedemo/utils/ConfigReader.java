package com.saucedemo.utils;

import io.github.cdimascio.dotenv.Dotenv;

public final class ConfigReader {
    private static final Dotenv DOTENV = Dotenv.configure().load();

    private ConfigReader() {
    }

    // Backwards-compatible name and clearer API for tests
    public static String get(String key) {
        return DOTENV.get(key);
    }

    public static String getProperty(String key) {
        return get(key);
    }
}
