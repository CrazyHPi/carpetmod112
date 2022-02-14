package carpet.commands;

import carpet.CarpetSettings;
import carpet.helpers.TeleportHelper;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandNether extends CommandCarpetBase{
    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "nether";
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
        return "Usage: nether <x> <y> <z> OR nether here OR nether";
    }

    /**
     * Callback for when the command is executed
     *
     * @param server The server instance
     * @param sender The sender who executed the command
     * @param args The arguments that were passed
     */
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!command_enabled("commandnether",sender)) return;
        EntityPlayerMP entity = getCommandSenderAsPlayer(sender);

        //tp to fixed loc
        if (args.length<1 && entity instanceof EntityPlayerMP){
            double x = 0.5;
            double y = 128;
            double z = 0.5;

            if (entity.getEntityWorld().provider.getDimensionType().getId() != -1) {
                TeleportHelper.changeDimensionsToCoords(entity, -1, x, y, z);
            }
            else {
                entity.connection.setPlayerLocation(x, y, z, entity.rotationYaw, entity.rotationPitch);
            }
            notifyCommandListener(sender, this, "commands.tp.success.coordinates", new Object[] {entity.getName(), x, y, z});
        }

        //tp to correspond pos(*8 or /8) if player in nether/oberworld, else to same pos
        if (args.length == 1 && args[0].equalsIgnoreCase("here")){
            WorldServer worldserver = entity.getServerWorld();
            double x = entity.posX;
            double y = entity.posY;
            double z = entity.posZ;

            if (entity.getEntityWorld().provider.getDimensionType().getId() == 0) {
                x = MathHelper.clamp(entity.posX / 8.0D, worldserver.getWorldBorder().minX() + 16.0D, worldserver.getWorldBorder().maxX() - 16.0D);
                z = MathHelper.clamp(entity.posZ / 8.0D, worldserver.getWorldBorder().minZ() + 16.0D, worldserver.getWorldBorder().maxZ() - 16.0D);

                TeleportHelper.changeDimensionsToCoords(entity, -1, x, y, z);
            }
            else if (entity.getEntityWorld().provider.getDimensionType().getId() == 1) {
                TeleportHelper.changeDimensionsToCoords(entity, -1, x, y, z);
            }
            else {
                entity.connection.setPlayerLocation(x, y, z, entity.rotationYaw, entity.rotationPitch);
            }

            notifyCommandListener(sender, this, "commands.tp.success.coordinates", new Object[] {entity.getName(), x, y, z});
        }

        //tp to coords
        if (args.length == 3){
            CoordinateArg x = parseCoordinate(entity.posX, args[0], true);
            CoordinateArg y = parseCoordinate(entity.posY, args[1],-4096, 4096, false);
            CoordinateArg z = parseCoordinate(entity.posZ, args[2], true);
            CoordinateArg yaw = parseCoordinate(entity.rotationYaw, "~", false);
            CoordinateArg pitch = parseCoordinate(entity.rotationPitch, "~", false);

            TeleportHelper.changeDimensionsToPos(entity, -1, x, y, z, yaw, pitch);

            notifyCommandListener(sender, this, "commands.tp.success.coordinates", new Object[] {entity.getName(), x.getResult(), y.getResult(), z.getResult()});
        }

    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos){
        if (!CarpetSettings.commandNether){
            notifyCommandListener(sender, this, "Command is disabled in carpet settings");
        }

        if (args.length == 1){
            List<String> list = Arrays.asList("here");
            return list;
        }
        return Collections.<String>emptyList();
    }
}
