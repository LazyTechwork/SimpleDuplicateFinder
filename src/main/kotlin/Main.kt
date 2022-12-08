import java.io.File
import java.io.IOException

fun main() {
    val hashes = HashMap<ULong, ArrayList<File>>()
    print("Введите путь до директории: ")
    val dir = File(readln())
    dir.walkTopDown().forEach {
        if (it.isFile && it.canRead()) try {
            val hash = hash64FNV1a(it.readBytes())
            hashes[hash] = hashes.getOrDefault(hash, ArrayList()).apply { add(it) }
        } catch (_: IOException) {
        }
    }

    hashes.filter { it.value.size > 1 }.forEach {
        println(
            "Найдено %d дубликат(-ов) для файла %s (хеш-сумма: %s):".format(
                it.value.size - 1, it.value.first().toRelativeString(dir), it.key.toString()
            )
        )
        it.value.subList(1, it.value.size).forEach { file -> println(file.toRelativeString(dir)) }
        println()
    }
}
