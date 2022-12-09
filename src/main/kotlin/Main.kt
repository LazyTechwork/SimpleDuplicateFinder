import java.io.File
import java.io.IOException

fun main() {
    val hashes = HashMap<ULong, ArrayList<File>>()
    print("Введите путь до директории: ")
    val dir = File(readln())
    dir.walkTopDown().forEach {
        if (it.isFile && it.canRead()) {
            if (it.length() < 2L * 1024 * 1024 * 1024) try {
                val hash = hash64FNV1a(it.readBytes())
                hashes[hash] = hashes.getOrDefault(hash, ArrayList()).apply { add(it) }
            } catch (_: IOException) {
            }
            else println("Пропущен анализ файла %s из-за большого размера".format(it.toRelativeString(dir)))
        }
    }

    hashes.filter { it.value.size > 1 }.apply {
        if (isEmpty())
            println("Дубликатов не найдено")
    }.forEach {
        println(
            "Найдено %d дубликат(-ов) для файла %s (хеш-сумма: %s):".format(
                it.value.size - 1, it.value.first().toRelativeString(dir), it.key.toString()
            )
        )
        it.value.subList(1, it.value.size).forEach { file -> println(file.toRelativeString(dir)) }
        println()
    }
}
