import textwrap
from unittest import TestCase

from main import part01, to_key_value, parse_line, to_passport, valid, byr_valid, hgt_valid, hcl_valid, pid_valid, \
    ecl_valid, iyr_valid, part02


def get_input(name):
    with open(name, "r") as myfile:
        return myfile.read()


class Test(TestCase):
    def test_part01(self):
        input = get_input('input.txt')
        print(part01(input))

    def test_part02(self):
        input = get_input('input.txt')
        print(part02(input))

    def test_testpart01(self):
        input = get_input('testinput.txt')
        assert part01(input) == 2

    def test_kv(self):
        assert to_key_value('ecl:gry') == ('ecl', 'gry')
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

    def test_byr_valid(self):
        assert byr_valid('1919') == False
        assert byr_valid('1920') == True
        assert byr_valid('2002') == True
        assert byr_valid('2003') == False

    def test_hgt_valid(self):
        # assert hgt_valid('123in') == False
        assert hgt_valid('58in') == False
        assert hgt_valid('59in') == True
        assert hgt_valid('76in') == True
        assert hgt_valid('74in') == True
        assert hgt_valid('77in') == False

        assert hgt_valid('149cm') == False
        assert hgt_valid('150cm') == True
        assert hgt_valid('193cm') == True
        assert hgt_valid('194cm') == False

    def test_hcl_valid(self):
        assert hcl_valid('#111fff') == True
        assert hcl_valid('#111ffg') == False
        assert hcl_valid('#111ff') == False

    def test_pid_valid(self):
        assert pid_valid('000000001') == True
        assert pid_valid('0123456789') == False

    def test_ecl_valid(self):
        assert ecl_valid('amb') == True
        assert ecl_valid('blu') == True
        assert ecl_valid('asdf') == False

    def test_valid(self):
        input = to_passport("pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980 hcl:#623a2f")
        assert valid(input) == True
        input = to_passport("eyr:2029 ecl:blu cid:129 byr:1989 iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm")
        assert valid(input) == True
