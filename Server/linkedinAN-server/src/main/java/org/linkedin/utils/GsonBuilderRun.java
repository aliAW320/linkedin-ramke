package org.linkedin.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.time.LocalDateTime;

public class GsonBuilderRun {
    public static Gson getGson(){
        GsonBuilder builder = new GsonBuilder();
        JsonDeserializer<LocalDateTime> localDateTimeDeserializer = new LocalDateTimeDeserializer();
        builder.registerTypeAdapter(LocalDateTime.class, localDateTimeDeserializer);
        return builder.create();
    }
}
