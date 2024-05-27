# https://adventofcode.com/2022/day/5

def part1(inputLines):
    startingLine = 0
    for i, line in enumerate(inputLines):
        if not line:
            startingLine = i + 1
            break
    # print(startingLine)
    crateLabelLine = startingLine - 2
    # print(crateLabelLine)
    print(inputLines[crateLabelLine])
    columnCount = max(inputLines[crateLabelLine])


    stack = []
    for i in range(crateLabelLine - 1, -1, -1):
        print(inputLines[i])
        for j, char in enumerate(inputLines[i]):
            if char.isalpha():
                if j == 1:
                    column = 0
                else:
                    column = (j - 1) // 4
                print(char, column)
                # stack[i]


def main():
    input = open('C:\\Users\\danny\\OneDrive\\Desktop\\Algo\\School-repo\\adventOfCode\\day5_2022.txt')
    inputLines = input.read().splitlines()

    part1(inputLines)

if __name__ == "__main__":
    main()