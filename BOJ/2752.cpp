#include <bits/stdc++.h>

using namespace std;

int main(void) {
	int a[3];
	cin >> a[0] >> a[1] >> a[2];
	sort(a, a+3);
	printf("%d %d %d\n", a[0], a[1], a[2]);
	return 0;
}
