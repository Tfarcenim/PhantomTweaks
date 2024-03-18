package tfar.phantomtweaks;

import net.minecraftforge.common.MinecraftForge;
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

        // Use Forge to bootstrap the Common mod.
        PhantomTweaks.init();
        
    }



}