function [x_plot, y_plot] = linearReg(x, y)

x = x'; y = y';

n = length(x); % 입력된 값의 개수

sx = sum(x); sy = sum(y); % 각각의 합
sx2 = sum(x.*x); sxy = sum(x.*y); sy2 = sum(y.*y);

a(1) = (n*sxy - sx*sy) / (n*sx2 - sx^2); % 최소제곱 회귀분석
a(2) = sy/n - a(1)*sx/n;

r2 = ((n*sxy-sx*sy)/sqrt(n*sx2-sx^2)/sqrt(n*sy2-sy^2))^2;

x_plot = linspace(min(x),max(x),2);
y_plot = a(1)*x_plot + a(2);
scatter(x, y)
hold on
plot(x_plot, y_plot)
grid on
disp(r2);

end


