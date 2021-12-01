#include <iostream>
#include <cstring>

using namespace std;

int N;
int K;
int W[20];
int V[20];

int maxprofit = -1;

int Input() {
    cin >> N >> K;
    for(int i = 0; i < N; i++) 
        cin >> W[i] >> V[i];

}

void BTknapsack(int i, int profit, int weight) {
    if(weight <= K && profit > maxprofit) {

    }
}

bool promising(int i, int profit, int weight) {
    int j, k;
    int totweight;
    float bound;
    if(weight >= K) return false;
    else {
        j = i + 1;
        bound = profit;
        totweight = weight;
        while((j <= N) && (totweight + W[j] <= K)) {
            totweight+= W[j];
            bound += V[j];
            j++;
        }
        k = j;
        if(k <= N)
            bound += (K-totweight)*V[k]/W[k];
        return bound > maxprofit;
    }
}

void Backtracking() {
    
}

void Breadth() {

}

void Best() {

}


int main(int argc, char *argv[]) {
    Input();

    if(strcmp(argv[1], "backtracking") == 0) {
        Backtracking();
    } else if(strcmp(argv[1], "breadth") == 0) {
        Breadth();
    } else if(strcmp(argv[1], "best") == 0) {
        Best();
    }
    return 0;
}