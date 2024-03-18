package tfar.phantomtweaks;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tfar.phantomtweaks.datagen.ModDatagen;

@Mod(PhantomTweaks.MOD_ID)
public class PhantomTweaksForge {
    
    public PhantomTweaksForge() {
    
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(ModDatagen::gather);
        bus.addListener(this::spawnPlacement);
        // Use Forge to bootstrap the Common mod.
        PhantomTweaks.init();
    }

    private void spawnPlacement(SpawnPlacementRegisterEvent e) {
        e.register(EntityType.PHANTOM, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PhantomTweaks::requireDragonDefeat,
                SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

}