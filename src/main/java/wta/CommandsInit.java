package wta;

import com.mojang.brigadier.arguments.FloatArgumentType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static wta.Fab.MODID;

public class CommandsInit {
	public static float x=0;
	public static float y=0;
	public static float z=0;

	private static final Identifier test=Identifier.of(MODID, "test_packet");

	public record TestPayload(float x, float y, float z) implements CustomPayload {
		public static final Id<TestPayload> ID = new Id<>(test);

		public static final PacketCodec<PacketByteBuf, TestPayload> CODEC =
			  PacketCodec.of(
					(value, buf) -> {
						buf.writeFloat(value.x);
						buf.writeFloat(value.y);
						buf.writeFloat(value.z);
					},
					buf -> {
						float x = buf.readFloat();
						float y = buf.readFloat();
						float z = buf.readFloat();
						return new TestPayload(x, y, z);
					}
			  );

		@Override
		public Id<? extends CustomPayload> getId() {
			return ID;
		}
	}

	public static void init(){
		PayloadTypeRegistry.playS2C().register(TestPayload.ID, TestPayload.CODEC);
		ClientPlayNetworking.registerGlobalReceiver(TestPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				x = payload.x();
				y = payload.y();
				z = payload.z();
			});
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(
				  CommandManager.literal("an")
					    .then(
							  CommandManager.argument("x", FloatArgumentType.floatArg())
								    .then(
										  CommandManager.argument("y", FloatArgumentType.floatArg())
											    .then(
													  CommandManager.argument("z", FloatArgumentType.floatArg())
														    .executes(
																  context -> {
																	    x=FloatArgumentType.getFloat(context, "x");
																	    y=FloatArgumentType.getFloat(context, "y");
																	    z=FloatArgumentType.getFloat(context, "z");

																	    PacketByteBuf buf = PacketByteBufs.create();
																	    buf.writeFloat(x);
																	    buf.writeFloat(y);
																	    buf.writeFloat(z);

																		for (ServerPlayerEntity playerI : context.getSource().getServer().getPlayerManager().getPlayerList()){
																	        ServerPlayNetworking.send(playerI, new TestPayload(x, y, z));
																		}

																	    return 1;
																  }
														    )
											    )
								    )
					    )
			);
		});
	}
}
