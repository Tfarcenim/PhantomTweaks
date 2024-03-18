package tfar.phantomtweaks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tfar.phantomtweaks.mixin.EndDragonFightMixin;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class PhantomTweaks {

    public static final String MOD_ID = "phantomtweaks";
    public static final String MOD_NAME = "PhantomTweaks";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {

        // It is common for all supported loaders to provide a similar feature that can not be used directly in the
        // common code. A popular way to get around this is using Java's built-in service loader feature to create
        // your own abstraction layer. You can learn more about this in our provided services class. In this example
        // we have an interface in the common code and use a loader specific implementation to delegate our call to
        // the platform specific approach.

    }

    public static boolean requireDragonDefeat(EntityType<Phantom> pType, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        boolean b = Mob.checkMobSpawnRules(pType,pLevel,pSpawnType,pPos,pRandom);
        if (pLevel instanceof ServerLevel serverLevel) {
            EndDragonFight endDragonFight = serverLevel.getDragonFight();
            b &= endDragonFight != null && ((EndDragonFightMixin) endDragonFight).getDragonKilled();
        }
        return b;
    }

}