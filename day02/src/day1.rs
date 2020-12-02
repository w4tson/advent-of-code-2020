use std::str::{FromStr};

pub fn part1(str: &str) -> usize {
    str.lines()
        .map(|line| line.parse().unwrap())
        .filter(|pd: &PasswordDump| pd.is_valid())
        .count()
}

pub fn part2(str: &str) -> usize {
    str.lines()
        .map(|line| line.parse().unwrap())
        .filter(|pd: &PasswordDump| pd.is_valid2())
        .count()
}


struct PasswordDump {
    password: String,
    policy: Policy
}


impl PasswordDump {
    pub fn is_valid(&self) -> bool {
        let count = self.password.chars().filter(|c| c == &self.policy.letter).count();
        count >= self.policy.min && count <= self.policy.max
    }
    
    pub fn is_valid2(&self) -> bool {
        let c1 = self.password.chars().nth(self.policy.min-1).expect("index not found for c1");
        let c2 = self.password.chars().nth(self.policy.max-1).expect("index not found for c2");
        
        (c1 != c2) && (c1 == self.policy.letter || c2 == self.policy.letter)
    }
}


struct Policy {
    min: usize,
    max: usize,
    letter: char
}

impl FromStr for PasswordDump {
    type Err = ();

    fn from_str(s: &str) -> Result<Self, Self::Err> {
        let pwd_dump = s.split(": ").collect::<Vec<&str>>();
        let policy_str = pwd_dump[0].trim().split(" ").collect::<Vec<&str>>();
        let mut min_max = policy_str[0].split("-");
        let min = min_max.next().unwrap().parse::<usize>().unwrap();
        let max = min_max.next().unwrap().parse::<usize>().unwrap();
        let letter = policy_str[1].chars().next().unwrap();
        
        Ok(PasswordDump{
            password: pwd_dump[1].to_string(),
            policy : Policy{ min, max, letter }
        })
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    
    #[test]
    fn test() {
        let p : PasswordDump = "1-3 c: abc".parse().unwrap();
        
        assert_eq!("abc", p.password);
        assert_eq!('c', p.policy.letter);
        assert_eq!(1, p.policy.min);
        assert_eq!(3, p.policy.max);
    }
    
    #[test]
    fn part1() {
        let p : PasswordDump = "1-3 a: abcde".parse().unwrap();
        assert_eq!(true, p.is_valid());
        
        let p : PasswordDump = "1-3 b: cdefg".parse().unwrap();
        assert_eq!(false, p.is_valid());
        
        
        let p : PasswordDump = "2-9 c: ccccccccc".parse().unwrap();
        assert_eq!(true, p.is_valid());
    }

    #[test]
    fn part2() {
        let p : PasswordDump = "1-3 a: abcde".parse().unwrap();
        assert_eq!(true, p.is_valid2());

        let p : PasswordDump = "1-3 b: cdefg".parse().unwrap();
        assert_eq!(false, p.is_valid2());


        let p : PasswordDump = "2-9 c: ccccccccc".parse().unwrap();
        assert_eq!(false, p.is_valid2());
    }
}