package carpet.commands;

import carpet.CarpetSettings;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

import carpet.helpers.DataHelper;

import javax.annotation.Nullable;
import java.util.*;

public class CommandData extends CommandCarpetBase{
    //temporary implementation of /data, only get player pos and dim

    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "data";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    /**
     * Gets the usage string for the command.
     */
    public String getUsage(ICommandSender sender)
    {
        return "Usage: data get <player>";
    }

    /**
     * Callback for when the command is executed
     *
     * @param server The server instance
     * @param sender The sender who executed the command
     * @param args The arguments that were passed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException{
        if (!command_enabled("commandData",sender)) return;
        if (args.length == 3 && "entity".equalsIgnoreCase(args[1])) {
            notifyCommandListener(sender, this, DataHelper.process_player_info(server, sender, args[2]));
        }
        if (args.length == 4 && "Pos".equalsIgnoreCase(args[3])){
            notifyCommandListener(sender,this, DataHelper.process_player_pos(server, sender, args[2]));
        }
        if (args.length == 4 && "Dimension".equalsIgnoreCase(args[3])){
            notifyCommandListener(sender,this, DataHelper.process_player_dim(server,sender, args[2]));
        }
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos){
        if (!CarpetSettings.commandData){
            notifyCommandListener(sender, this, "Command is disabled in carpet settings");;
        }
        if (args.length == 1){
            List<String> list = Arrays.asList("get");
            return list;
        }
        if (args.length == 2){
            List<String> list = Arrays.asList("entity");
            return list;
        }
        List<String> playerNameList = getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        if (args.length == 3 && "entity".equalsIgnoreCase(args[1])){
            return playerNameList;
        }

        return Collections.<String>emptyList();
    }
}
