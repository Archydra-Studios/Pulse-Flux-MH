package net.azzy.pulseflux.util.fluid

import net.minecraft.nbt.CompoundTag

data class DirectionalVectorEnergy(var n: Double = 0.0, var e: Double = 0.0, var s: Double = 0.0, var w: Double = 0.0, var u: Double = 0.0, var d: Double = 0.0) {

    fun toTag(): CompoundTag {
        val innerTag = CompoundTag()
        innerTag.putDouble("north", n)
        innerTag.putDouble("east", e)
        innerTag.putDouble("south", s)
        innerTag.putDouble("west", w)
        innerTag.putDouble("up", u)
        innerTag.putDouble("down", d)
        return innerTag
    }

    fun toTag(tag: CompoundTag) {
        tag.put("vectorEnergy", toTag())
    }

    companion object {
        fun fromTag(tag: CompoundTag): DirectionalVectorEnergy {
            var tag = tag
            if(tag.contains("vectorEnergy"))
                tag = tag["vectorEnergy"] as CompoundTag
            return DirectionalVectorEnergy(
                    tag.getDouble("n"),
                    tag.getDouble("e"),
                    tag.getDouble("s"),
                    tag.getDouble("w"),
                    tag.getDouble("u"),
                    tag.getDouble("d")
            )
        }
    }

    operator fun plusAssign(vector: DirectionalVectorEnergy) {
        n += vector.n
        e += vector.e
        s += vector.s
        w += vector.w
        u += vector.u
        d += vector.d
    }

    operator fun minusAssign(vector: DirectionalVectorEnergy) {
        n -= vector.n
        e -= vector.e
        s -= vector.s
        w -= vector.w
        u -= vector.u
        d -= vector.d
    }

    operator fun timesAssign(vector: DirectionalVectorEnergy) {
        n *= vector.n
        e *= vector.e
        s *= vector.s
        w *= vector.w
        u *= vector.u
        d *= vector.d
    }

    operator fun divAssign(vector: DirectionalVectorEnergy) {
        n /= vector.n
        e /= vector.e
        s /= vector.s
        w /= vector.w
        u /= vector.u
        d /= vector.d
    }

    operator fun plus(vector: DirectionalVectorEnergy): DirectionalVectorEnergy {
        return DirectionalVectorEnergy(
                n + vector.n,
                e + vector.e,
                s + vector.s,
                w + vector.w,
                u + vector.u,
                d + vector.d
        )
    }

    operator fun minus(vector: DirectionalVectorEnergy): DirectionalVectorEnergy {
        return DirectionalVectorEnergy(
                n - vector.n,
                e - vector.e,
                s - vector.s,
                w - vector.w,
                u - vector.u,
                d - vector.d
        )
    }
    operator fun times(vector: DirectionalVectorEnergy): DirectionalVectorEnergy {
        return DirectionalVectorEnergy(
                n * vector.n,
                e * vector.e,
                s * vector.s,
                w * vector.w,
                u * vector.u,
                d * vector.d
        )
    }
    operator fun div(vector: DirectionalVectorEnergy): DirectionalVectorEnergy {
        return DirectionalVectorEnergy(
                n / vector.n,
                e / vector.e,
                s / vector.s,
                w / vector.w,
                u / vector.u,
                d / vector.d
        )
    }
}
