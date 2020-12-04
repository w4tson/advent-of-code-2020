from functools import reduce

fields = ['byr', 'iyr', 'eyr', 'hgt', 'hcl', 'ecl', 'pid']


def part01(input):
    passport_strings = input.split("\n\n")

    passports = [to_passport(lines) for lines in passport_strings]
    # print(passports)

    return sum(1 for p in passports if valid(p))


def valid(passport):
    keys = passport.keys()
    return all(elem in keys for elem in fields)


def combine_dicts(acc, line):
    return acc | parse_line(line)


def to_passport(lines):
    # z = x | y
    return reduce(combine_dicts, lines.splitlines(), {})


def parse_line(line):
    key_values = [to_key_value(keyvalue_str) for keyvalue_str in line.split()]
    return dict(key_values)


def to_key_value(keyvalue_str):
    kv = keyvalue_str.split(':')
    return kv[0], kv[1]
