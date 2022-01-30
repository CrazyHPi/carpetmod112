package carpet.helpers;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

import static net.minecraft.command.CommandBase.getPlayer;

public class DataHelper {
    //kinda dumb
    public static String process_player_info (MinecraftServer server, ICommandSender sender, String name) throws CommandException{
        EntityPlayer ePlayer = getPlayer(server, sender, name);
        String processed_info = name + " has the following entity data: {";
        processed_info += String.format("Pos: [%.5fd, %.5fd, %.5fd], ",ePlayer.posX, ePlayer.posY, ePlayer.posZ);
        processed_info += String.format("Dimension: %d, ", ePlayer.getEntityWorld().provider.getDimensionType().getId());
        processed_info += "UselessBumper: \"end data command\"}";
        return processed_info;
    }

    public static String process_player_pos (MinecraftServer server, ICommandSender sender, String name) throws CommandException{
        EntityPlayer e = getPlayer(server, sender, name);
        return String.format("%s has the following entity data: [%.5fd, %.5fd, %.5fd]",name, e.posX, e.posY, e.posZ);
    }

    public static String process_player_dim (MinecraftServer server, ICommandSender sender, String name) throws CommandException{
        EntityPlayer e = getPlayer(server, sender, name);
        return String.format("%s has the following entity data: %d",name, e.getEntityWorld().provider.getDimensionType().getId());
    }
}
