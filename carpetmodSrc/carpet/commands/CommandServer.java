package carpet.commands;

import carpet.CarpetSettings;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.CommandException;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandServer extends CommandCarpetBase{

    //implement bungeecord command Tab Completions :/server

    public String getName(){
        return "server";
    }

    public String getUsage(ICommandSender sender){
        return "Usage: server <server to toggle>";
    }

    //do nothing cuz its just a tab completions
    public void execute(final MinecraftServer server, final ICommandSender sender, String[] args) throws CommandException{}

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos ){
        if (!CarpetSettings.commandServer){
            return Collections.<String>emptyList();
        }
        if (args.length == 1){
            return getListOfStringsMatchingLastWord(args, CarpetSettings.serverList.split(","));
        }
        return Collections.<String>emptyList();
    }
}
