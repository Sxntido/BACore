package org.github.sxntido.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ActionBarAPI {
    private static Class<?> craftPlayer;

    private static Class<?> chatPacket;

    private static Class<?> chatComponentText;

    private static Class<?> chatBaseComponent;

    private static Class<?> chatMessageType;

    private static Class<?> packet;

    private static Method getHandle;

    private static Constructor<?> constructChatPacket;

    private static Constructor<?> constructChatText;

    private static boolean invoked;

    private static int versionId = Integer.parseInt(Bukkit.getBukkitVersion().split("-")[0].replace(".", "#").split("#")[1]);

    private String message;

    public ActionBarAPI() {
        if (!invoked) {
            try {
                craftPlayer = SetReflection.getCraftClass("entity.CraftPlayer");
                chatPacket = SetReflection.getNMSClass("PacketPlayOutChat");
                packet = SetReflection.getNMSClass("Packet");
                chatBaseComponent = SetReflection.getNMSClass("IChatBaseComponent");
                chatComponentText = SetReflection.getNMSClass("ChatComponentText");
                if (versionId >= 12) {
                    chatMessageType = SetReflection.getNMSClass("ChatMessageType");
                    constructChatPacket = chatPacket.getConstructor(new Class[] { chatBaseComponent });
                } else {
                    constructChatPacket = chatPacket.getConstructor(new Class[] { chatBaseComponent, byte.class });
                }
                constructChatText = chatComponentText.getConstructor(new Class[] { String.class });
                getHandle = craftPlayer.getDeclaredMethod("getHandle", new Class[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            invoked = true;
        }
    }

    public ActionBarAPI setMessage(Object message) {
        this.message = String.valueOf(message);
        return this;
    }

    public ActionBarAPI send(Player... players) {
        try {
            byte b;
            int i;
            Player[] arrayOfPlayer;
            for (i = (arrayOfPlayer = players).length, b = 0; b < i; ) {
                Player player = arrayOfPlayer[b];
                Object text = constructChatText.newInstance(new Object[] { this.message });
                Object data = null;
                if (versionId >= 12) {
                    Object data1 = constructChatPacket.newInstance(new Object[] { text });
                    Field field = data1.getClass().getDeclaredField("b");
                    field.setAccessible(true);
                    field.set(data1, SetReflection.getField(chatMessageType.getDeclaredField("GAME_INFO")).get((Object)null));
                } else {
                    data = constructChatPacket.newInstance(new Object[] { text, Byte.valueOf((byte)2) });
                }
                Object handle = getHandle.invoke(player, new Object[0]);
                Object connection = SetReflection.getValue(handle, "playerConnection");
                Method send = SetReflection.getMethod(connection, "sendPacket", new Class[] { packet });
                send.invoke(connection, new Object[] { data });
                b++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
}
