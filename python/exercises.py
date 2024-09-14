from dataclasses import dataclass
from collections.abc import Callable
from typing import Tuple


def change(amount: int) -> dict[int, int]:
    if not isinstance(amount, int):
        raise TypeError('Amount must be an integer')
    if amount < 0:
        raise ValueError('Amount cannot be negative')
    counts, remaining = {}, amount
    for denomination in (25, 10, 5, 1):
        counts[denomination], remaining = divmod(remaining, denomination)
    return counts

# Write your first then lower case function here
def first_then_lower_case(seq, predicate):
    for item in seq:
        if predicate(item):
            return item.lower()
    return None

# Write your powers generator here
def powers_generator(*, base: int, limit: int):
    value = 1
    while value <= limit:
        yield value
        value *= base

# Write your say function here
def say(word = None):
    if 'words' not in say.__dict__:
        say.words = []
    
    if word is None:
        result = ' '.join(say.words)
        say.words = [] 
        return result
    
    say.words.append(word)
    return say

# Write your line count function here
def meaningful_line_count(filename):
    try:
        with open(filename, 'r') as file:
            count = 0
            for line in file:
                line = line.strip()
                if line and not line.startswith('#'):
                    count += 1
        return count
    except FileNotFoundError:
        raise FileNotFoundError(f"No such file: {filename}")

# Write your Quaternion class here
@dataclass(frozen=True)
class Quaternion:
    a: float  
    b: float  
    c: float  
    d: float  

    @property
    def coefficients(self) -> Tuple[float, float, float, float]:
        return (self.a, self.b, self.c, self.d)

    @property
    def conjugate(self) -> 'Quaternion':
        return Quaternion(self.a, -self.b, -self.c, -self.d)

    def __add__(self, other: 'Quaternion') -> 'Quaternion':
        return Quaternion(
            self.a + other.a,
            self.b + other.b,
            self.c + other.c,
            self.d + other.d
        )

    def __mul__(self, other: 'Quaternion') -> 'Quaternion':
        a1, b1, c1, d1 = self.a, self.b, self.c, self.d
        a2, b2, c2, d2 = other.a, other.b, other.c, other.d

        return Quaternion(
            a1*a2 - b1*b2 - c1*c2 - d1*d2,
            a1*b2 + b1*a2 + c1*d2 - d1*c2,
            a1*c2 - b1*d2 + c1*a2 + d1*b2,
            a1*d2 + b1*c2 - c1*b2 + d1*a2
        )

    def __eq__(self, other: 'Quaternion') -> bool:
        return (self.a, self.b, self.c, self.d) == (other.a, other.b, other.c, other.d)

    def __str__(self) -> str:
        parts = []
        if self.a != 0:
            parts.append(f"{self.a}")
        if self.b != 0:
            if self.b == 1:
                parts.append("i")
            elif self.b == -1:
                parts.append("-i")
            else:
                parts.append(f"{self.b}i")
        if self.c != 0:
            if self.c == 1:
                parts.append("j")
            elif self.c == -1:
                parts.append("-j")
            else:
                parts.append(f"{self.c}j")
        if self.d != 0:
            if self.d == 1:
                parts.append("k")
            elif self.d == -1:
                parts.append("-k")
            else:
                parts.append(f"{self.d}k")

        if not parts:
            return "0"

        return "+".join(parts).replace("+-", "-")