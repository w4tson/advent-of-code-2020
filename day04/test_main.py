import textwrap
from unittest import TestCase

from main import part01, to_key_value, parse_line, to_passport, valid


def get_input(name):
    with open(name, "r") as myfile:
        return myfile.read()


class Test(TestCase):
    def test_part01(self):
        input = get_input('input.txt')
        print(part01(input))
        
    def test_testpart01(self):
        input = get_input('testinput.txt')
        assert part01(input) == 2

    def test_kv(self):
        assert to_key_value('ecl:gry') == ('ecl', 'grygry')
        assert to_key_value('pid:860033327') == ('pid', '860033327')

    def test_parse_values(self):
        assert parse_line('hcl:#cfa07d byr:1929') == {'hcl': '#cfa07d', 'byr': '1929'}

    def test_to_passport(self):
        input = """ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
byr:1937 iyr:2017 cid:147 hgt:183cm"""
        print(".{}.".format(input))
        res = to_passport(input)
        print(res)

    def test_valid(self):
        testinput = [{'ecl': 'gry', 'pid': '860033327', 'eyr': '2020', 'hcl': '#fffffd', 'byr': '1937', 'iyr': '2017',
                      'cid': '147', 'hgt': '183cm'},
                     {'iyr': '2013', 'ecl': 'amb', 'cid': '350', 'eyr': '2023', 'pid': '028048884', 'hcl': '#cfa07d',
                      'byr': '1929'},
                     {'hcl': '#ae17e1', 'iyr': '2013', 'eyr': '2024', 'ecl': 'brn', 'pid': '760753108', 'byr': '1931',
                      'hgt': '179cm'},
                     {'hcl': '#cfa07d', 'eyr': '2025', 'pid': '166559648', 'iyr': '2011', 'ecl': 'brn', 'hgt': '59in'}]
        assert valid(testinput[0]) == True
        assert valid(testinput[1]) == False
        assert valid(testinput[2]) == True
        assert valid(testinput[3]) == False
        
