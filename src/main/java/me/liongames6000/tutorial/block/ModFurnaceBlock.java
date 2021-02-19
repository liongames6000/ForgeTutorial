package me.liongames6000.tutorial.block;

import me.liongames6000.tutorial.init.ModTileEntityTypes;
import me.liongames6000.tutorial.tileentity.ModFurnaceTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class ModFurnaceBlock extends HorizontalBlock {
    public static final BooleanProperty BURNING = BooleanProperty.create("burning");

    public ModFurnaceBlock(Properties properties) {
        super(properties);

        this.setDefaultState(this.getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH).with(BURNING, false));
    }

    @Override
    public boolean hasTileEntity(final BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world) {
        return ModTileEntityTypes.MOD_FURNACE.get().create();
    }

    /**
     * Amount of light emitted
     *
     * @deprecated Call via {@link BlockState#getLightValue()}
     */
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.get(BURNING) ? super.getLightValue(state, world, pos) : 0;
    }


    @Override
    public void onReplaced(BlockState oldState, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if(oldState.getBlock() != newState.getBlock()) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof ModFurnaceTileEntity) {
                final ItemStackHandler inventory = ((ModFurnaceTileEntity) tileEntity).inventory;
                for(int slot = 0; slot < inventory.getSlots(); slot++) {
                    InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(),
                            inventory.getStackInSlot(slot));
                }
            }
        }
        super.onReplaced(oldState, worldIn, pos, newState, isMoving);
    }

    /**
     * @deprecated Call via {@link BlockState#onBlockActivated(World, PlayerEntity, Hand, BlockRayTraceResult)} whenever possible.
     */
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isRemote) {
            final TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof ModFurnaceTileEntity)
                NetworkHooks.openGui((ServerPlayerEntity) player, (ModFurnaceTileEntity) tileEntity, pos);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    /**
     * @deprecated call via {@link BlockState#getComparatorInputOverride(World, BlockPos)} whenever possible
     */
    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        final TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof ModFurnaceTileEntity)
            return ItemHandlerHelper.calcRedstoneFromInventory(((ModFurnaceTileEntity) tileEntity).inventory);
        return super.getComparatorInputOverride(blockState, worldIn, pos);
    }

    /**
     * Called from inside the constructor {@link Block#Block(Properties)} to add all the properties to our blockstate
     */
    @Override
    protected void fillStateContainer(final StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(HORIZONTAL_FACING);
        builder.add(BURNING);
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate.
     * If inapplicable, returns the passed blockstate.
     *
     * @deprecated call via {@link BlockState#rotate(Rotation)} whenever possible. Implementing/overriding is fine.
     */
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(HORIZONTAL_FACING, rot.rotate(state.get(HORIZONTAL_FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate.
     * If inapplicable, returns the passed blockstate.
     *
     * @deprecated call via {@link BlockState#mirror(Mirror)} whenever possible. Implementing/overriding is fine.
     */
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(HORIZONTAL_FACING)));
    }
}
