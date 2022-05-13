package carpet.commands;

import carpet.CarpetSettings;
import carpet.utils.Messenger;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandSurvivalCarpet extends CommandCarpetBase{
    /**
     * Gets the name of the command
     */
    @Override
    public String getName() {
        return "survivalcarpet";
    }

    /**
     * Gets the usage string for the command.
     *
     * @param sender
     */
    @Override
    public String getUsage(ICommandSender sender) {
        return "/survivalcarpet <rule> <value>";
    }

    /**
     * Callback for when the command is executed
     *
     * @param server
     * @param sender
     * @param args
     */
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!command_enabled("commandSurvivalCarpet", sender)) return;

        List<String> list = Arrays.asList(CarpetSettings.survivalCarpetList.split(","));

        if (args.length == 1 && !"list".equalsIgnoreCase(args[0])) {
            throw new WrongUsageException(getUsage(sender));
        }

        if (args.length == 1 && "list".equalsIgnoreCase(args[0])) {
            msg(sender, Messenger.m(null, CarpetSettings.survivalCarpetList));
        }

        if (args.length == 2 && list.contains(args[0])) {
            boolean success = CarpetSettings.set(args[0], args[1]);
            if (!success) {
                throw new WrongUsageException(getUsage(sender));
            }
            msg(sender, Messenger.m(null, "w " + args[0] + " now set to: ", "y " + CarpetSettings.get(args[0])));
        }

        if (args.length == 2 && !list.contains(args[0])) {
            msg(sender, Messenger.m(null, "r rule " + args[0] + " is not in survivalCarpetList"));
        }
    }

    /**
     * Get a list of options for when the user presses the TAB key
     *
     * @param server
     * @param sender
     * @param args
     * @param targetPos
     */
    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (!CarpetSettings.commandSurvivalCarpet) {
            return Collections.emptyList();
        }

        if (args.length == 1) {
            String string1 = CarpetSettings.survivalCarpetList;
            string1 += ",list";

            return getListOfStringsMatchingLastWord(args, string1.split(","));
        }

        if (args.length == 2) {
            return getListOfStringsMatchingLastWord(args, "true", "false");
        }

        return Collections.emptyList();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
