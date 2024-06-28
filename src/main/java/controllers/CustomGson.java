package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CustomGson {

    private static final Gson gson;

    static {
        gson = new GsonBuilder()
                .excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT) // Excluir campos transitorios
                .create();
    }

    public static Gson getGson() {
        return gson;
    }
}
