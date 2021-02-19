package me.liongames6000.tutorial.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.liongames6000.tutorial.container.ModFurnaceContainer;
import me.liongames6000.tutorial.tileentity.ModFurnaceTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class ModFurnaceScreen extends ContainerScreen<ModFurnaceContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("minecraft",
            "textures/gui/container/furnace.png");

    public ModFurnaceScreen(final ModFurnaceContainer container, final PlayerInventory inventory, final ITextComponent title) {
        super(container, inventory, title);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);

        int relMouseX = mouseX - this.guiLeft;
        int relMouseY = mouseY - this.guiTop;
        final ModFurnaceTileEntity tileEntity = this.container.tileEntity;
        boolean arrowHovered = relMouseX > 79 && relMouseX < 104 && relMouseY > 34 && relMouseY < 50;
        if (arrowHovered && tileEntity.maxSmeltTime > 0) {
            List<ITextComponent> tooltip = new ArrayList<ITextComponent>();
            tooltip.add(new StringTextComponent(String.valueOf(tileEntity.smeltTimeLeft)));
            tooltip.add(new StringTextComponent(String.valueOf(tileEntity.maxSmeltTime)));

            this.func_243308_b(matrixStack, tooltip, mouseX, mouseY);
        }
        boolean fireHovered = relMouseX > 56 && relMouseX < 70 && relMouseY > 36 && relMouseY < 50;
        if (fireHovered && tileEntity.maxFuelBurnTime > 0) {
            List<ITextComponent> tooltip = new ArrayList<ITextComponent>();
            tooltip.add(new StringTextComponent(String.valueOf(tileEntity.fuelBurnTimeLeft)));
            tooltip.add(new StringTextComponent(String.valueOf(tileEntity.maxFuelBurnTime)));

            this.func_243308_b(matrixStack, tooltip, mouseX, mouseY);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
        // Copied from AbstractFurnaceScreen#drawGuiContainerForegroundLayer
        String s = this.title.getString();
        this.font.drawString(matrixStack, s, (float) (this.xSize / 2 - this.font.getStringWidth(s) / 2), 6.0F, 0x404040);
        this.font.drawString(matrixStack, this.playerInventory.getDisplayName().getString(), 8.0F, (float) (this.ySize - 96 + 2), 0x404040);

        final ModFurnaceTileEntity tileEntity = this.container.tileEntity;
        if (tileEntity.smeltTimeLeft > 0)
            this.font.drawString(matrixStack, tileEntity.smeltTimeLeft + " / " + tileEntity.maxSmeltTime, 8.0F, this.ySize, 0x404040);
        this.font.drawString(matrixStack, tileEntity.fuelBurnTimeLeft + " / " + tileEntity.maxFuelBurnTime, 8.0F, this.ySize + 14, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, final float partialTicks, final int mouseX, final int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int startX = this.guiLeft;
        int startY = this.guiTop;

        // Screen#blit draws a part of the current texture (assumed to be 256x256) to the screen
        // The parameters are (matrixStack, x, y, u, v, width, height)

        this.blit(matrixStack, startX, startY, 0, 0, this.xSize, this.ySize);

        final ModFurnaceTileEntity tileEntity = container.tileEntity;
        if(tileEntity.smeltTimeLeft > 0) {
            // Draw progress arrow
            int arrowWidth = getSmeltTimeScaled();
            this.blit(matrixStack,
                    startX + 76, startY + 34,
                    176, 14,
                    arrowWidth, 14
                    );
        }
        if(tileEntity.isBurning()) {
            //Draw flame
            int flameHeight = getFuelBurnTimeScaled();
            this.blit(matrixStack,
                    startX + 56, startY + 50 - flameHeight,
                    176, 14 - flameHeight,
                    14, flameHeight
            );
        }
    }

    private int getSmeltTimeScaled() {
        final ModFurnaceTileEntity tileEntity = this.container.tileEntity;
        final short smeltTimeLeft = tileEntity.smeltTimeLeft;
        final short maxSmeltTime = tileEntity.maxSmeltTime;
        if(smeltTimeLeft <= 0 || maxSmeltTime <= 0)
            return 0;
        return (maxSmeltTime - smeltTimeLeft) * 24 / maxSmeltTime;
    }

    private int getFuelBurnTimeScaled() {
        final ModFurnaceTileEntity tileEntity = this.container.tileEntity;
        if(tileEntity.maxFuelBurnTime <= 0)
            return 0;
        return tileEntity.fuelBurnTimeLeft * 16 / tileEntity.maxFuelBurnTime; // 14 is the height of the flames
    }
}
