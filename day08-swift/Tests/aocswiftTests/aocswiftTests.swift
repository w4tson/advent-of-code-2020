import XCTest
@testable import aocswift

final class aocswiftTests: XCTestCase {
    func testExample() {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct
        // results.
        print("hello")
        
        let path = "/path/to/input.txt"
        
        do {
            let contents = try NSString(contentsOfFile: path,
                                    encoding: String.Encoding.ascii.rawValue) as String
            
            var aoc = Aoc(input: contents)
            aoc.run()
            var part2 = Part2(input: contents)
            
        } catch {
            print("ERRRRRR")
        }
            

        
//        XCTAssertEqual(aocswift().text, "Hello, World!")
    }

    static var allTests = [
        ("testExample", testExample),
    ]
}
