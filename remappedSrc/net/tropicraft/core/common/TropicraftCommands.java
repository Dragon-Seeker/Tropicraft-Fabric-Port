package net.tropicraft.core.common;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.tropicraft.core.common.dimension.TropicraftDimension;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static net.minecraft.commands.Commands.*;

public class TropicraftCommands {
    public static void commandInitalization(){
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(
                literal("tropicDimensionTP")
                    .requires(s -> s.hasPermission(2))
                        .then(argument("world", string())
                                .executes(c -> teleportTropics(c.getSource(), getString(c, "world")))
                        )
                        .executes(c -> {
                            c.getSource().sendSuccess(Component.nullToEmpty("Input either Overworld or Tropics to teleport"), false);
                            return 1;
                        })
            );
        });
    }

    private static int teleportTropics(final CommandSourceStack source, String string) {
        if (source.getEntity().getType() != EntityType.PLAYER) {
            source.sendFailure(new TextComponent("Cannot teleport non-players!"));
        }
        if(string.equals("overworld") || string.equals("Overworld")) {
            TropicraftDimension.teleportPlayer((ServerPlayer) source.getEntity(), Level.OVERWORLD);
        }

        if(string.equals("tropics") || string.equals("Tropics") || string.equals("tropicraft") || string.equals("Tropicraft")) {
            TropicraftDimension.teleportPlayer((ServerPlayer) source.getEntity(), TropicraftDimension.WORLD);
        }

        return 1;
    }


}
