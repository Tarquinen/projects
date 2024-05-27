# https://adventofcode.com/2023/day/6

import time

def part1(inputLines): 
    times = inputLines[0].split()[1:]
    distances = inputLines[1].split()[1:]
    print(f"times: {times}, distances: {distances}")
    recordBreakMultiply = 1

    for i in range(len(times)):
        time = int(times[i])
        distance = int(distances[i])
        recordBreakCounter = 0
        for speed in range(1, time):
            # print(f" speed: {speed}, time {time}, distance: {distance}")
            timeAfterButtonPress = time - speed
            if speed * timeAfterButtonPress > distance:
                recordBreakCounter += 1
        print(f"recordBreakCounter: {recordBreakCounter}")
        recordBreakMultiply *= recordBreakCounter
    print(f"recordBreakMultiply: {recordBreakMultiply}")

def part2(inputLines):
    startTime = time.time()
    raceTime = inputLines[0].split()[1:]
    raceTime = int(''.join(map(str, raceTime)))
    distance = inputLines[1].split()[1:]
    distance = int(''.join(map(str, distance)))
    print(f"time: {raceTime}, distances: {distance}")
    recordBreakCounter = 0
    for speed in range(1, raceTime):
        timeAfterButtonPress = raceTime - speed
        if speed * timeAfterButtonPress > distance:
            recordBreakCounter += 1
        if speed % 1000000 == 0:
            print(f"speed: {speed}")
            print(f"recordBreakCounter: {recordBreakCounter}")

    endTime = time.time()
    execution_time_ms = (endTime - startTime) * 1000
    print(f"The code took {execution_time_ms} milliseconds to evaluate.")
    print(f"recordBreakCounter: {recordBreakCounter}")


def part2WithoutTrash(inputLines):
    # 48,989,083
    # 390,110,311,121,360
    startTime = time.time()
    raceTime = inputLines[0].split()[1:]
    raceTime = int(''.join(map(str, raceTime)))
    distance = inputLines[1].split()[1:]
    distance = int(''.join(map(str, distance)))
    print(f"time: {raceTime}, distances: {distance}")
    recordBreakCounter = 0
    for speed in range(raceTime//2, raceTime):
        timeAfterButtonPress = raceTime - speed
        if speed * timeAfterButtonPress > distance:
            recordBreakCounter += 1
        else:
            break
        if speed % 1000000 == 0:
            print(f"speed: {speed}")
            print(f"recordBreakCounter: {recordBreakCounter}")
    for speed in range(raceTime//2 - 1, 1, -1):
        timeAfterButtonPress = raceTime - speed
        if speed * timeAfterButtonPress > distance:
            recordBreakCounter += 1
        else:
            break
        if speed % 1000000 == 0:
            print(f"speed: {speed}")
            print(f"recordBreakCounter: {recordBreakCounter}")
    endTime = time.time()
    execution_time_ms = (endTime - startTime) * 1000
    print(f"The code took {execution_time_ms} milliseconds to evaluate.")
    print(f"recordBreakCounter: {recordBreakCounter}")

def part2BinarySearch(inputLines):
    # 48,989,083
    # 390,110,311,121,360
    startTime = time.time()
    raceTime = inputLines[0].split()[1:]
    raceTime = int(''.join(map(str, raceTime)))
    distance = inputLines[1].split()[1:]
    distance = int(''.join(map(str, distance)))
    print(f"time: {raceTime}, distances: {distance}")
    topIndex = raceTime + 1
    bottomIndex = 1
    topSpeed = 0
    bottomSpeed = 0

    while True: #find top speed that works
        searchSpeed = (topIndex + bottomIndex) // 2
        # print(f"topIndex: {topIndex}, bottomIndex: {bottomIndex}, searchSpeed: {searchSpeed}")
        timeAfterButtonPress = raceTime - searchSpeed
        if topIndex == bottomIndex + 1:
            print(f"top speed: {searchSpeed}")
            topSpeed = searchSpeed
            break
        if searchSpeed * timeAfterButtonPress > distance:
            bottomIndex = searchSpeed + 1
        else:
            topIndex = searchSpeed - 1
    
    topIndex = raceTime + 1
    bottomIndex = 1

    while True: #find bottom speed that works
        searchSpeed = (topIndex + bottomIndex) // 2
        # print(f"topIndex: {topIndex}, bottomIndex: {bottomIndex}, searchSpeed: {searchSpeed}")
        timeAfterButtonPress = raceTime - searchSpeed
        if topIndex == bottomIndex + 1:
            print(f"bottom speed: {searchSpeed}")
            bottomSpeed = searchSpeed
            break
        if searchSpeed * timeAfterButtonPress > distance:
            topIndex = searchSpeed - 1
        else:
            bottomIndex = searchSpeed + 1
    endTime = time.time()
    execution_time_ms = (endTime - startTime) * 1000
    print(f"The code took {execution_time_ms} milliseconds to evaluate.")
    print(f"Total valid speeds: {topSpeed - bottomSpeed + 1}")



def main():
    input = open('C:\\Users\\danny\\OneDrive\\Desktop\\Algo\\School-repo\\adventOfCode\\day6.txt')
    inputLines = input.read().splitlines()
    

    part2BinarySearch(inputLines)

if __name__ == "__main__":
    main()
