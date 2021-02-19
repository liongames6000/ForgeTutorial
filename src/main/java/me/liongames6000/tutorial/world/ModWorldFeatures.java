package me.liongames6000.tutorial.world;

import me.liongames6000.tutorial.TutorialMod;
import me.liongames6000.tutorial.init.ModBlocks;
import me.liongames6000.tutorial.utils.ModResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID)
public class ModWorldFeatures {
    private static boolean configuredFeaturesRegistered = false;

    @SuppressWarnings("WeakerAccess")
    public static final class Configured {
        static final Map<String, Lazy<ConfiguredFeature<?, ?>>> TO_REGISTER = new LinkedHashMap<>();

        public static final Lazy<ConfiguredFeature<?, ?>> SILVER_ORE_VEINS = createLazy("silver_ore_veins", () -> Feature.ORE
                .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
                        ModBlocks.SILVER_ORE.get().getDefaultState(), 8))
                .withPlacement(Placement.RANGE.configure(topSolidRange(24, 120)))
                .square()
                .func_242731_b(10));

        private static Lazy<ConfiguredFeature<?, ?>> createLazy(String name, Supplier<ConfiguredFeature<?, ?>> supplier) {
            if(TO_REGISTER.containsKey(name)) {
                throw new IllegalArgumentException("Configured feature lazy with name '" + name + "' already created");
            }

            Lazy<ConfiguredFeature<?, ?>> lazy = Lazy.of(supplier);
            TO_REGISTER.put(name, lazy);
            return lazy;
        }

        @Nonnull
        private static TopSolidRangeConfig topSolidRange(int bottom, int top) {
            return new TopSolidRangeConfig(bottom, 0, top - bottom);
        }

        private Configured() {}
    }

    public ModWorldFeatures() {}

    public static void registerConfiguredFeatures() {
        if(configuredFeaturesRegistered) return;

        configuredFeaturesRegistered = true;

        Configured.TO_REGISTER.forEach((name, cf) -> registerConfiguredFeature(name, cf.get()));
    }

    public static void registerConfiguredFeature(String name, ConfiguredFeature<?, ?> configuredFeature) {
        ModResourceLocation id = TutorialMod.getId(name);
        TutorialMod.LOGGER.debug("Register configured feature {}", id);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, id, configuredFeature);
    }

    public static void registerPlacements(RegistryEvent.Register<Placement<?>> event) {
    }

    @SubscribeEvent
    public static void addFeaturesToBiomes(BiomeLoadingEvent biome) {
        registerConfiguredFeatures();

        if(biome.getCategory() != Biome.Category.NETHER || biome.getCategory() != Biome.Category.THEEND) addSilverOre(biome);
    }

    private static void addSilverOre(BiomeLoadingEvent biome) {
        biome.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Configured.SILVER_ORE_VEINS.get());
    }
}
