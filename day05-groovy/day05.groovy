def readFromFile(file) {
    (file as File).readLines().collect()
}

def binarySearch(instructions, seats, lowerChar ) {
    def n = seats.size /2
    if (seats.size == 1) {
        return seats[0]
    } else  {
        def lower = instructions[0] == lowerChar ? 0 : n
        def upper = instructions[0] == lowerChar ? n : seats.size
        return binarySearch(instructions.drop(1), seats[lower..<upper], lowerChar)
    }
}

assert binarySearch('FBFBBFFRLR', (0..127).collect(), 'F') == 44
assert binarySearch('RLR', (0..7).collect(), 'L') == 5

def calcSeatNumber(seat) {
    def row = binarySearch(seat.take(7), (0..127).collect(), 'F')
    def col = binarySearch(seat.drop(7), (0..7).collect(), 'L')
    return row * 8 + col
}

assert calcSeatNumber('FBFBBFFRLR') == 357

def boardingPasses = readFromFile('input.txt')
def seatNumbers = boardingPasses.collect { calcSeatNumber(it) }

def maxSeatNumber = seatNumbers.max()
println "part01: $maxSeatNumber"
def sortedSeatNumbers = seatNumbers.sort()


for (n in 0..<sortedSeatNumbers.size-1) {
    (a,b) = sortedSeatNumbers[n..n+1]
    if (b-a != 1) println "Part02: ${b-1}"
}
