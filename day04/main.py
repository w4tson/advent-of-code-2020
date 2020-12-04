import re
from functools import reduce


def part01(input):
    passport_strings = input.split("\n\n")
    passports = [to_passport(lines) for lines in passport_strings]
    return sum(1 for p in passports if required_keys(p))


def part02(input):
    passport_strings = input.split("\n\n")
    passports = [to_passport(lines) for lines in passport_strings]
    return sum(1 for p in passports if valid(p))


def valid(passport):
    keys = passport.keys()
    keys_to_validate = list(set(passport.keys()) & set(validation.keys()))
    need_validation = {k: passport[k] for k in keys_to_validate}
    return required_keys(keys) and all([validation[k](v) for k, v in need_validation.items()])


def required_keys(keys):
    return all(elem in keys for elem in validation.keys())


def combine_dicts(acc, line):
    return acc | parse_line(line)


def to_passport(lines):
    return reduce(combine_dicts, lines.splitlines(), {})


def parse_line(line):
    key_values = [to_key_value(keyvalue_str) for keyvalue_str in line.split()]
    return dict(key_values)


def to_key_value(keyvalue_str):
    kv = keyvalue_str.split(':')
    return kv[0], kv[1]


def byr_valid(value):
    return int(value) in range(1920, 2003)


def iyr_valid(value):
    return int(value) in range(2010, 2021)


def eyr_valid(value):
    return int(value) in range(2020, 2031)


def hgt_valid(value):
    match = re.search(r'(\d+)(cm|in)', value)
    if match:
        height = int(match.group(1))
        if match.group(2) == 'in':
            return height in range(59, 77)
        else:
            return height in range(150, 194)
    else:
        return False


def hcl_valid(value):
    return bool(re.match(r'#[0-9a-f]{6}', value))


def pid_valid(value):
    return bool(re.match(r'\d{9}$', value))


def ecl_valid(value):
    return value in ['amb', 'blu', 'brn', 'gry', 'grn', 'hzl', 'oth']


validation = {
    'byr': byr_valid,
    'iyr': iyr_valid,
    'eyr': eyr_valid,
    'hgt': hgt_valid,
    'hcl': hcl_valid,
    'ecl': ecl_valid,
    'pid': pid_valid
}
