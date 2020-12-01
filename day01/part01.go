package day01

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

func Solve(solver func([]int) int) int {
	file, err := os.Open("./input.txt")
	check(err)
	defer file.Close()

	scanner := bufio.NewScanner(file)
	var i = 0

	var inputs [200]int

	for scanner.Scan() {
		lineStr := scanner.Text()
		mass, _ := strconv.Atoi(lineStr)
		inputs[i] = mass
		i++;
	}

	solver(inputs[:])

	fmt.Println(inputs)



	return 0
}

func Part01(input []int) int {
	var size = len(input)
	for i:=0; i <size; i++ {
		for j:=i+1; j<size; j++ {
			if input[i] + input[j] == 2020 {
				fmt.Println(input[i], input[j])
				fmt.Println(input[i] * input[j])
				return input[i] * input[j]
			}
		}
	}

	return -1
}

func Part02(input []int) int {
	var size = len(input)
	for i:=0; i <size; i++ {
		for j:=i+1; j<size; j++ {
			for k:=j+1; k<size; k++ {
				if input[i]+input[j]+ input[k] == 2020 {
					fmt.Println(input[i], input[j], input[k])
					result := input[i] * input[j] * input[k]
					fmt.Println(result)
					return result
				}
			}
		}
	}

	return -1
}


func check(e error) {
	if e != nil {
		panic(e)
	}
}

