// https://rosettacode.org/wiki/100_doors
#include <iostream>

using namespace std;

int main() 
{
   bool doors[100] = {false};

   for (int i = 0; i < 100; i++) 
   {
      for (int j = 0; j < 100; j+= (i+1))
      {
         doors[j] = !doors[j];
      }
   }

   for (int i = 0; i < 100; i++)
   {
      if (doors[i] == true)
      {
         cout << "door " << i << " is open" << endl;
      }
   }
}