//https://rosettacode.org/wiki/2048
#include <iostream>
#include <sstream>
#include <iomanip>
#include <random>

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

      int generateRandomNumber(int x) {
         std::random_device rd;
         std::mt19937 gen(rd());
         std::uniform_int_distribution<> dis(1, x);
         return dis(gen);
      }

      bool generateRandomCell() {
         int possiblePositions = countBlankCells();
         if (possiblePositions == 0) return false;
         int newCell = generateRandomNumber(possiblePositions);
         cout << "new cell at: " << newCell << endl;
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

      void setCellValue(int row, int col, int value) {
         boardGrid[row][col] = value;
      }

      int getCellValue(int row, int col) {
         return boardGrid[row][col];
      }
};


int main() {
   board b;
   cout << b << endl;
   return 0;
}