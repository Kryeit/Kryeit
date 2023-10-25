/*
 * Open Parties and Claims Create Support - adds Create mod support to OPAC
 * Copyright (C) 2023, Xaero <xaero1996@gmail.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of version 3 of the GNU Lesser General Public License
 * (LGPL-3.0-only) as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received copies of the GNU Lesser General Public License
 * and the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package com.kryeit.mixin.create;

import com.kryeit.event.ToolboxModeEvent;
import com.simibubi.create.content.equipment.toolbox.ToolboxDisposeAllPacket;
import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ToolboxDisposeAllPacket.class)
public class MixinToolboxDisposeAllPacket {

	@Shadow
	private BlockPos toolboxPos;

	@Inject(method = "lambda$handle$1", remap = false, at = @At("HEAD"), cancellable = true)
	public void onHandle(SimplePacketBase.Context context, CallbackInfo ci){
		ServerPlayer player = context.getSender();

		if (ToolboxModeEvent.EVENT.invoker().onToolboxMode(player, toolboxPos)) {
			ci.cancel();
		}
	}

}
