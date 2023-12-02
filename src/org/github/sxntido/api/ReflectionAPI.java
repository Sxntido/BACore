package org.github.sxntido.api;


import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReflectionAPI {

    public static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        return getClass("net.minecraft.server." + version + "." + name);
    }

    public static Class<?> getNMSClassArray(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        return getClass("[Lnet.minecraft.server." + version + "." + name + ";");
    }

    public static Class<?> getCraftClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        return getClass("org.bukkit.craftbukkit." + version + "." + name);
    }

    public static Class<?> getBukkitClass(String name) {
        return getClass("org.bukkit." + name);
    }

    public static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") }).invoke(playerConnection, new Object[] { packet });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getFieldValue(Object obj, String name) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception exception) {
            return null;
        }
    }

    public static void setFieldValue(Object obj, String name, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception exception) {}
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Enum<?> getEnum(String enumFullName) {
        String[] x = enumFullName.split("\\.(?=[^\\.]+$)");
        if (x.length == 2) {

            String enumClassName = x[0];
            String enumName = x[1];


            Class<Enum> cl = (Class<Enum>) getClass(enumClassName);

            return Enum.valueOf(cl, enumName);
        }
        return null;
    }

    public static Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
