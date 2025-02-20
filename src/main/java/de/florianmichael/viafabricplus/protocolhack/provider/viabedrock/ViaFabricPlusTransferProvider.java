/*
 * This file is part of ViaFabricPlus - https://github.com/FlorianMichael/ViaFabricPlus
 * Copyright (C) 2021-2023 FlorianMichael/EnZaXD and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.florianmichael.viafabricplus.protocolhack.provider.viabedrock;

import com.viaversion.viaversion.api.connection.UserConnection;
import de.florianmichael.viafabricplus.base.settings.groups.BedrockSettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import net.raphimc.viabedrock.protocol.providers.TransferProvider;

import java.net.InetSocketAddress;

public class ViaFabricPlusTransferProvider extends TransferProvider {

    private void connect(final InetSocketAddress newAddress) {
        final var mc = MinecraftClient.getInstance();
        mc.world.disconnect();

        final var serverInfo = new ServerInfo(newAddress.getHostName(), newAddress.getHostName() + ":" + newAddress.getPort(), false);
        ConnectScreen.connect(new MultiplayerScreen(new TitleScreen()), mc, ServerAddress.parse(serverInfo.address), serverInfo, false);
    }

    @Override
    public void connectToServer(UserConnection user, InetSocketAddress newAddress) throws Exception {
        final var mc = MinecraftClient.getInstance();
        mc.execute(() -> {
            if (BedrockSettings.INSTANCE.confirmServerTransferInBedrockEdition.getValue()) {
                mc.setScreen(new ConfirmScreen(
                        (bl) -> {
                            if (bl)
                                connect(newAddress);
                            else
                                MinecraftClient.getInstance().setScreen(null);
                        },
                        Text.of("ViaFabricPlus"),
                        Text.translatable("bedrockplay.viafabricplus.confirmtransfer", newAddress.getHostName() + ":" + newAddress.getPort())
                ));
            } else {
                connect(newAddress);
            }
        });
    }
}
