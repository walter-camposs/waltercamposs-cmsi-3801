import { open } from "node:fs/promises"
import * as readline from "readline"
import { createReadStream } from "fs"

export function change(amount: bigint): Map<bigint, bigint> {
  if (amount < 0) {
    throw new RangeError("Amount cannot be negative")
  }
  let counts: Map<bigint, bigint> = new Map()
  let remaining = amount
  for (const denomination of [25n, 10n, 5n, 1n]) {
    counts.set(denomination, remaining / denomination)
    remaining %= denomination
  }
  return counts
}

export function firstThenApply<T, U>(
  array: T[],
  predicate: (item: T) => boolean,
  fn: (item: T) => U
): U | undefined {
  for (const item of array) {
    if (predicate(item)) {
      return fn(item)
    }
  }
  return undefined
}

export function* powersGenerator(
  base: bigint
): Generator<bigint, void, unknown> {
  let power = 0n
  while (true) {
    yield base ** power
    power += 1n
  }
}

export async function meaningfulLineCount(filePath: string): Promise<number> {
  let validLineCount = 0

  const fileStream = createReadStream(filePath)
  const rl = readline.createInterface({
    input: fileStream,
    crlfDelay: Infinity,
  })

  for await (const line of rl) {
    const trimmedLine = line.trim()
    if (trimmedLine && !trimmedLine.startsWith("#")) {
      validLineCount++
    }
  }

  rl.close()
  return validLineCount
}

export type Shape = Box | Sphere

export interface Box {
  kind: "Box"
  width: number
  length: number
  depth: number
}

export interface Sphere {
  kind: "Sphere"
  radius: number
}

export function surfaceArea(shape: Shape): number {
  switch (shape.kind) {
    case "Box":
      return (
        2 *
        (shape.width * shape.length +
          shape.width * shape.depth +
          shape.length * shape.depth)
      )
    case "Sphere":
      return 4 * Math.PI * shape.radius ** 2
  }
}

export function volume(shape: Shape): number {
  switch (shape.kind) {
    case "Box":
      return shape.width * shape.length * shape.depth
    case "Sphere":
      return (4 / 3) * Math.PI * shape.radius ** 3
  }
}

// Write your binary search tree implementation here
