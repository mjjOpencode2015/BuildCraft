package buildcraft.lib.misc;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ChunkUtil {
    private static final ThreadLocal<Chunk> lastChunk = new ThreadLocal<>();

    public static Chunk getChunk(World world, ChunkPos pos) {
        return getChunk(world, pos.chunkXPos, pos.chunkZPos);
    }

    public static Chunk getChunk(World world, int x, int z) {
        Chunk chunk = lastChunk.get();

        if (chunk != null) {
            if (chunk.isLoaded()) {
                if (chunk.getWorld() == world && chunk.xPosition == x && chunk.zPosition == z) {
                    return chunk;
                }
            } else {
                lastChunk.set(null);
            }
        }

        chunk = world.getChunkProvider().getLoadedChunk(x, z);

        if (chunk != null) {
            lastChunk.set(chunk);
        }
        return chunk;
    }
}
