#include <iostream>
#include <time.h>

using namespace std;

int arr[500005];

void shuffle() {
    srand(time(NULL));
    for(int i = 1; i <= 500000; i++) 
        arr[i] = i;

    //shuffle
    int temp, r;
    for(int i = 1; i <= 500000; i++) {
        r = rand() % 500000 + 1;
        temp = arr[i];
        arr[i] = arr[r];
        arr[r] = temp;
    }
}


int main(void) {
    shuffle();
    for(int i = 1; i <= 500000; i++) 
        printf("%d ", arr[i]);
    return 0;
}