package tfar.phantomtweaks.datagen;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import tfar.phantomtweaks.PhantomTweaks;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDatagen {

    public static void gather(GatherDataEvent e) {
        ExistingFileHelper existingFileHelper = e.getExistingFileHelper();
        DataGenerator generator = e.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        boolean server = e.includeServer();

        generator.addProvider(server, new DatapackBuiltinEntriesProvider(
                packOutput, CompletableFuture.supplyAsync(ModDatagen::getProvider), Set.of(PhantomTweaks.MOD_ID)));
    }


    private static HolderLookup.Provider getProvider() {
        final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
        // We need the BIOME registry to be present so we can use a biome tag, doesn't matter that it's empty
        registryBuilder.add(Registries.BIOME, context -> {
        });
        registryBuilder.add(ForgeRegistries.Keys.BIOME_MODIFIERS, context -> {
            final HolderGetter<Biome> biomeHolderGetter = context.lookup(Registries.BIOME);
            final BiomeModifier addSpawn = ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
                    biomeHolderGetter.getOrThrow(BiomeTags.IS_END),
                    new MobSpawnSettings.SpawnerData(EntityType.PHANTOM, 5, 1, 4));
            context.register(createModifierKey("add_phantom_spawn"), addSpawn);
        });
        RegistryAccess.Frozen regAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        return registryBuilder.buildPatch(regAccess, VanillaRegistries.createLookup());
    }

    private static ResourceKey<BiomeModifier> createModifierKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(PhantomTweaks.MOD_ID, name));
    }
}
