# https://adventofcode.com/2022/day/4

def part1(inputLines):
    contained = 0
    for line in inputLines:
        e1 = []
        e2 = []
        e1, e2 = [list(map(int, part.split("-"))) for part in line.split(",")]
        # print (e1, e2)

        if (e1[0] >= e2[0] and e1[1] <= e2[1]) or (e2[0] >= e1[0] and e2[1] <= e1[1]):
            contained += 1
    
    print(f"contained: {contained}")

def part2(inputLines):
    contained = 0
    for line in inputLines:
        e1 = []
        e2 = []
        e1, e2 = [list(map(int, part.split("-"))) for part in line.split(",")]
        
        if (e1[0] >= e2[0] and e1[1] <= e2[1]) or (e2[0] >= e1[0] and e2[1] <= e1[1]):
            contained += 1
        elif e1[0] <= e2[0] <= e1[1]:
            contained += 1
        elif e1[0] <= e2[1] <= e1[1]:
            contained += 1
    print(f"contained: {contained}")

def main():
    input = open('C:\\Users\\danny\\OneDrive\\Desktop\\Algo\\School-repo\\adventOfCode\\day4_2022.txt')
    inputLines = input.read().splitlines()

    part2(inputLines)

if __name__ == "__main__":
    main()
