package me.liongames6000.tutorial.data.recipes;

import me.liongames6000.tutorial.TutorialMod;
import me.liongames6000.tutorial.init.ModBlocks;
import me.liongames6000.tutorial.init.ModItems;
import me.liongames6000.tutorial.init.ModTags;
import net.minecraft.data.*;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "Tutorial Mod - Recipes";
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        registerCookingRecipes(consumer);
        registerShapelessCraftingRecipes(consumer);
    }

    private void registerCookingRecipes(Consumer<IFinishedRecipe> consumer) {
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(ModTags.Items.ORES_SILVER), ModItems.SILVER_INGOT.get(),
                0.5f, 100).addCriterion("has_item", hasItem(ModTags.Items.ORES_SILVER))
                .build(consumer, TutorialMod.getId(ModItems.SILVER_INGOT.getId().getNamespace() + "_ore_blasting"));
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(ModTags.Items.ORES_SILVER), ModItems.SILVER_INGOT.get(),
                0.5f, 200).addCriterion("has_item", hasItem(ModTags.Items.ORES_SILVER))
                .build(consumer, TutorialMod.getId(ModItems.SILVER_INGOT.getId().getNamespace() + "_ore_smelting"));
    }

    private void registerShapelessCraftingRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.SILVER_INGOT.get(), 9)
                .addIngredient(Ingredient.fromTag(ModTags.Items.STORAGE_BLOCKS_SILVER), 1)
                .addCriterion("has_item", hasItem(ModTags.Items.INGOTS_SILVER))
                .build(consumer, TutorialMod.getId(ModItems.SILVER_INGOT.getId().getNamespace() + "_from_block"));
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.SILVER_BLOCK.get(), 1).
                key('#', ModItems.SILVER_INGOT.get())
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_item", hasItem(ModTags.Items.INGOTS_SILVER))
                .build(consumer);
    }
}
