package me.liongames6000.tutorial.data;

import me.liongames6000.tutorial.TutorialMod;
import me.liongames6000.tutorial.data.client.ModBlockStateProvider;
import me.liongames6000.tutorial.data.client.ModItemModelProvider;
import me.liongames6000.tutorial.data.loot.ModLootTableProvider;
import me.liongames6000.tutorial.data.recipes.ModRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = TutorialMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(gen, existingFileHelper);
        gen.addProvider(blockTagsProvider);
        gen.addProvider(new ModItemTagsProvider(gen, blockTagsProvider, existingFileHelper));
        gen.addProvider(new ModRecipeProvider(gen));
        gen.addProvider((new ModLootTableProvider(gen)));

        gen.addProvider(new ModBlockStateProvider(gen, existingFileHelper));
        gen.addProvider(new ModItemModelProvider(gen, existingFileHelper));
    }
}
