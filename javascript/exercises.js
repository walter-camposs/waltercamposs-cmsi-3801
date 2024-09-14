import { open } from "node:fs/promises"

export function change(amount) {
  if (!Number.isInteger(amount)) {
    throw new TypeError("Amount must be an integer")
  }
  if (amount < 0) {
    throw new RangeError("Amount cannot be negative")
  }
  let [counts, remaining] = [{}, amount]
  for (const denomination of [25, 10, 5, 1]) {
    counts[denomination] = Math.floor(remaining / denomination)
    remaining %= denomination
  }
  return counts
}

// Write your first then lower case function here
export function firstThenLowerCase(strings, predicate) {
  for (const str of strings) {
    if (str?.toString() && predicate(str)) {
      return str.toLowerCase()
    }
  }
  return undefined
}
// Write your powers generator here
export function powersGenerator({ ofBase: base, upTo: limit }) {
  let value = 1
  return {
    [Symbol.iterator]() {
      return this
    },
    next() {
      if (value > limit) {
        return { value: undefined, done: true }
      }
      const currentValue = value
      value *= base
      return { value: currentValue, done: false }
    },
  }
}

// Write your say function here
export function say(word) {
  let sentence = []
  function addToSentence(nextWord) {
    if (nextWord === undefined) {
      return sentence.join(" ")
    } else {
      sentence.push(nextWord)
      return addToSentence
    }
  }
  if (word === undefined) {
    return addToSentence()
  }
  sentence.push(word)
  return addToSentence
}
// Write your line count function here
async function meaningfulLineCount(filename) {
  let fileHandle
  try {
    fileHandle = await open(filename, "r")
    const data = await fileHandle.readFile("utf8")
    const lines = data.split("\n")
    const meaningfulLines = lines.filter((line) => {
      const trimmedLine = line.trim()
      return trimmedLine.length > 0 && !trimmedLine.startsWith("#")
    })
    return meaningfulLines.length
  } catch (error) {
    throw new Error(`Error reading file: ${error.message}`)
  } finally {
    if (fileHandle) {
      await fileHandle.close()
    }
  }
}

export { meaningfulLineCount }
// Write your Quaternion class here
