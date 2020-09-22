package net.azzy.pulseflux.util.misc

import net.minecraft.util.Identifier


object ResourcePath {

    fun prefixedPath(id: String, resource: String, path: String): Identifier {
        return Identifier(id, "")
    }
}