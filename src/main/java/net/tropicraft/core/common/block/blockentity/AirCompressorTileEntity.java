package net.tropicraft.core.common.block.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
//import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NbtCompound;
//import net.minecraft.network.ClientConnection;
//import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
//import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.Direction;
//import net.minecraftforge.fml.network.PacketDistributor;
//import net.tropicraft.core.common.block.AirCompressorBlock;
//import net.tropicraft.core.common.item.scuba.ScubaArmorItem;
//import net.tropicraft.core.common.network.TropicraftPackets;
//import net.tropicraft.core.common.network.message.MessageAirCompressorInventory;
import net.tropicraft.core.common.registry.TropicBlockEntities;
import org.jetbrains.annotations.NotNull;


//import javax.annotation.Nullable;

public class AirCompressorTileEntity extends BlockEntity { //implements IMachineTile {

    /** Is the compressor currently giving air */
    private boolean compressing;

    /** Number of ticks compressed so far */
    private int ticks;

    /** Amount of PSI (ticks of air time) to fill per tick */
    private static final int fillRate = 5; // fills 5x faster than it's used
    
    /** The stack that is currently being filled */
    @NotNull
    private ItemStack stack;
    
    //private ScubaArmorItem tank;

    public AirCompressorTileEntity(BlockPos pos, BlockState state) {
        super(TropicBlockEntities.AIR_COMPRESSOR, pos, state);
        this.stack = ItemStack.EMPTY;
    }

    /*
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.compressing = nbt.getBoolean("Compressing");

        if (nbt.contains("Tank")) {
            setTank(ItemStack.fromNbt(nbt.getCompound("Tank")));
        } else {
            setTank(ItemStack.EMPTY);
        }
    }

    @Override
    public @NotNull NbtCompound writeNbt(@NotNull NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("Compressing", compressing);

        NbtCompound var4 = new NbtCompound();
        this.stack.writeNbt(var4);
        nbt.put("Tank", var4);
        
        return nbt;
    }

    public void setTank(@NotNull ItemStack tankItemStack) {
        this.stack = tankItemStack;
        this.tank = !(stack.getItem() instanceof ScubaArmorItem) ? null : (ScubaArmorItem) stack.getItem();
    }

    
    @Nonnull
    public ItemStack getTankStack() {
        return stack;
    }
    
    @Nullable
    public ScubaArmorItem getTank() {
        return tank;
    }
    */

    public void tick() {
        /*
        if (tank == null)
            return;

        int airContained = tank.getRemainingAir(getTankStack());
        int maxAir = tank.getMaxAir(getTankStack());

        if (compressing) {
            int overflow = tank.addAir(fillRate, getTankStack());
            ticks++;
            if (overflow > 0) {
                finishCompressing();
            }
        }
         */
    }
    /*
    public boolean addTank(ItemStack stack) {
        if (tank == null && stack.getItem() instanceof ScubaArmorItem && ((ScubaArmorItem)stack.getItem()).providesAir()) {
            setTank(stack);
            this.compressing = true;
            syncInventory();
            return true;
        }

        return false;
    }

    public void ejectTank() {
        if (!stack.isEmpty()) {
            if (!world.isClient) {
                ItemEntity tankItem = new ItemEntity(world, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), stack);
                world.spawnEntity(tankItem);
            }
        }

        setTank(ItemStack.EMPTY);
        syncInventory();
        this.ticks = 0;
        this.compressing = false;
    }

    public boolean isDoneCompressing() {
        return this.ticks > 0 && !this.compressing;
    }

    public float getTickRatio(float partialTicks) {
        if (tank != null) {
            return (this.ticks + partialTicks) / (tank.getMaxAir(getTankStack()) * fillRate);
        }
        return 0;
    }

    public boolean isCompressing() {
        return this.compressing;
    }

    public void startCompressing() {
        this.compressing = true;
        syncInventory();
    }

    public void finishCompressing() {
        this.compressing = false;
        this.ticks = 0;
        syncInventory();
    }
    
    public float getBreatheProgress(float partialTicks) {
        if (isDoneCompressing()) {
            return 0;
        }
        return (float) (((((ticks + partialTicks) / 20) * Math.PI) + Math.PI) % (Math.PI * 2));
    }
     */
    
    /* == IMachineTile == */

    /*
    @Override
    public boolean isActive() {
        return !getTankStack().isEmpty();
    }
    
    @Override
    public float getProgress(float partialTicks) {
        return getTickRatio(partialTicks);
    }
    
    @Override
    public Direction getDirection(BlockState state) {
        return state.get(AirCompressorBlock.FACING);
    }
     */
    /**
     * Called when you receive a TileEntityData packet for the location this
     * TileEntity is currently in. On the client, the NetworkManager will always
     * be the remote server. On the server, it will be whomever is responsible for
     * sending the packet.
     *
     * @param net The NetworkManager the packet originated from
     * @param pkt The data packet
     */

    /*
    @Override
    public void onDataPacket(ClientConnection net, BlockEntityUpdateS2CPacket pkt) {
        this.fromTag(getCachedState(), pkt.getNbt());
    }

    protected void syncInventory() {
        if (!world.isClient) {
            TropicraftPackets.INSTANCE.send(PacketDistributor.DIMENSION.with(world::getRegistryKey), new MessageAirCompressorInventory(this));
        }
    }

    @Override
    @Nullable
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return new BlockEntityUpdateS2CPacket(this.pos, 1, this.toInitialChunkDataNbt());
    }

    @Override
    public @Nonnull NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbttagcompound = this.writeNbt(new NbtCompound());
        return nbttagcompound;
    }

     */
}
