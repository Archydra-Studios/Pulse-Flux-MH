package net.azzy.pulseflux

import net.azzy.pulseflux.PulseFlux.MOD_ID
import net.azzy.pulseflux.PulseFlux.PFLog
import net.devtech.arrp.api.RRPPreGenEntrypoint
import net.devtech.arrp.impl.RuntimeResourcePackImpl
import net.devtech.arrp.json.models.JModel
import net.minecraft.util.Identifier

class ResourceInit : RRPPreGenEntrypoint {

    override fun pregen() {
    }

    companion object{
        @JvmStatic
        val PF_DYN_RES = RuntimeResourcePackImpl(Identifier("pulseflux"))
    }
}