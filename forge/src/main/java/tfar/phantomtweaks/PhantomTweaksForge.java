package tfar.phantomtweaks;

import net.minecraftforge.fml.common.Mod;

@Mod(PhantomTweaks.MOD_ID)
public class PhantomTweaksForge {
    
    public PhantomTweaksForge() {
    
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
    
        // Use Forge to bootstrap the Common mod.
        PhantomTweaks.LOG.info("Hello Forge world!");
        PhantomTweaks.init();
        
    }
}