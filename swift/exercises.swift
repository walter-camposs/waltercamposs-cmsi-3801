import Foundation

struct NegativeAmountError: Error {}
struct NoSuchFileError: Error {}

func change(_ amount: Int) -> Result<[Int:Int], NegativeAmountError> {
    if amount < 0 {
        return .failure(NegativeAmountError())
    }
    var (counts, remaining) = ([Int:Int](), amount)
    for denomination in [25, 10, 5, 1] {
        (counts[denomination], remaining) = 
            remaining.quotientAndRemainder(dividingBy: denomination)
    }
    return .success(counts)
}

func firstThenLowerCase(of array: [String], satisfying predicate: (String) -> Bool) -> String? {
    return array.first(where: predicate)?.lowercased()
}

class ChainablePhrase {
    private var words: [String] = []

    init(_ initialWord: String) {
        words.append(initialWord)
    }

    func and(_ word: String) -> ChainablePhrase {
        let newPhrase = ChainablePhrase(self.phrase)
        newPhrase.words.append(word)
        return newPhrase
    }

    var phrase: String {
        return words.joined(separator: " ")
    }
}

func say(_ word: String = "") -> ChainablePhrase {
    return ChainablePhrase(word)
}


func meaningfulLineCount(_ filename: String) async -> Result<Int, Error> {
    do {
        let fileURL = URL(fileURLWithPath: filename)
        
        let fileContents = try String(contentsOf: fileURL, encoding: .utf8)
        
        let lines = fileContents.components(separatedBy: .newlines)

        let validLinesCount = lines.reduce(0) { count, line in
            let trimmedLine = line.trimmingCharacters(in: .whitespacesAndNewlines)
            return (trimmedLine.isEmpty || trimmedLine.hasPrefix("#")) ? count : count + 1
        }
        
        return .success(validLinesCount)

    } catch {
        return .failure(error)
    }
}

struct Quaternion: Equatable, CustomStringConvertible {
    let a: Double 
    let b: Double 
    let c: Double 
    let d: Double 
    
    static let ZERO = Quaternion(a: 0, b: 0, c: 0, d: 0)
    static let I = Quaternion(a: 0, b: 1, c: 0, d: 0)
    static let J = Quaternion(a: 0, b: 0, c: 1, d: 0)
    static let K = Quaternion(a: 0, b: 0, c: 0, d: 1)
    
    init(a: Double = 0, b: Double = 0 , c: Double = 0, d: Double = 0) {
        self.a = a
        self.b = b
        self.c = c
        self.d = d
    }

    
    var coefficients: [Double] {
        return [self.a, self.b, self.c, self.d]
    }
    
    var conjugate: Quaternion {
        return Quaternion(a: self.a, b: -self.b, c: -self.c, d: -self.d)
    }
    
    static func == (lhs: Quaternion, rhs: Quaternion) -> Bool {
        return lhs.a == rhs.a && lhs.b == rhs.b && lhs.c == rhs.c && lhs.d == rhs.d
    }
    
    static func + (lhs: Quaternion, rhs: Quaternion) -> Quaternion {
        return Quaternion(a: lhs.a + rhs.a, b: lhs.b + rhs.b, c: lhs.c + rhs.c, d: lhs.d + rhs.d)
    }
    
    static func * (lhs: Quaternion, rhs: Quaternion) -> Quaternion {
        return Quaternion(
            a: lhs.a * rhs.a - lhs.b * rhs.b - lhs.c * rhs.c - lhs.d * rhs.d,
            b: lhs.a * rhs.b + lhs.b * rhs.a + lhs.c * rhs.d - lhs.d * rhs.c,
            c: lhs.a * rhs.c - lhs.b * rhs.d + lhs.c * rhs.a + lhs.d * rhs.b,
            d: lhs.a * rhs.d + lhs.b * rhs.c - lhs.c * rhs.b + lhs.d * rhs.a
        )
    }
    
    var description: String {
        var parts: [String] = []

        if self.a == 0 && self.b == 0 && self.c == 0 && self.d == 0 {
            return "0"
        }

        if self.a != 0 || (self.b == 0 && self.c == 0 && self.d == 0) {
            parts.append("\(self.a)")
        }

        if self.b != 0 {
            if self.b == 1 {
                parts.append("+i")
            } else if self.b == -1 {
                parts.append("-i")
            } else {
                parts.append(self.b > 0 ? "+\(self.b)i" : "\(self.b)i")
            }
        }

        if self.c != 0 {
            if self.c == 1 {
                parts.append("+j")
            } else if self.c == -1 {
                parts.append("-j")
            } else {
                parts.append(self.c > 0 ? "+\(self.c)j" : "\(self.c)j")
            }
        }

        if self.d != 0 {
            if self.d == 1 {
                parts.append("+k")
            } else if self.d == -1 {
                parts.append("-k")
            } else {
                parts.append(self.d > 0 ? "+\(self.d)k" : "\(self.d)k")
            }
        }

        let result = parts.joined()
        return result.first == "+" ? String(result.dropFirst()) : result
    }
}

enum BinarySearchTree: CustomStringConvertible {
    case empty
    indirect case node(String, BinarySearchTree, BinarySearchTree)

    var size: Int {
        switch self {
        case .empty:
            return 0
        case let .node(_, left, right):
            return 1 + left.size + right.size
        }
    }

    func insert(_ value: String) -> BinarySearchTree {
        switch self {
        case .empty:
            return .node(value, .empty, .empty)
        case let .node(existingValue, left, right):
            if value < existingValue {
                return .node(existingValue, left.insert(value), right)
            } else if value > existingValue {
                return .node(existingValue, left, right.insert(value))
            } else {
                return self
            }
        }
    }

    func contains(_ value: String) -> Bool {
        switch self {
        case .empty:
            return false
        case let .node(existingValue, left, right):
            if value == existingValue {
                return true
            } else if value < existingValue {
                return left.contains(value)
            } else {
                return right.contains(value)
            }
        }
    }
    
    var description: String {
        switch self {
        case .empty:
            return "()" 
        case let .node(value, .empty, .empty):
            return "(\(value))" 
        case let .node(value, left, .empty):
            return "(\(left)\(value))" 
        case let .node(value, .empty, right):
            return "(\(value)\(right))" 
        case let .node(value, left, right):
            return "(\(left)\(value)\(right))" 
        }
    }
}
