package carpet.helpers;

import carpet.CarpetServer;
import carpet.pubsub.PubSubInfoProvider;
import carpet.utils.Messenger;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XpCounter {
    public static final XpCounter armorStandCounter = new XpCounter("armorStand");

    private PubSubInfoProvider<Long> pubSubProvider;
    private long totalXp = 0;
    private long startTick;
    private long startMillis;
    private String name;


    private XpCounter(String name) {
        this.name = name;
        this.totalXp = 0;
        pubSubProvider = new PubSubInfoProvider<>(CarpetServer.PUBSUB, "carpet.xpCounter." + name, 0, this::getTotalXp);
    }

    public long getTotalXp() {
        return totalXp;
    }

    public void add(MinecraftServer server, int xp) {
        if (startTick == 0){
            startTick = server.getTickCounter();
            startMillis = MinecraftServer.getCurrentTimeMillis();
        }

        this.totalXp += xp;
        pubSubProvider.publish();
    }

    public ITextComponent[] format(MinecraftServer server, boolean realTime, boolean brief){
        long ticks = Math.max(realTime ? (MinecraftServer.getCurrentTimeMillis() - startMillis) / 50 : server.getTickCounter() - startTick, 1);

        if (this.totalXp == 0) {
            return new ITextComponent[] {
                    Messenger.m(null, String.format("g No xp for %s yet", this.name))
            };
        }

        List<ITextComponent> list = new ArrayList<>();
        list.add(Messenger.m(null, String.format("w xp of %s: %d, %d/h, %.1fmin", name, totalXp, totalXp* (20 * 60 * 60) / ticks, ticks / (20.0 * 60.0))));

        return list.toArray(new ITextComponent[0]);
    }

    public void reset(MinecraftServer server) {
        totalXp = 0;
        startTick = server.getTickCounter();
        startMillis = MinecraftServer.getCurrentTimeMillis();
        pubSubProvider.publish();
    }
}
