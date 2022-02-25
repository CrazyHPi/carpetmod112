package carpet.commands;

import carpet.CarpetSettings;
import carpet.helpers.XpCounter;
import carpet.utils.Messenger;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandXpCounter extends CommandCarpetBase{
    public String getName() {
        return "xpcounter";
    }

    public String getUsage(ICommandSender sender) {
        return "/xpcounter <player/armorstand> <reset/realtime>";
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (CarpetSettings.xpCounter == CarpetSettings.XpCounter.off) {
            msg(sender, Messenger.m(null, "w /carpet xpCounter is not enabled"));
            return;
        }

        if (args.length == 2 && "armorstand".equalsIgnoreCase(args[0]) && "reset".equalsIgnoreCase(args[1]) ) {
            XpCounter.armorStandCounter.reset(server);
        }
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (CarpetSettings.xpCounter == CarpetSettings.XpCounter.off) {
            msg(sender, Messenger.m(null, "w /carpet xpCounter is not enabled"));
            return Collections.emptyList();
        }

        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "player", "armorstand");
        }

        if (args.length == 2 && !"player".equalsIgnoreCase(args[1])) {
            List<String> list = Arrays.asList("reset");
            return list;
        }

        if (args.length == 2 && "player".equalsIgnoreCase(args[1])){
            return getListOfStringsMatchingLastWord(args, "set", "reset");
        }

        return Collections.emptyList();
    }
}
