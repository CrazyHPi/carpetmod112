package carpet.helpers;

import net.minecraft.command.CommandBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.royawesome.jlibnoise.module.combiner.Min;

import java.util.EnumSet;
import java.util.Set;

public class TeleportHelper {

    public static void changeDimensions(EntityPlayerMP player, EntityPlayerMP target){
        // Adapted from spectator teleport code (NetHandlerPlayServer::handleSpectate)
        double x = target.posX;
        double y = target.posY;
        double z = target.posZ;
        MinecraftServer server = player.getServer();
        assert server != null;

        WorldServer worldFrom = (WorldServer) player.world;
        WorldServer worldTo = (WorldServer) target.world;
        int dimension = worldTo.provider.getDimensionType().getId();
        player.dimension = dimension;

        player.connection.sendPacket(new SPacketRespawn(dimension, worldFrom.getDifficulty(), worldFrom.getWorldInfo().getTerrainType(), player.interactionManager.getGameType()));
        server.getPlayerList().updatePermissionLevel(player);
        worldFrom.removeEntityDangerously(player);
        player.isDead = false;
        player.setLocationAndAngles(x, y, z, (float) target.rotationYaw, (float) target.rotationPitch);

        worldFrom.updateEntityWithOptionalForce(player, false);
        worldTo.spawnEntity(player);
        worldTo.updateEntityWithOptionalForce(player, false);

        player.setWorld(worldTo);
        server.getPlayerList().preparePlayer(player, worldFrom);

        player.setPositionAndUpdate(x, y, z);
        player.interactionManager.setWorld(worldTo);
        server.getPlayerList().updateTimeAndWeatherForPlayer(player, worldTo);
        server.getPlayerList().syncPlayerInventory(player);
    }

    //Which dimension the player is in (-1 = the Nether, 0 = normal world, 1 = the End)
    //this one can process relative coords(~ ~ ~)
    public static void changeDimensionsToPos(EntityPlayerMP player, int targetDimension, CommandBase.CoordinateArg x, CommandBase.CoordinateArg y, CommandBase.CoordinateArg z, CommandBase.CoordinateArg yaw, CommandBase.CoordinateArg pitch){

        Set<SPacketPlayerPosLook.EnumFlags> set = EnumSet.<SPacketPlayerPosLook.EnumFlags>noneOf(SPacketPlayerPosLook.EnumFlags.class);

        if (x.isRelative())
        {
            set.add(SPacketPlayerPosLook.EnumFlags.X);
        }

        if (y.isRelative())
        {
            set.add(SPacketPlayerPosLook.EnumFlags.Y);
        }

        if (z.isRelative())
        {
            set.add(SPacketPlayerPosLook.EnumFlags.Z);
        }

        //process same dim tp
        if (player.getEntityWorld().provider.getDimensionType().getId() == targetDimension){
            player.connection.setPlayerLocation(x.getAmount(), y.getAmount(), z.getAmount(), player.rotationYaw, player.rotationPitch, set);
            return;
        }

        MinecraftServer server = player.getServer();
        assert server != null;
        WorldServer worldFrom = (WorldServer) player.world;
        WorldServer worldTo = server.getWorld(targetDimension);

        double x1 = x.getResult();
        double y1 = y.getResult();
        double z1 = z.getResult();

        player.dismountRidingEntity();
        //kill and respawn player entity to notify client to change dim
        //idk why do this
        //but adapted from spectator teleport code (NetHandlerPlayServer::handleSpectate)
        player.dimension = targetDimension;
        player.connection.sendPacket(new SPacketRespawn(player.dimension, worldFrom.getDifficulty(), worldFrom.getWorldInfo().getTerrainType(), player.interactionManager.getGameType()));
        server.getPlayerList().updatePermissionLevel(player);
        worldFrom.removeEntityDangerously(player);
        worldFrom.getChunk(player.chunkCoordX, player.chunkCoordZ).removeEntityAtIndex(player, player.chunkCoordY);
        player.isDead = false;
        player.setLocationAndAngles(x1, y1, z1, 0, 0);

        worldTo.spawnEntity(player);
        worldTo.updateEntityWithOptionalForce(player, false);

        player.setWorld(worldTo);
        server.getPlayerList().preparePlayer(player, worldFrom);

        player.setPositionAndUpdate(x1, y1, z1);
        player.interactionManager.setWorld(worldTo);
        server.getPlayerList().updateTimeAndWeatherForPlayer(player, worldTo);
        server.getPlayerList().syncPlayerInventory(player);
    }

    //directly to coords
    public static void changeDimensionsToCoords(EntityPlayerMP player, int targetDimension, double x, double y, double z){
        MinecraftServer server = player.getServer();
        assert server != null;
        WorldServer worldFrom = (WorldServer) player.world;
        WorldServer worldTo = server.getWorld(targetDimension);

        player.dismountRidingEntity();
        //kill and respawn player entity to notify client to change dim
        //idk why do this
        //but adapted from spectator teleport code (NetHandlerPlayServer::handleSpectate) should be right
        player.dimension = targetDimension;
        player.connection.sendPacket(new SPacketRespawn(player.dimension, worldFrom.getDifficulty(), worldFrom.getWorldInfo().getTerrainType(), player.interactionManager.getGameType()));
        server.getPlayerList().updatePermissionLevel(player);
        worldFrom.removeEntityDangerously(player);
        worldFrom.getChunk(player.chunkCoordX, player.chunkCoordZ).removeEntityAtIndex(player, player.chunkCoordY);
        player.isDead = false;
        player.setLocationAndAngles(x, y, z, player.rotationYaw, player.rotationPitch);

        worldTo.spawnEntity(player);
        worldTo.updateEntityWithOptionalForce(player, false);

        player.setWorld(worldTo);
        server.getPlayerList().preparePlayer(player, worldFrom);

        player.setPositionAndUpdate(x, y, z);
        player.interactionManager.setWorld(worldTo);
        server.getPlayerList().updateTimeAndWeatherForPlayer(player, worldTo);
        server.getPlayerList().syncPlayerInventory(player);
    }
}
