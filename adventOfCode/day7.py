# https://adventofcode.com/2023/day/7

def part1(inputLines):
    hands = []
    for i in range(len(inputLines)):
        hands.append(inputLines[i].split(" "))

    for i, hand in enumerate(hands):
        hands[i].append(handType(hand))
        hands[i].append(handValue(hand))

    sortedHands = []
    for hand in hands:
        lenSorted = len(sortedHands)
        for i in range(lenSorted):
            if hand[2] > sortedHands[i][2]:
                sortedHands.insert(i, hand)
                break
            elif hand[2] == sortedHands[i][2]:
                if hand[3] > sortedHands[i][3]:
                    sortedHands.insert(i, hand)
                    break
            if i == lenSorted - 1:
                sortedHands.append(hand)
        if lenSorted == 0:
            sortedHands.append(hand)
    
    for hand in sortedHands:
        print(hand[0] + ", ", end = '')
    print()

    winnings = 0
    rank = len(hands)
    
    for hand in sortedHands:
        # print(f"rank: {rank}")
        winnings += int(hand[1]) * rank
        # print(int(hand[1]) * rank)
        rank -= 1
    print(f"Winnings: {winnings}")

def handType(hand):
    handDic = {}
    for card in hand[0]:
        if card in handDic:
            handDic[card] += 1
        else:
            handDic[card] = 1

    # five of a kind
    for card in handDic:
        if handDic[card] == 5:
            return 7

    # four of a kind
    for card in handDic:
        if handDic[card] == 4:
            return 6

    # full house
    pair = False   
    pair3 = False
    for card in handDic:
        if handDic[card] == 2:
            pair = True
        if handDic[card] == 3:
            pair3 = True
    if pair and pair3:
        return 5

    # three of a kind
    for card in handDic:
        if handDic[card] == 3:
            return 4

    # two pair
    pairCounter = 0
    for card in handDic:
        if handDic[card] == 2:
            pairCounter += 1
    if pairCounter == 2:
        return 3

    # one pair
    for card in handDic:
        if handDic[card] == 2:
            return 2

    # high card
    return 1

def handValue(hand):
    # converts the hand into a pentadecimal value
    handDic = {'T': 10, 'J': 11, 'Q': 12, 'K': 13, 'A': 14}
    value = 0
    for i, card in enumerate(reversed(hand[0])):
        if card in handDic:
            cardValue = handDic[card]
        else:
            cardValue = int(card)
        value += cardValue * 15 ** i
    return value
        
def part2(inputLines):
    hands = []
    for i in range(len(inputLines)):
        hands.append(inputLines[i].split(" "))

    for i, hand in enumerate(hands):
        hands[i].append(handType2(hand))
        hands[i].append(handValue2(hand))    

    sortedHands = []
    for hand in hands:
        lenSorted = len(sortedHands)
        for i in range(lenSorted):
            if hand[2] > sortedHands[i][2]:
                sortedHands.insert(i, hand)
                break
            elif hand[2] == sortedHands[i][2]:
                if hand[3] > sortedHands[i][3]:
                    sortedHands.insert(i, hand)
                    break
            if i == lenSorted - 1:
                sortedHands.append(hand)
        if lenSorted == 0:
            sortedHands.append(hand)
    
    # for hand in sortedHands:
    #     print(hand[0] + ", ", end = '')
    # print()

    winnings = 0
    rank = len(hands)
    
    for hand in sortedHands:
        # print(f"rank: {rank}")
        winnings += int(hand[1]) * rank
        # print(int(hand[1]) * rank)
        rank -= 1
    print(f"Winnings: {winnings}")

def handType2(hand):
    handDic = {}
    JCounter = 0
    for card in hand[0]:
        if card == 'J':
            JCounter += 1
        elif card in handDic:
            handDic[card] += 1
        else:
            handDic[card] = 1
    if handDic:
        mostRepeatedCard = max(handDic.values())
        for key in handDic:
            if handDic[key] == mostRepeatedCard:
                handDic[key] += JCounter
                break
    else:
        return 7

    # five of a kind
    for card in handDic:
        if handDic[card] == 5:
            return 7

    # four of a kind
    for card in handDic:
        if handDic[card] == 4:
            return 6

    # full house
    pair = False   
    pair3 = False
    for card in handDic:
        if handDic[card] == 2:
            pair = True
        if handDic[card] == 3:
            pair3 = True
    if pair and pair3:
        return 5

    # three of a kind
    for card in handDic:
        if handDic[card] == 3:
            return 4

    # two pair
    pairCounter = 0
    for card in handDic:
        if handDic[card] == 2:
            pairCounter += 1
    if pairCounter == 2:
        return 3

    # one pair
    for card in handDic:
        if handDic[card] == 2:
            return 2

    # high card
    return 1
    
def handValue2(hand):
    # converts the hand into a pentadecimal value
    handDic = {'T': 10, 'J': 1, 'Q': 12, 'K': 13, 'A': 14}
    value = 0
    for i, card in enumerate(reversed(hand[0])):
        if card in handDic:
            cardValue = handDic[card]
        else:
            cardValue = int(card)
        value += cardValue * 15 ** i
    return value

def main():
    input = open('C:\\Users\\danny\\OneDrive\\Desktop\\Algo\\School-repo\\adventOfCode\\day7.txt')
    inputLines = input.read().splitlines()
    

    part2(inputLines)

if __name__ == "__main__":
    main()