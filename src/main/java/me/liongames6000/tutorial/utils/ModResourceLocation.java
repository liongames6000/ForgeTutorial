package me.liongames6000.tutorial.utils;

import me.liongames6000.tutorial.TutorialMod;
import net.minecraft.util.ResourceLocation;

public class ModResourceLocation extends ResourceLocation {
    public ModResourceLocation(String resourceName) {
        super(resourceName);
    }

    private static String addModNamespace(String resourceName) {
        if(resourceName.contains(":")) {
            return resourceName;
        }
        return TutorialMod.MOD_ID + ":" + resourceName;
    }
}
