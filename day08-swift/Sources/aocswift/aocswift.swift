import Foundation

enum Instruction {
    case Nop
    case Acc(amount : Int)
    case Jmp(value: Int)
}

class Aoc {
    var input : String
    var instructions : [Instruction]
    var p = 0
    var visited : [Int] = []
    var acc = 0
    var finalInstruction : Int
    
    init(input: String) {
        self.input = input
        let lines = input.split(separator: "\n")
        self.finalInstruction = lines.count
        self.instructions = lines.map({ (line) in
            var inst : Instruction = .Nop
            let nameValue = line.split(separator: " ")
            let value = Int(nameValue[1]).unsafelyUnwrapped
            switch nameValue[0] {
                case "nop": inst = Instruction.Nop
                case "acc": inst = Instruction.Acc(amount: value)
                case "jmp": inst = Instruction.Jmp(value: value)
                default: print("*** ERROR")
            }
            return inst
        })
    }
    
    //returns false if in infinite loop
    func run() -> Bool {
        while (true) {
            self.visited.append(p)
        
            switch self.instructions[p] {
                case .Nop: ()
                case .Acc(let amount): acc+=amount
                case .Jmp(let value): p = p+value-1
            }
            p+=1
            
            if self.visited.contains(p) {
                print(acc)
                return false
            }
            
            if p == self.finalInstruction {
                print(acc)
                return true
            }
        }
        
        return false
    }
}

class Part2 {
    
    var input: String
    init(input: String) {
        self.input = input
        print("part 2")
        let lines = input.split(separator: "\n")
        let jmpsAndNops = (0...lines.count-1).filter({ i in
            let inst = lines[i]
            return inst.starts(with: "jmp") || inst.starts(with: "nop")
        }).map({ index -> Aoc in
            var newLines = input.split(separator: "\n").map { String($0) }
            if newLines[index].starts(with: "jmp") {
                newLines[index] = String(newLines[index].replacingOccurrences(of: "jmp", with: "nop"))
            } else {
                newLines[index] = String(newLines[index].replacingOccurrences(of: "nop", with: "jmp"))
            }
            return Aoc(input: newLines.joined(separator: "\n"))
        }).drop { (aoc) -> Bool in
            !aoc.run()
        }
    }
}
