package com.postman.collection;


import java.io.IOException;
import com.google.gson.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import com.google.gson.stream.JsonWriter;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.internal.*;
import java.io.IOException;
;
public class PostmanCollectionTypeAdapter implements TypeAdapterFactory {
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        return new TypeAdapter<T>() {
            public void write(JsonWriter out, T value) throws IOException {
                try {
                    delegate.write(out, value);
                } catch (IOException e) {
                    delegate.write(out, null);
                }
            }
            public T read(JsonReader in) throws IOException {
                try {
                    return delegate.read(in);
                } catch (IOException e) {
                    //Log.w("Adapter Factory", "IOException. Value skipped");
                    in.skipValue();
                    return null;
                } catch (IllegalStateException e) {
                    //Log.w("Adapter Factory", "IllegalStateException. Value skipped");
                    in.skipValue();
                    return null;
                } catch (JsonSyntaxException e) {
                    //Log.w("Adapter Factory", "JsonSyntaxException. Value skipped");
                    in.skipValue();
                    return null;
                }
            }
        };
    }
}