from collections import defaultdict

def part1(inputLine):
    totalPoints = 0;
    for i in range(len(inputLine)):
        fixedList = inputLine[i].split(": ")[1]
        myCards = fixedList.split(' |')[0].split()
        winningNumbers = fixedList.split("| ")[1].split()
        cardSetCounter = 0
        for card in myCards:
            if card in winningNumbers:
                cardSetCounter += 1
        points = int(2 ** (cardSetCounter - 1))
        totalPoints += points
        #print(f"there are {cardSetCounter} winning cards in the {i + 1} card set worth {points} points")
    
    print(f"The total points are {totalPoints}")


def part2(inputLine):
    cardDict = defaultdict(int)
    for i in range(len(inputLine)):
        fixedList = inputLine[i].split(": ")[1]
        myCards = set(fixedList.split(' |')[0].split())
        winningNumbers = set(fixedList.split("| ")[1].split())
        matchesInRowCounter = 1
        for card in myCards:
            if card in winningNumbers:
                if (i + matchesInRowCounter) < len(inputLine):
                    cardDict[i + matchesInRowCounter] += cardDict[i] + 1;
                matchesInRowCounter += 1
    #print(cardDict)
    print(f"total cards: {sum(cardDict.values()) + len(inputLine)}")

def main():
    input = open('C:\\Users\\danny\\OneDrive\\Desktop\\Algo\\School-repo\\adventOfCode\\day4.txt')
    inputLine = input.read().splitlines()
    
    # print input
    # for i in range(len(inputLine)):
    #     print(inputLine[i])

    part2(inputLine)

if __name__ == "__main__":
    main()
