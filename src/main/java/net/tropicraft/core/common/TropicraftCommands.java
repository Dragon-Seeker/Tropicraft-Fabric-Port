package net.tropicraft.core.common;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.EntityType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.tropicraft.core.common.dimension.TropicraftDimension;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static net.minecraft.server.command.CommandManager.*;

public class TropicraftCommands {
    public static void commandInitalization(){
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(
                literal("tropicDimensionTP")
                    .requires(s -> s.hasPermissionLevel(2))
                        .then(argument("world", string())
                                .executes(c -> teleportTropics(c.getSource(), getString(c, "world")))
                        )
                        .executes(c -> {
                            c.getSource().sendFeedback(Text.of("Input either Overworld or Tropics to teleport"), false);
                            return 1;
                        })
            );
        });
    }

    private static int teleportTropics(final ServerCommandSource source, String string) {
        if (source.getEntity().getType() != EntityType.PLAYER) {
            source.sendError(new LiteralText("Cannot teleport non-players!"));
        }
        if(string.equals("overworld") || string.equals("Overworld")) {
            TropicraftDimension.teleportPlayer((ServerPlayerEntity) source.getEntity(), World.OVERWORLD);
        }

        if(string.equals("tropics") || string.equals("Tropics") || string.equals("tropicraft") || string.equals("Tropicraft")) {
            TropicraftDimension.teleportPlayer((ServerPlayerEntity) source.getEntity(), TropicraftDimension.WORLD);
        }

        return 1;
    }


}
