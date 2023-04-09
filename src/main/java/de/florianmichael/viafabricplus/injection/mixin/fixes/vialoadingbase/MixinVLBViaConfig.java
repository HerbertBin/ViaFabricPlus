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
package de.florianmichael.viafabricplus.injection.mixin.fixes.vialoadingbase;

import com.viaversion.viaversion.configuration.AbstractViaConfig;
import de.florianmichael.viafabricplus.settings.groups.GeneralSettings;
import de.florianmichael.vialoadingbase.platform.viaversion.VLBViaConfig;
import org.spongepowered.asm.mixin.Mixin;

import java.io.File;

@Mixin(value = VLBViaConfig.class, remap = false)
public abstract class MixinVLBViaConfig extends AbstractViaConfig {

    protected MixinVLBViaConfig(File configFile) {
        super(configFile);
    }

    @Override
    public boolean isLeftHandedHandling() {
        return false;
    }

    @Override
    public boolean isShieldBlocking() {
        return false;
    }

    @Override
    public boolean isChunkBorderFix() {
        return GeneralSettings.INSTANCE.fixChunkBorders.getValue();
    }
}
