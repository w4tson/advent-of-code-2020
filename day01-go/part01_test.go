package day01

import (
	"github.com/stretchr/testify/assert"
	"testing"
)

func Test(t *testing.T) {
	Solve(Part01)
	Solve(Part02)
}

var testInput = []int { 1721, 979, 366, 299, 675, 1456 }

func TestSolvePart1(t *testing.T) {
	a:= Part01(testInput)
	assert.Equal(t, 514579, a)
}

func TestSolvePart2(t *testing.T) {
	a:= Part02(testInput)
	assert.Equal(t, 241861950, a)
}
