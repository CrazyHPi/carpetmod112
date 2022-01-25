package carpet.commands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandRelight extends CommandCarpetBase {
    public static final String USAGE = "/relight <radius>";

    @Override
    public String getName() {
        return "relight";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return USAGE;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!command_enabled("commandRelight", sender)) return;

        int radius = 128;
        if (args.length == 1)
            radius = parseInt(args[0]);

        int x1 = sender.getPosition().getX() - radius;
        int y1 = 0;
        int z1 = sender.getPosition().getZ() - radius;

        int x2 = sender.getPosition().getX() + radius;
        int y2 = 255;
        int z2 = sender.getPosition().getZ() + radius;

        relightArea(sender.getEntityWorld(), x1, y1, z1, x2, y2, z2);

        notifyCommandListener(sender, this,
                String.format("Recalculating light levels from: [%d %d %d] to: [%d %d %d]", x1, y1, z1, x2, y2, z2));
    }

    private void relightArea(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        for (int x = x1; x <= x2; x++) {
            for (int z = z1; z <= z2; z++) {
                for (int y = y2; y >= y1; y--) {
                    BlockPos pos = new BlockPos(x, y, z);
                    world.checkLight(pos);
                }
            }
        }
    }
}
