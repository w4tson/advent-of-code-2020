import fs from 'fs';
import {constainsTwoNumbers, findContigousRange, part01} from "./app";

const readFile = (path) => fs.readFileSync(path,'utf8')
const nums = [35,20,15,25,47,40,62,55,65,95,102,117,150,182,127,219,299,277,309,576]


const lines = readFile('input.txt').split(/\r?\n/)
describe('Day 8', () => {

    it('should just work TM', () => {
        console.log(lines)
        let oneToTwentyFive = Array.from(Array(25).keys()).map(k => k+1);
        expect(constainsTwoNumbers(oneToTwentyFive, 2)).toBe(false)
        expect(constainsTwoNumbers(oneToTwentyFive, 26)).toBe(true)

    })
    it('example', () => {
        let result = part01(nums, 5)
        expect(result).toBe(127)
    })
    
    it('part1', () => {
        const nums = lines.map(line => +line)
        console.log(part01(nums, 25))
    })
    
    it('part2 example', ()=>{
        let result = findContigousRange(nums, 127)
        expect(result).toBe(62)
    })
    
    it('part2', () => {
        const nums = lines.map(line => +line)
        let result = findContigousRange(nums, 25918798)
        console.log(result)
    })
})
