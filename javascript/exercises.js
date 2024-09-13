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

// Write your Quaternion class here
