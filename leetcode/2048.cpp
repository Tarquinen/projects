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

   public:
      board() {
         for (int i = 0; i < 4; i ++) {
            for (int j = 0; j < 4; j++) {
               boardGrid[i][j] = 0;
            }
         }
         // generaterandomcell();
         // generaterandomcell();
         setCellValue(0, 0, 2);
         setCellValue(0, 1, 2);

         setCellValue(1, 1, 2);
         setCellValue(1, 2, 2);

         setCellValue(2, 0, 2);
         setCellValue(2, 1, 2);
         setCellValue(2, 2, 2);
         setCellValue(2, 3, 2);

         
         setCellValue(3, 0, 2);
         setCellValue(3, 1, 2);
         setCellValue(3, 2, 4);
      }

      friend ostream& operator<<(ostream& os, const board& b) {
         os << b.toString();
         return os;
      }

      bool generateRandomCell() {
         int possiblePositions = countBlankCells();
         if (possiblePositions == 0) return false;
         int newCell = generateRandomNumber(possiblePositions);
         // cout << "new cell at: " << newCell << endl;
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

      void pushLeft() {
         bool modified[4][4] { false };
         for (int i = 0; i < 4; i++) {
            // move cells over
            for (int j = 1; j < 4; j++) {
               int currentCell = boardGrid[i][j];
               if (currentCell != 0) {
                  if (boardGrid[i][j-1] == 0) {
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
      }

      void pushRight() {
         bool modified[4][4] { false };
         for (int i = 0; i < 4; i++) {
            // move cells over
            for (int j = 2; j >= 0; j--) {
               int currentCell = boardGrid[i][j];
               if (currentCell != 0) {
                  if (boardGrid[i][j+1] == 0) {
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
      }

      void pushUp() {
         bool modified[4][4] { false };
         for (int j = 0; j < 4; j++) {
            // move cells over
            for (int i = 1; i < 4; i++) {
               int currentCell = boardGrid[i][j];
               if (currentCell != 0) {
                  if (boardGrid[i-1][j] == 0) {
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
      }

      void pushDown() {
         bool modified[4][4] { false };
         for (int j = 0; j < 4; j++) {
            // move cells over
            for (int i = 2; i >= 0; i--) {
               int currentCell = boardGrid[i][j];
               if (currentCell != 0) {
                  if (boardGrid[i+1][j] == 0) {
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
      }

      void setCellValue(int row, int col, int value) {
         boardGrid[row][col] = value;
      }

      int getCellValue(int row, int col) {
         return boardGrid[row][col];
      }
};

void clearScreen() {
   cout << "\033[2J\033[1;1H";
}

int main() {
   clearScreen();
   board b;
   cout << b << endl;
   // std::this_thread::sleep_for(std::chrono::seconds(1));
   
   while (true) {
      string move;
      cin >> move;
      if (move == "w") {
         clearScreen();
         b.pushUp();
      }
      else if (move == "s") {
         clearScreen();
         b.pushDown();
      }
      else if (move == "d") {
         clearScreen();
         b.pushRight();
      }
      else if (move == "a") {
         clearScreen();
         b.pushLeft();
      }
      b.generateRandomCell();
      cout << b << endl;
   }
}