package org.example.Server.Session;

public abstract class GroupSessionFactory {
    private static GroupSession session = new GroupSessionMemoryImpl();

    public static GroupSession getGroupSession() {
        return session;
    }
}
