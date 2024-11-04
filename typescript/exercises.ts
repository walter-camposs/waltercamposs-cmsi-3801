import { open } from "node:fs/promises"

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

  const fileHandle = await open(filePath, "r")
  for await (const line of fileHandle.readLines()) {
    if (line.trim() && !line.trim().startsWith("#")) {
      validLineCount++
    }
  }
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

export interface BinarySearchTree<T> {
  size(): number
  insert(value: T): BinarySearchTree<T>
  contains(value: T): boolean
  inorder(): Iterable<T>
  toString(): string
}

export class Empty<T> implements BinarySearchTree<T> {
  size(): number {
    return 0
  }

  insert(value: T): BinarySearchTree<T> {
    return new Node<T>(value, new Empty(), new Empty())
  }

  contains(value: T): boolean {
    return false
  }

  *inorder(): Iterable<T> {}

  toString(): string {
    return "()"
  }
}

class Node<T> implements BinarySearchTree<T> {
  private value: T
  private left: BinarySearchTree<T>
  private right: BinarySearchTree<T>
  private treeSize: number

  constructor(value: T, left: BinarySearchTree<T>, right: BinarySearchTree<T>) {
    this.left = left
    this.right = right
    this.value = value
    this.treeSize = 1 + left.size() + right.size()
  }

  size(): number {
    return this.treeSize
  }

  insert(value: T): BinarySearchTree<T> {
    if (value < this.value) {
      return new Node(this.value, this.left.insert(value), this.right)
    } else if (value > this.value) {
      return new Node(this.value, this.left, this.right.insert(value))
    } else {
      return this
    }
  }

  contains(value: T): boolean {
    if (value < this.value) {
      return this.left.contains(value)
    } else if (value > this.value) {
      return this.right.contains(value)
    } else {
      return true
    }
  }

  *inorder(): Iterable<T> {
    yield* this.left.inorder()
    yield this.value
    yield* this.right.inorder()
  }

  toString(): string {
    return `(${this.left.toString().replace("()", "")}${this.value}${this.right
      .toString()
      .replace("()", "")})`
  }
}
