//https://rosettacode.org/wiki/2048
#include <iostream>
#include <sstream>
#include <iomanip>
#include <random>
#include <chrono>
#include <thread>

using namespace std;

class board {
   private:
      int boardGrid [4][4]; // row, column
   
      string printCell(int x) const {
         ostringstream oss;
         if (x == 0) {
            oss << "      ";
         } else {
            oss << setw(5) << x << " ";
         }
         return oss.str();
      }

      string toString() const {
         string result = "";
         string rowDivider = "+------+------+------+------+\n";
         for (int i = 0; i < 4; i++) {
            result += rowDivider + "|";
            for (int j = 0; j < 4; j++) {
               result += printCell(boardGrid[i][j]);
               result += "|";
            }
            result += "\n";
         }
         result += rowDivider;
         return result;
      }

      int generateRandomNumber(int x) {
         std::random_device rd;
         std::mt19937 gen(rd());
         std::uniform_int_distribution<> dis(1, x);
         return dis(gen);
      }

      int countBlankCells() const {
         int count = 0;
         for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
               if (boardGrid[i][j] == 0) {
                  count++;
               }
            }
         }
         return count;
      }

      bool surroundingCellsMatch(int row, int col) {
         int currentCell = boardGrid[row][col];
         if (row > 0 && boardGrid[row-1][col] == currentCell) return true;
         if (row < 3 && boardGrid[row+1][col] == currentCell) return true;
         if (col > 0 && boardGrid[row][col-1] == currentCell) return true;
         if (col < 3 && boardGrid[row][col+1] == currentCell) return true;
         return false;
      }

   public:
      board() {
         for (int i = 0; i < 4; i ++) {
            for (int j = 0; j < 4; j++) {
               boardGrid[i][j] = 0;
            }
         }
         generateRandomCell();
         generateRandomCell();
      }

      friend ostream& operator<<(ostream& os, const board& b) {
         os << b.toString();
         return os;
      }

      void setCellValue(int row, int col, int value) {
         boardGrid[row][col] = value;
      }

      int getCellValue(int row, int col) {
         return boardGrid[row][col];
      }

      bool generateRandomCell() {
         int possiblePositions = countBlankCells();
         if (possiblePositions == 0) return false;
         int newCell = generateRandomNumber(possiblePositions);
         int currCell = 0;
         for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
               if (boardGrid[i][j] == 0) {
                  currCell++;
               }
               if (currCell == newCell) {
                  if (generateRandomNumber(10) == 1) {
                     setCellValue(i, j, 4);
                     return true;
                  }
                  setCellValue(i, j, 2);
                  return true;
               }
            }
         }
         return true;
      }

      bool pushLeft() {
         bool modified[4][4] { false };
         bool gridMoved { false };
         for (int i = 0; i < 4; i++) {
            // move cells over
            for (int j = 1; j < 4; j++) {
               int currentCell = boardGrid[i][j];
               if (currentCell != 0) {
                  if (boardGrid[i][j-1] == 0) {
                     gridMoved = true;
                     boardGrid[i][j] = 0;
                     boardGrid[i][j-1] = currentCell;
                     if (modified[i][j]) {
                        modified[i][j] = false;
                        modified[i][j-1] = true;
                     }
                  }
                  if (j > 1 && getCellValue(i, j-2) == 0) {
                     j-=2;
                  }
               }
            }
            // combine cells if possible
            for (int j = 1; j < 4; j++) {
               int currentCell = boardGrid[i][j];
               if (currentCell != 0) {
                  if (boardGrid[i][j-1] == currentCell && !modified[i][j] && !modified[i][j-1]) {
                     gridMoved = true;
                     boardGrid[i][j-1] = currentCell * 2;
                     boardGrid[i][j] = 0;
                     modified[i][j-1] = true;
                     modified[i][j] = false;
                     i--;
                     break;
                  }
               }
            }
         }

         return gridMoved;
      }

      bool pushRight() {
         bool modified[4][4] { false };
         bool gridMoved { false };
         for (int i = 0; i < 4; i++) {
            // move cells over
            for (int j = 2; j >= 0; j--) {
               int currentCell = boardGrid[i][j];
               if (currentCell != 0) {
                  if (boardGrid[i][j+1] == 0) {
                     gridMoved = true;
                     boardGrid[i][j] = 0;
                     boardGrid[i][j+1] = currentCell;
                     if (modified[i][j]) {
                        modified[i][j] = false;
                        modified[i][j+1] = true;
                     }
                  }
                  if (j < 2 && getCellValue(i, j+2) == 0) {
                     j+=2;
                  }
               }
            }
            // combine cells if possible
            for (int j = 2; j >= 0; j--) {
               int currentCell = boardGrid[i][j];
               if (currentCell != 0) {
                  if (boardGrid[i][j+1] == currentCell && !modified[i][j] && !modified[i][j+1]) {
                     gridMoved = true;
                     boardGrid[i][j+1] = currentCell * 2;
                     boardGrid[i][j] = 0;
                     modified[i][j+1] = true;
                     modified[i][j] = false;
                     i--;
                     break;
                  }
               }
            }
         }

         return gridMoved;
      }

      bool pushUp() {
         bool modified[4][4] { false };
         bool gridMoved { false };
         for (int j = 0; j < 4; j++) {
            // move cells over
            for (int i = 1; i < 4; i++) {
               int currentCell = boardGrid[i][j];
               if (currentCell != 0) {
                  if (boardGrid[i-1][j] == 0) {
                     gridMoved = true;
                     boardGrid[i][j] = 0;
                     boardGrid[i-1][j] = currentCell;
                     if (modified[i][j]) {
                        modified[i][j] = false;
                        modified[i-1][j] = true;
                     }
                  }
                  if (i > 1 && getCellValue(i-2, j) == 0) {
                     i-=2;
                  }
               }
            }
            // combine cells if possible
            for (int i = 1; i < 4; i++) {
               int currentCell = boardGrid[i][j];
               if (currentCell != 0) {
                  if (boardGrid[i-1][j] == currentCell && !modified[i][j] && !modified[i-1][j]) {
                     gridMoved = true;
                     boardGrid[i-1][j] = currentCell * 2;
                     boardGrid[i][j] = 0;
                     modified[i-1][j] = true;
                     modified[i][j] = false;
                     j--;
                     break;
                  }
               }
            }
         }
         return gridMoved;
      }

      bool pushDown() {
         bool modified[4][4] { false };
         bool gridMoved { false };
         for (int j = 0; j < 4; j++) {
            // move cells over
            for (int i = 2; i >= 0; i--) {
               int currentCell = boardGrid[i][j];
               if (currentCell != 0) {
                  if (boardGrid[i+1][j] == 0) {
                     gridMoved = true;
                     boardGrid[i][j] = 0;
                     boardGrid[i+1][j] = currentCell;
                     if (modified[i][j]) {
                        modified[i][j] = false;
                        modified[i+1][j] = true;
                     }
                  }
                  if (i < 2 && getCellValue(i+2, j) == 0) {
                     i+=2;
                  }
               }
            }
            // combine cells if possible
            for (int i = 2; i >= 0; i--) {
               int currentCell = boardGrid[i][j];
               if (currentCell != 0) {
                  if (boardGrid[i+1][j] == currentCell && !modified[i][j] && !modified[i+1][j]) {
                     gridMoved = true;
                     boardGrid[i+1][j] = currentCell * 2;
                     boardGrid[i][j] = 0;
                     modified[i+1][j] = true;
                     modified[i][j] = false;
                     j--;
                     break;
                  }
               }
            }
         }
         return gridMoved;
      }

      bool checkWin(int winNumber) {
         for (int i = 0; i < 4; i++ ) {
            for (int j = 0; j < 4; j++) {
               if (boardGrid[i][j] == winNumber) {
                  return true;
               }
            }
         }
         return false;
      }

      bool checkLoss() {
         if (countBlankCells() == 0) {
            for (int i = 0; i < 4; i++) {
               for (int j = 0; j < 4; j++) {
                  if (surroundingCellsMatch(i, j)) {
                     return false;
                  }
               }
            }
            return true;
         }
         return false;
      }
};

void clearScreen() {
   cout << "\033[2J\033[1;1H"; // lol wtf why does this clear screen
}

int main() {
   clearScreen();
   board b;
   cout << b << endl;
   
   while (true) {
      if (b.checkLoss()) {
         cout << "you've lost" << endl;
         break;
      }
      if (b.checkWin(2048)) { 
         cout << "you've won" << endl;
         break;
      }

      string move;
      cin >> move;
      
      if (move == "w") {
         if (b.pushUp()) {
            b.generateRandomCell();
         }
      }
      else if (move == "s") {
         if (b.pushDown()) {
            b.generateRandomCell();
         }
      }
      else if (move == "d") {
         if (b.pushRight()) {
            b.generateRandomCell();
         }
      }
      else if (move == "a") {
         if (b.pushLeft()) {
            b.generateRandomCell();
         }
      }
      clearScreen();
      cout << b << endl;
   }
}
