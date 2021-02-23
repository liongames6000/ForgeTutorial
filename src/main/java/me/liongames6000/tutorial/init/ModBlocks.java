package me.liongames6000.tutorial.init;

import me.liongames6000.tutorial.TutorialMod;
import me.liongames6000.tutorial.block.ModFurnaceBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TutorialMod.MOD_ID);

    public static final RegistryObject<OreBlock> SILVER_ORE = register("silver_ore", () ->
            getOre(2, SoundType.STONE));

    public static final RegistryObject<Block> SILVER_BLOCK = register("silver_block", () ->
            new Block(AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(3, 10)
                    .sound(SoundType.METAL)));

    public static final RegistryObject<Block> MOD_FURNACE = register("mod_furnace", () ->
            new ModFurnaceBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.5F).setLightLevel((lightValue) -> 13)));

    private static OreBlock getOre(int harvestLevel, SoundType soundType) {
        return new OreBlock(AbstractBlock.Properties.create(Material.ROCK)
        .hardnessAndResistance(4, 10)
        .setRequiresTool()
        .harvestLevel(harvestLevel)
        .harvestTool(ToolType.PICKAXE)
        .sound(soundType));
    }

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> ret = registerNoItem(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
        return ret;
    }
}
