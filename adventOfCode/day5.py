
def part1(inputLines):
    l = len(inputLines)
    seeds = list(map(int, inputLines[0].split()[1:]))
    # print(f"seeds: {seeds}")

    inputMaps = []
    for i in range(1, l):
        if inputLines[i] and inputLines[i][0].isalpha():
            inputMaps.append(i)
    inputMaps.append(l + 1)
    # print(f"map locations: {inputMaps}")

    seedconverted = []
    for seed in seeds:
        for i in range(len(inputMaps) - 1):
            Map = getMap(inputMaps[i], inputMaps[i + 1], inputLines)
            for mapLine in Map:
                if seed >= mapLine[1] and seed < (mapLine[1] + mapLine[2]):
                    seed = seed - mapLine[1] + mapLine[0]
                    break
        seedconverted.append(seed)
        print(f"converted: {seedconverted}")
    print(f"lowest location: {min(seedconverted)}")

def part2(inputLines):
    l = len(inputLines)
    seeds = list(map(int, inputLines[0].split()[1:]))

    inputMaps = []
    for i in range(1, l):
        if inputLines[i] and inputLines[i][0].isalpha():
            inputMaps.append(i)
    inputMaps.append(l + 1)

    smallestSeed = float('inf')
    for j, seed in enumerate(seeds):
        if j % 2 == 1:
            continue
        seedIncrement = seeds[j + 1]
        seedMax = seed + seedIncrement - 1
        unconvertedSeeds = []
        unconvertedSeeds.append((seed, seedIncrement))
        # print(f"unconverted seeds: {unconvertedSeeds}")
        convertedSeeds = []
        for i in range(len(inputMaps) - 1):
            Map = getMap(inputMaps[i], inputMaps[i + 1], inputLines)
            for mapLine in Map:
                # print(f"map line: {mapLine}")
                # print(f"seed: {seed} seed max: {seedMax} seed increment: {seedIncrement} mapline: {mapLine}")
                leftoverSeedsFromRow = []
                for uSeed in unconvertedSeeds:
                    # print(f"unconverted seed: {uSeed}")
                    convertableSeeds, leftoverSeeds = overlappingSeeds(uSeed[0], uSeed[1], mapLine[1], mapLine[2])
                    leftoverSeeds = [value for value in leftoverSeeds if value is not None]
                    numLeftoverSeeds = len(leftoverSeeds)//2
                    # print(f"num leftover seeds: {numLeftoverSeeds}")
                    if convertableSeeds[0] is not None:
                        convertedSeedTemp = (convertableSeeds[0] - mapLine[1] + mapLine[0], convertableSeeds[1])
                        convertedSeeds.append(convertedSeedTemp)
                        # print(f"converted seed: {convertedSeed}")
                    if numLeftoverSeeds > 1:         
                        leftoverSeedsFromRow.append((leftoverSeeds[0], leftoverSeeds[1]))
                        leftoverSeedsFromRow.append((leftoverSeeds[2], leftoverSeeds[3]))
                        # print(f"leftover seeds from row: {leftoverSeedsFromRow}")

                    elif numLeftoverSeeds == 1:
                        leftoverSeedsFromRow.append((leftoverSeeds[0], leftoverSeeds[1]))
                        # print(f"leftover seeds from row: {leftoverSeedsFromRow}")

                # print(f"all current seeds: converted: {convertedSeed} leftover: {leftoverSeedsFromRow}\n")
                unconvertedSeeds = leftoverSeedsFromRow
                    
            # print(f"Map {i + 1} outputs: converted: {convertedSeeds}, unconverted: {unconvertedSeeds}\n")
            unconvertedSeeds = convertedSeeds + unconvertedSeeds
            convertedSeeds = []
        if min(unconvertedSeeds)[0] < smallestSeed:
            smallestSeed = min(unconvertedSeeds)[0]
        print(f"smallest seed location up to seed batch # {1 + j // 2}: {smallestSeed}\n")

def overlappingSeeds(inputseed, inputInc, mapSeed, mapInc):
    if inputseed > mapSeed + mapInc - 1 or inputseed + inputInc - 1 < mapSeed:
        return (None, None), (inputseed, inputInc, None, None)
    inputSeedMax = inputseed + inputInc - 1
    mapSeedMax = mapSeed + mapInc - 1
    overlappingStart = max(inputseed, mapSeed)
    overlappingEnd = min(inputSeedMax, mapSeedMax)
    overlappingInc = overlappingEnd - overlappingStart + 1
    leftoverLeftSeed = None
    leftoverLeftInc = None
    leftoverRightSeed = None
    leftoverRightInc = None

    if inputseed < mapSeed: #leftover to the left of map
        leftoverLeftSeed = inputseed
        leftoverLeftInc = mapSeed - leftoverLeftSeed
    if inputSeedMax > overlappingEnd: #leftover to the right
        leftoverRightSeed = mapSeedMax + 1
        leftoverRightInc = inputSeedMax - leftoverRightSeed + 1

    convertableSeeds = (overlappingStart, overlappingInc)
    leftoverSeeds = (leftoverLeftSeed, leftoverLeftInc, leftoverRightSeed, leftoverRightInc)
    return (convertableSeeds, leftoverSeeds)
            
def getMap(mapStart, nextMap, inputLines):
    Map = []
    for i in range(mapStart + 1, nextMap - 1):
        Map.append(list(map(int, inputLines[i].split())))
    return Map

def main():
    input = open('C:\\Users\\danny\\OneDrive\\Desktop\\Algo\\School-repo\\adventOfCode\\day5.txt')
    inputLines = input.read().splitlines()
    
    # # print input
    # for i in range(len(inputLine)):
    #     print(inputLine[i])

    part2(inputLines)
    
if __name__ == "__main__":
    main()