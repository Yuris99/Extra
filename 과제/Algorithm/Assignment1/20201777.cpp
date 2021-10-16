#include <iostream>
#include <string.h>
#include <time.h>

using namespace std;

int arr[500010];
int U[500010];
long long int cnt_comp = 0;
long long int cnt_swap = 0;

double run_time;

void swap(int& a, int& b) {
    int temp = a;
    a = b;
    b = temp;
    cnt_swap ++;
    return;
}


void insertion(int low, int high) {
    int i, j;
    for(i = low + 1; i <= high; i++) {
        j = i;
        cnt_comp ++;
        while(j - 1 >= low && arr[j] < arr[j-1]) {
            cnt_comp ++;
            swap(arr[j], arr[j-1]);
            j--;
        }
    }
}

void merge_merge(int low, int mid, int high) {
    int i = low, j = mid + 1, k = low;
    //int *U = new int[high + 1];
    while(i <= mid && j <= high) {
        if(arr[i] < arr[j]) {
            U[k++] = arr[i++];
        }
        else {
            U[k++] = arr[j++];
        }
        cnt_comp ++;
        cnt_swap ++;
    }
    while(i <= mid) {
        U[k++] = arr[i++];
        cnt_swap ++;
    }
    while(j <= high) {
        U[k++] = arr[j++];
        cnt_swap ++;
    }

    for(int l = low; l <= high; l++)
        arr[l] = U[l];
    //delete(U);
}

void merge(int low, int high) {
    int mid;
    if(low < high) {
        mid = (low + high) / 2;
        merge(low, mid);
        merge(mid+1, high);
        merge_merge(low, mid, high);
    }
    
}

void quick_partition(int low, int high, int& pivotpoint) {
    int i, j;
    int pivotitem = arr[low];
    j = low;
    for(i = low + 1; i <= high; i++) {
        if(arr[i] < pivotitem) {
            j++;
            swap(arr[i], arr[j]);
        }
        pivotpoint = j;
        cnt_comp ++;
    }
    swap(arr[low], arr[pivotpoint]);
}

void quick(int low, int high) {
    int pivotpoint;
    if(low < high) {
        quick_partition(low, high, pivotpoint);
        quick(low, pivotpoint-1);
        quick(pivotpoint+1, high);
    }
}


void heapify(int now, int high) {
    int left = now * 2;
    int right = now * 2 + 1;
    int big = now;
    if(left < high && arr[left] > arr[big])
        big = left;
    if(right < high && arr[right] > arr[big])
        big = right;
    
    if(left < high) cnt_comp++;
    if(right < high) cnt_comp++;


    if(big != now) {
        swap(arr[now], arr[big]);
        heapify(big, high);
    }
}
void buildHeap(int n) {
    for(int i = n / 2; i > 0; i--) 
        heapify(i, n);
}

void heap(int low, int high) {
    buildHeap(high);
    for(int i = high; i >= low; i--) {
        swap(arr[1], arr[i]);
        heapify(1, i);
    }
}



int main(int argc, char *argv[]) {
    int n = 500000;

    //get input data
    for(int i = 1; i <= n; i++) 
        cin >> arr[i]; 

    //ignore when arg not set
    if(argc < 2) return 0;
    
    //time count
    clock_t start, end;

    //select sort
    if(!strcmp(argv[1], "insertion")) {
        start = clock();
        insertion(1, n);
        end = clock();
    }
    else if(!strcmp(argv[1], "merge")) {
        start = clock();
        merge(1, n);
        end = clock();
    }
    else if(!strcmp(argv[1], "quick")) {
        start = clock();
        quick(1, n);
        end = clock();
    } 
    else if(!strcmp(argv[1], "heap")) {
        start = clock();
        heap(1, n);
        end = clock();
    } 
    
    run_time = (double)(end - start);

    //print result
    for(int i = 1; i <= 500000; i++) 
        cout << arr[i] << " "; 

    //print info
    //cout << "\n\ncnt_comp: " << cnt_comp << endl;
    //cout << "\n\ncnt_swap: " << cnt_swap << endl;
    //cout << "\nrun_time: " << run_time << "ms\n";
    

    return 0;
}

