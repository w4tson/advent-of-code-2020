export function part01(input : number[], preambleSize: number) {
    var result : number = -1;
    input.forEach((item, index) =>{
        if (index+1 <= preambleSize) {
            return -1
        } else {
            let list = input.slice(index - preambleSize, index);
            if (!constainsTwoNumbers(list, item)) {
                result = item
            }
        }
    });
    
    return result;
}


export function constainsTwoNumbers(list: number[], value: number) : boolean {
    for (let i=0; i<list.length; i++) {
        for (let j=i+1; j<list.length; j++) {
            if (list[i] + list[j] == value) {
                return true
            }
         }
    }
    return false
}

export function findContigousRange(list: number[], sumvalue: number) : number {
    let nums : Array<number>= Array.from(Array(list.length).keys()).map(k => (+k as number));
    for (let i in nums) {
        var found : [number, number] = [0,0];
        nums
            .slice(+i)
            .reduce((acc, curr, index, arr) => {
                if (acc == sumvalue) {
                    arr.splice(1);  // eject early by mutating iterated copy
                    found = [+i, +i+index] 
                } else if (acc > sumvalue) {
                    arr.splice(1);
                }  
                return acc + list[curr];
            }, 0);
        
        if (found[0] !== 0) break; 
    }
    
    let contiguousRange = list.slice(found[0], found[1]);
    return  Math.min.apply(Math, contiguousRange) + Math.max.apply(Math, contiguousRange)
}