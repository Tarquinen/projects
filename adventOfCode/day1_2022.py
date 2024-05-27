# https://adventofcode.com/2022/day/1

def part1(inputLines):
    totals = []
    tempTotal = 0
    for line in inputLines:
        if line:
            tempTotal += int(line)
        else:
            totals.append(tempTotal)
            tempTotal = 0
    print(max(totals))
    
def part2(inputLines):
    totals = []
    tempTotal = 0
    for line in inputLines:
        if line:
            tempTotal += int(line)
        else:
            totals.append(tempTotal)
            tempTotal = 0

    top3 = 0
    for _ in range(3):
        top3 += totals.pop(totals.index(max(totals)))

    print(top3)

def main():
    input = open('C:\\Users\\danny\\OneDrive\\Desktop\\Algo\\School-repo\\adventOfCode\\day1_2022.txt')
    inputLines = input.read().splitlines()

    part2(inputLines)
if __name__ == "__main__":
    main()