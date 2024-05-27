// https://rosettacode.org/wiki/100_prisoners
#include <iostream>
#include <chrono>
#include <random>
#include <algorithm>
#include <cassert>

using namespace std;

int* shuffledArray(int length, int minValue, int maxValue)
{
   assert(maxValue - minValue + 1 >= length && "Range is too small for the requested array length without duplicates.");

   int range = maxValue - minValue + 1;
   int * tempArray = new int[range];
   for (int i = 0; i < range; i++)
   {
      tempArray[i] = minValue + i;
   }

   unsigned seed = chrono::system_clock::now().time_since_epoch().count();
   shuffle(tempArray, tempArray + range, default_random_engine(seed));

   int* array = new int[length];
   for (int i = 0; i < length; ++i) {
      array[i] = tempArray[i];
   }

   delete[] tempArray;
   return array;
}

int noStrategy() {
   int* drawers;
   int* listOfDrawers;
   int freedPrisoners { 0 };

   drawers = shuffledArray(100, 1, 100);

   for (int prisoner = 0; prisoner < 100; prisoner++)
   {
      listOfDrawers = shuffledArray(50, 1, 100);
      for (int attempt = 0; attempt < 50; attempt++)
      {
         if (drawers[listOfDrawers[attempt] - 1] == prisoner + 1)
         {
            freedPrisoners++;
            break;
         }
      }
      delete[] listOfDrawers;
   }
   delete[] drawers;
   return freedPrisoners;
}

int bestStrategy() {
   int* drawers;
   int freedPrisoners { 0 };

   drawers = shuffledArray(100, 1, 100);

   for (int prisoner = 0; prisoner < 100; prisoner++) {
      int drawerToCheck = prisoner;
      for (int attempt = 0; attempt < 50; attempt++) {
         if (drawers[drawerToCheck] == prisoner + 1) {
            freedPrisoners++;
            // cout << " attempt: " << attempt + 1 << " checking drawer " << drawerToCheck << " for prisoner " << prisoner << " found " << drawers[drawerToCheck] << "\n";
            break;
         }
         else {
            // cout << " attempt: " << attempt  + 1 << " checking drawer " << drawerToCheck << " for prisoner " << prisoner << " found " << drawers[drawerToCheck] << "\n";
            drawerToCheck = drawers[drawerToCheck] - 1;
         }
      }
   }
   delete[] drawers;
   return freedPrisoners;
}



int main()
{
   int successfulSimulations { 0 };
   int simulations { 100000 };
   auto start = chrono::high_resolution_clock::now();
   auto end = chrono::high_resolution_clock::now();
   auto duration = chrono::duration_cast<chrono::milliseconds>(end - start).count();

   cout << successfulSimulations << "/" << simulations << " successful simulations, took " << duration << " ms\n";

   successfulSimulations = 0;
   start = chrono::high_resolution_clock::now();
   for (int i = 0; i < simulations; i++)
   {
      if (bestStrategy() == 100)
      {
         successfulSimulations++;
      }
   }
   end = chrono::high_resolution_clock::now();
   duration = chrono::duration_cast<chrono::milliseconds>(end - start).count();
   cout << successfulSimulations << "/" << simulations << " successful simulations, took " << duration << " ms\n";

}