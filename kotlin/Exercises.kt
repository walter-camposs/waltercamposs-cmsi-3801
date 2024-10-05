import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.io.File

fun change(amount: Long): Map<Int, Long> {
    require(amount >= 0) { "Amount cannot be negative" }

    val counts = mutableMapOf<Int, Long>()
    var remaining = amount
    for (denomination in listOf(25, 10, 5, 1)) {
        counts[denomination] = remaining / denomination
        remaining %= denomination
    }
    return counts
}

fun firstThenLowerCase(a: List<String>, p: (String) -> Boolean): String? {
    return a.firstOrNull(p)?.lowercase()
}

class PhraseBuilder(private var currentPhrase: String = "") {

    fun and(word: String): PhraseBuilder {
        val newPhraseBuilder = PhraseBuilder(currentPhrase)

        if (word.isEmpty()) {
            newPhraseBuilder.currentPhrase += " "
            return newPhraseBuilder
        }

        if (currentPhrase.isEmpty()) {
            newPhraseBuilder.currentPhrase = word
        } else {
            newPhraseBuilder.currentPhrase += " $word"
        }

        return newPhraseBuilder
    }

    val phrase: String
        get() = currentPhrase
}

fun say(word: String = ""): PhraseBuilder {
    return PhraseBuilder(word)
}

fun meaningfulLineCount(filename: String): Long {
    return File(filename).bufferedReader().use { reader ->
        reader.lineSequence()
            .count { line -> line.isNotBlank() && !line.trimStart().startsWith('#') }
            .toLong()
    }
}

data class Quaternion(val a: Double, val b: Double, val c: Double, val d: Double) {
    companion object {
        val ZERO = Quaternion(0.0, 0.0, 0.0, 0.0)
        val I = Quaternion(0.0, 1.0, 0.0, 0.0)
        val J = Quaternion(0.0, 0.0, 1.0, 0.0)
        val K = Quaternion(0.0, 0.0, 0.0, 1.0)
    }

    operator fun plus(other: Quaternion): Quaternion {
        return Quaternion(a + other.a, b + other.b, c + other.c, d + other.d)
    }

    operator fun times(other: Quaternion): Quaternion {
        return Quaternion(
            a * other.a - b * other.b - c * other.c - d * other.d,
            a * other.b + b * other.a + c * other.d - d * other.c,
            a * other.c - b * other.d + c * other.a + d * other.b,
            a * other.d + b * other.c - c * other.b + d * other.a
        )
    }

    fun conjugate(): Quaternion {
        return Quaternion(a, -b, -c, -d)
    }

    fun coefficients(): List<Double> {
        return listOf(a, b, c, d)
    }

    override fun toString(): String {
        val string = StringBuilder()
        for (i in 0 until 4) {
            val coefficient = listOf(a, b, c, d)[i]
            val element = listOf("", "i", "j", "k")[i]
            if (coefficient == 0.0) continue
            if (string.isNotEmpty() && coefficient > 0) {
                string.append("+")
            }
            if (kotlin.math.abs(coefficient) == 1.0 && i > 0) {
                string.append(if (coefficient < 0) "-" else "").append(element)
            } else {
                string.append(coefficient).append(element)
            }
        }
        return if (string.isNotEmpty()) string.toString() else "0"
    }
}

sealed interface BinarySearchTree {
    fun insert(value: String): BinarySearchTree
    fun contains(value: String): Boolean
    fun size(): Int
    override fun toString(): String

    object Empty : BinarySearchTree {
        override fun insert(value: String): BinarySearchTree {
            return Node(value, Empty, Empty)
        }

        override fun contains(value: String): Boolean = false
        override fun size(): Int = 0
        override fun toString(): String = ""
    }

    data class Node(val value: String, val left: BinarySearchTree, val right: BinarySearchTree) : BinarySearchTree {
        override fun insert(value: String): BinarySearchTree {
            return when {
                value < this.value -> Node(this.value, left.insert(value), right)
                value > this.value -> Node(this.value, left, right.insert(value))
                else -> this
            }
        }

        override fun contains(value: String): Boolean {
            return when {
                value < this.value -> left.contains(value)
                value > this.value -> right.contains(value)
                else -> true
            }
        }

        override fun size(): Int = 1 + left.size() + right.size()

        override fun toString(): String {
            return "(${left.toString()}$value${right.toString()})"
        }
    }
}
