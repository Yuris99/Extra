#include <bits/stdc++.h>

using namespace std;

long long int arc[100] = {0, 1};

long long int f(int n) {
	if(arc[n] != 0 || n == 0) return arc[n];
	arc[n] = f(n-1) + f(n-2);
	return arc[n];
}

int main (void) {
	int n;
	cin >> n;
	cout << f(n) << endl;
	return 0;
}
