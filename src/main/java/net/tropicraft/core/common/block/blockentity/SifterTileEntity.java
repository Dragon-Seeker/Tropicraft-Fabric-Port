package net.tropicraft.core.common.block.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
//import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.tropicraft.Constants;
import net.tropicraft.core.client.data.TropicraftTags;
//import net.tropicraft.core.common.TropicraftTags;
//import net.tropicraft.core.common.item.TropicraftItems;
//import net.tropicraft.core.common.network.TropicraftPackets;
//import net.tropicraft.core.common.network.message.MessageSifterInventory;
//import net.tropicraft.core.common.network.message.MessageSifterStart;
import net.tropicraft.core.common.registry.TropicBlockEntities;
import net.tropicraft.core.common.registry.TropicraftItems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import oshi.annotation.concurrent.NotThreadSafe;

//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
import java.util.Random;

public class SifterTileEntity extends BlockEntity {

    /** Number of seconds to sift multiplied by the number of ticks per second */
    private static final int SIFT_TIME = 4 * 20;

    /** Is this machine currently sifting? */
    private boolean isSifting;

    /** Current progress in sifting; -1 if not sifting */
    private int currentSiftTime;

    private Random rand;

    public double yaw;
    public double yaw2 = 0.0D;

    @NotNull
    private ItemStack siftItem = ItemStack.EMPTY;

    public SifterTileEntity(BlockPos pos, BlockState state) {
        super(TropicBlockEntities.SIFTER, pos, state);
        rand = new Random();
        currentSiftTime = SIFT_TIME;
    }

    @NotNull
    public ItemStack getSiftItem() {
        return siftItem;
    }

    public void tick(World world, BlockPos pos, BlockState state, BlockEntity be) {
        // If sifter is sifting, decrement sift time
        if (currentSiftTime > 0 && isSifting) {
            currentSiftTime--;
        }

        // Rotation animation
        if (world.isClient) {
            yaw2 = yaw % 360.0D;
            yaw += 4.545454502105713D;
        }

        // Done sifting
        if (isSifting && currentSiftTime <= 0) {
            stopSifting();
        }
    }

    public void dumpResults(final BlockPos pos) {
        // NOTE: Removed check and drop for heated sifter in 1.12
        dumpBeachResults(pos);
        syncInventory();
    }

    // TODO replace with loot table
    private void dumpBeachResults(final BlockPos pos) {
        int dumpCount = rand.nextInt(3) + 1;
        ItemStack stack;

        while (dumpCount > 0) {
            dumpCount--;

            if (rand.nextInt(10) == 0) {
                stack = getRareItem();
            } else if (rand.nextInt(10) < 3) {
                String name;
                if (rand.nextBoolean()) {
                    name = Constants.LT17_NAMES[rand.nextInt(Constants.LT17_NAMES.length)];
                } else {
                    name = Constants.LT18_NAMES[rand.nextInt(Constants.LT18_NAMES.length)];
                }
                final NbtCompound nameTag = new NbtCompound();
                nameTag.putString("Name", name);
                stack = new ItemStack(TropicraftItems.LOVE_TROPICS_SHELL);
                stack.setNbt(nameTag);
            } else {
                stack = getCommonItem();
            }

            spawnItem(stack, pos);
        }
    }

    private void spawnItem(ItemStack stack, BlockPos pos) {
        if (world.isClient) {
            return;
        }

        final ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
        world.spawnEntity(itemEntity);
    }

    //TODO: Need to still implement tag stuff so not going to work
    private ItemStack getCommonItem() {
        // Random from -1 to size-1
        final int shellIndex = rand.nextInt(TropicraftTags.Items.SHELLS.values().size() + 1) - 1;
        if (shellIndex < 0) {
            return getRareItem();
        }
        return new ItemStack(TropicraftTags.Items.SHELLS.getRandom(rand));
    }

    private ItemStack getRareItem() {
        final int dmg = rand.nextInt(12);

        switch (dmg) {
            case 1:
                return new ItemStack(Items.GOLD_NUGGET, 1);
            case 2:
                return new ItemStack(Items.BUCKET, 1);
            case 3:
                return new ItemStack(Items.WOODEN_SHOVEL, 1);
            case 4:
                return new ItemStack(Items.GLASS_BOTTLE, 1);
            case 5:
                return new ItemStack(TropicraftItems.WHITE_PEARL, 1);
            case 6:
                return new ItemStack(TropicraftItems.BLACK_PEARL, 1);
            case 7:
                return new ItemStack(Items.STONE_SHOVEL, 1);
            case 0:
            default:
                return new ItemStack(TropicraftItems.RUBE_NAUTILUS);
        }
    }

    public void addItemToSifter(ItemStack stack) {
        siftItem = stack.copy().split(1);
        syncInventory();
    }

    public void startSifting() {
        isSifting = true;
        currentSiftTime = SIFT_TIME;

        if (!world.isClient) {
            //TODO: Reimplement this later
            //TropicraftPackets.sendToDimension(new MessageSifterStart(this), world.getRegistryKey());
        }
    }

    private void stopSifting() {
        final double x = pos.getX() + world.random.nextDouble() * 1.4;
        final double y = pos.getY() + world.random.nextDouble() * 1.4;
        final double z = pos.getZ() + world.random.nextDouble() * 1.4;

        if (!world.isClient) {
            dumpResults(new BlockPos(x, y, z));
        }
        currentSiftTime = SIFT_TIME;
        isSifting = false;
        siftItem = ItemStack.EMPTY;
        syncInventory();
    }

    public void setSifting(boolean flag) {
        this.isSifting = flag;
    }

    public boolean isSifting() {
        return this.isSifting;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        isSifting = nbt.getBoolean("isSifting");
        currentSiftTime = nbt.getInt("currentSiftTime");

        if (nbt.contains("Item", 10)) {
            siftItem = ItemStack.fromNbt(nbt.getCompound("Item"));
        }
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("isSifting", isSifting);
        nbt.putInt("currentSiftTime", currentSiftTime);
        if (!siftItem.isEmpty()) {
            nbt.put("Item", siftItem.writeNbt(new NbtCompound()));
        }
        return nbt;
    }

    public NbtCompound getTagCompound(ItemStack stack) {
        if (!stack.hasNbt())
            stack.setNbt(new NbtCompound());

        return stack.getNbt();
    }
    /*
    @Override
    public void onDataPacket(ClientConnection net, BlockEntityUpdateS2CPacket pkt) {
        readNbt(pkt.getNbt());
    }
     */

    protected void syncInventory() {
        //TropicraftPackets.sendToDimension(new MessageSifterInventory(this), world.getRegistryKey());
    }

    @Nullable
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return new BlockEntityUpdateS2CPacket(this.pos, 1, this.toInitialChunkDataNbt());
    }

    public NbtCompound toInitialChunkDataNbt() {
        return writeItems(new NbtCompound());
    }

    private NbtCompound writeItems(final NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, DefaultedList.copyOf(siftItem), true);
        return nbt;
    }

    public void setSiftItem(final ItemStack siftItem) {
        this.siftItem = siftItem.copy().split(1);
    }
}
