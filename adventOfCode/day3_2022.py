# https://adventofcode.com/2022/day/3

def part1(inputLines):
    foundItems = []
    for line in inputLines:
        comp1, comp2 = set(line[:len(line)//2]), (line[len(line)//2:])
        foundItems.extend(char for char in comp1 if char in comp2)

    sum = 0
    for letter in foundItems:
        if letter.isupper():
            sum += ord(letter) - ord('A') + 27
        else:
            sum += ord(letter) - ord('a') + 1
    print(sum)

def part2(inputLines):
    groupedLines = [inputLines[i:i+3] for i in range(0, len(inputLines), 3)]

    overlapping = []
    for group in groupedLines:
        line1, line2, line3 = set(group[0]), set(group[1]), set(group[2])
        overlap = line1 & line2 & line3
        overlap = next(iter(overlap))
        overlapping.append(overlap)

    sum = 0
    for letter in overlapping:
        if letter.isupper():
            sum += ord(letter) - ord('A') + 27
        else:
            sum += ord(letter) - ord('a') + 1
    print(sum)

def main():
    input = open('C:\\Users\\danny\\OneDrive\\Desktop\\Algo\\School-repo\\adventOfCode\\day3_2022.txt')
    inputLines = input.read().splitlines()

    part2(inputLines)

if __name__ == "__main__":
    main()