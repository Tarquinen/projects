//https://rosettacode.org/wiki/2048
#include <iostream>
#include <sstream>

using namespace std;

class board {
   private:
      int boardGrid [4][4];
   
   public:
      board() {
         for (int i = 0; i < 4; i ++) {
            for (int j = 0; j < 4; j++) {
               boardGrid[i][j] = 0;
            }
         }
      }

      string printCell(int x) const {
         if (x > 10) {
            ostringstream oss;
            oss << "    " << x;
            return oss.str();
         } 
      }
   
      string toString() const {
         return "+------+------+------+------+";
      }

      friend ostream& operator<<(ostream& os, const board& b) {
         os << b.toString();
         return os;
      }
};


int main() {
   cout << "test" << endl;
   board b;
   cout << b << endl;
   return 0;
}