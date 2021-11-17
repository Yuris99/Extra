function[deliper1, deliper2, income, deliper1_v, income_v] = getData() 
income = readmatrix("Data\1인당_지역총소득2013-2019.csv", "Range","B3:H19"); %단위 천원
population = readmatrix("Data\인구수2013-2019.csv", "Range","B3:H19");
cases = readmatrix("Data\체납건수2013-2019.xlsx", "Range","D7:J23");
delinquent = readmatrix("Data\2.3.1_체납액_정리_실적Ⅰ_지역·세목_2006_20211115201948.xlsx", "Range","C4:I20"); % 단위: 억원
% 통합 단위 천원
income = income * 1;
delinquent = delinquent * 100000;

deliper1 = delinquent./population; % 1인당 체납 금액

deliper2 = delinquent./cases; %1건당 체납 금액


%deliper = deliper(:);
%income = income(:);

%울산데이터 삭제
%deliper1(7, :) = [];
%income(7, :) = [];
data = [deliper1(:) income(:) deliper2(:)];
data = rmmissing(data); % 누락데이터 삭제
deliper1_v = data(:,1);
income_v = data(:,2);
deliper2_v = data(:,3);

end