package com.bigoat.android.arch;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final class Utils {
    private static Gson gson;

    public static void putBundleValue(Bundle bundle, String key, Object value) {
        if (bundle == null || key == null || value == null) {
            return ;
        }
        if (value instanceof Byte) {
            bundle.putByte(key, (Byte) value);
        } else if (value instanceof Short) {
            bundle.putShort(key, (Short) value);
        } else if (value instanceof Character) {
            bundle.putChar(key, (Character) value);
        } else if (value instanceof String) {
            bundle.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            bundle.putBoolean(key, (Boolean) value);
        } else if (value instanceof Long) {
            bundle.putLong(key, (Long) value);
        } else if (value instanceof Integer) {
            bundle.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            bundle.putFloat(key, (Float) value);
        } else if (value instanceof Double) {
            bundle.putDouble(key, (Double) value);
        } else {
            // 考虑内存存储，不依赖Json
            bundle.putString(key, toJson(value));
        }
    }

    public static <T> Class<T> getGenericType(Class<?> clazz, int index) {
        Type type = clazz.getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalArgumentException("Class " + clazz.getSimpleName() + " is not parameterized");
        }

        Type[] typeArguments = ((ParameterizedType) type).getActualTypeArguments();
        if (index < 0 || index >= typeArguments.length) {
            throw new IllegalArgumentException("Index " + index + " is out of bounds");
        }

        @SuppressWarnings("unchecked")
        Class<T> genericClass = (Class<T>) typeArguments[index];
        return genericClass;
    }

    public static void injectBundle(Object target, Bundle bundle, Class<? extends Annotation> annotationType) {
        if (target == null || bundle == null || annotationType == null) {
            return;
        }

        Class<?> clazz = target.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                if (field.isAnnotationPresent(annotationType)) {
                    field.setAccessible(true);
                    Object value = bundle.get(field.getName());
                    if (value == null) continue;

                    Class<?> type = field.getType();
                    if (Utils.isPrimitive(type)) {
                        field.set(target, value);
                    } else {
                        try {
                            if (value instanceof String) {
                                String stringValue = (String) value;
                                field.set(target, fromJson(stringValue, type));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    // 是否基本类型包含包装类型
    public static boolean isPrimitive(Class<?> c) {
        return c.isPrimitive()
                || c == Byte.class
                || c == Short.class
                || c == Integer.class
                || c == Long.class
                || c == Float.class
                || c == Double.class
                || c == Character.class
                || c == String.class
                || c == Boolean.class;
    }

    public static <T> T fromJson(final String json, @NonNull final Class<T> type) {
        return getGson().fromJson(json, type);
    }

    public static String toJson(final Object object) {
        return getGson().toJson(object);
    }


    public static Gson getGson() {
        if (gson == null) gson = new Gson();
        return gson;
    }


}
