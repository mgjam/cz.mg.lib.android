package cz.mg.lib.extensions

class ScreenDensity(
    val density: Float
) : Comparable<ScreenDensity> {
    companion object {
        /**
         *
         * */
        val LDPI = ScreenDensity(0.75f)
        val MDPI = ScreenDensity(1f)
        /**
         * 480x800
         * */
        val HDPI = ScreenDensity(1.5f)
        /**
         * 720*1280
         * */
        val XDPI = ScreenDensity(2f)
        /**
         * 1080*1920
         * */
        val XXDPI = ScreenDensity(3f)
        val XXXDPI = ScreenDensity(4f)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScreenDensity

        if (density != other.density) return false

        return true
    }

    override fun hashCode(): Int {
        return density.hashCode()
    }

    override fun compareTo(other: ScreenDensity): Int {
        if (density == other.density) return 0
        if (density < other.density) return -1
        return 1
    }
}