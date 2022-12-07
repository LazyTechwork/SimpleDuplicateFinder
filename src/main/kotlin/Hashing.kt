private const val FNV1A_OFFSET_BASIS = 14695981039346656037u
private const val FNV1A_PRIME = 1099511628211u
fun hash64FNV1a(data: ByteArray): ULong {
    var hash: ULong = FNV1A_OFFSET_BASIS
    for (byte in data) {
        hash = hash xor (byte.toULong() and 0xFFu)
        hash *= FNV1A_PRIME
    }
    return hash
}
