#include <bits/stdc++.h>

using namespace std;

bool des(int a, int b) {
	return a > b;
}

int main(void) {
	int S = 0;
	int A[51], B[51];
	int n;
	cin >> n;
	for(int i = 0; i < n; i++) 
		cin >> A[i];
	for(int i = 0; i < n; i++) 
		cin >> B[i];
	sort(A, A+n);
	sort(B, B+n, des);
	
	for(int i = 0; i < n; i++) 
		S += A[i] * B[i];
	cout << S << endl;
	return 0;
}
