# https://adventofcode.com/2023/day/12

def part1(inputLines):
    chart1 = []
    chart2 = []
    for line in inputLines:
        chart1, chart2 = line.split()
        # chart1 = chart1.split('.')
        chart2 = chart2.split(',')
        chart2 = [int(x) for x in chart2]

        subStringStart = 0
        startOver = True
        chart1Mod = []
        # print(f"chart1 length: {len(chart1)}")
        for i, char in enumerate(chart1):
            if char != '.':
                if startOver:
                    subStringStart = i
                    startOver = False
            if char == '.':
                if startOver == False:
                    chart1Mod.append(chart1[subStringStart:i])
                    startOver= True 
            if i == len(chart1) - 1 and char != '.':
                chart1Mod.append(chart1[subStringStart:i + 1])

        chart1 = chart1Mod
        print(chart1, chart2)

    occupied = []
    usedAllNumbers = False
    for num in chart2:
        for i, c in enumerate(chart1):
            if i not in occupied:
                if num <= len(c):
                    print(f"{num} fits {len(c) - num + 1} ways into {c}")
                    occupied.append(i)
                    break
    print(f"occupied: {occupied} \n")
                # go to next num

    currentPosNum = 1
    availElement, availCoord = coordToArrayPos(currentPosNum, chart1)
    for num in chart2:
        availElement, availCoord = coordToArrayPos(currentPosNum, chart1)
        print(f"available element: {availElement}, available coord: {availCoord}, Pos Num: {currentPosNum}")
        print(f"num: {num}")
        if len(chart1[availElement]) >= num - availCoord:
            print("fits\n")
            currentPosNum += num + 1


    print(coordToArrayPos(2, chart1))



def coordToArrayPos(coord, chart):
    chartPos = 0
    for i, element in enumerate(chart):
        for j in range(len(element)):
            chartPos += 1
            if chartPos == coord:
                return i, j
    return 0, 0


    


def main():
    input = open('C:\\Users\\danny\\OneDrive\\Desktop\\Algo\\School-repo\\adventOfCode\\day12.txt')
    inputLines = input.read().splitlines()

    
    part1(inputLines)
    
if __name__ == "__main__":
    main()