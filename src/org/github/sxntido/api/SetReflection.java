package org.github.sxntido.api;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;

public class SetReflection {
    public static Class<?> getNMSClass(String name) {
        return getClass("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().substring(23) + "." + name);
    }

    public static Class<?> getCraftClass(String name) {
        return getClass("org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().substring(23) + "." + name);
    }

    public static Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getValue(Object o, String fieldName) {
        try {
            Field field = o.getClass().getDeclaredField(fieldName);
            if (!field.isAccessible())
                field.setAccessible(true);
            return field.get(o);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Method getMethod(Object o, String methodName, Class... params) {
        try {
            Method method = o.getClass().getMethod(methodName, params);
            if (!method.isAccessible())
                method.setAccessible(true);
            return method;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Field getField(Field field) {
        field.setAccessible(true);
        return field;
    }
}