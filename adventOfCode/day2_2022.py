# https://adventofcode.com/2022/day/2

def part1(inputLines):
    dict = {"A Y": 6, "A X": 3, "A Z": 0, "B Y": 3, "B X": 0, "B Z": 6, "C Y": 0, "C X": 6, "C Z": 3}
    total = 0
    for line in inputLines:
        if line.split()[1] == 'X':
            total += dict[line] + 1
        elif line.split()[1] == 'Y':
            total += dict[line] + 2
        else:
            total += dict[line] + 3
    print(f"total: {total}")

def part2(inputLines):
    # A = ROCK, B = PAPER, C = SCISSORS
    # X = LOSS, Y = DRAW, Z = WIN
    dict = {"A Y": 6, "A X": 3, "A Z": 0, "B Y": 3, "B X": 0, "B Z": 6, "C Y": 0, "C X": 6, "C Z": 3}
    handValue = {"X": 1, "Y": 2, "Z": 3}

    total = 0
    for line in inputLines:
        if line.split()[1] == 'X':
            for key, value in dict.items():
                if value == 0 and key[0] == line.split()[0]:
                    total += 0 + handValue[key[2]]
        if line.split()[1] == 'Y':
            for key, value in dict.items():
                if value == 3 and key[0] == line.split()[0]:
                    total += 3 + handValue[key[2]]
        if line.split()[1] == 'Z':
            for key, value in dict.items():
                if value == 6 and key[0] == line.split()[0]:
                    total += 6 + handValue[key[2]]
    print(total)

def main():
    input = open('C:\\Users\\danny\\OneDrive\\Desktop\\Algo\\School-repo\\adventOfCode\\day2_2022.txt')
    inputLines = input.read().splitlines()

    part2(inputLines)
if __name__ == "__main__":
    main()