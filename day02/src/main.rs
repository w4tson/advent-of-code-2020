use crate::day1::{part1, part2};

mod day1;

fn main() {
    let input = include_str!("input.txt");
    
    let count = part1(input);
    eprintln!("Part1 = {:#?}", count);

    let count = part2(input);
    eprintln!("Part2 = {:#?}", count);
}
