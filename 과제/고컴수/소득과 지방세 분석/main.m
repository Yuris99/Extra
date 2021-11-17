importdata getData.m;
importdata linearReg.m;

format long;

% 데이터 불러오기
[deliper1, deliper2, income, deliper1_v, income_v] = getData();

% 지역별 데이터 PLOT
for i = 1:0 % 가리기
    % 세종(데이터누락), 인천(데이터이상) 제외
    if i == 8
        continue
    end
    income_s = income(i,:);
    income_s = income_s(:);
    deliper_s = deliper(i, :);
    deliper_s = deliper_s(:);


    linearReg(income_s, deliper_s);
end

% 일자로 출력
%linearReg(income_v, deliper_v);
linearReg(income_v, deliper2(:));

hold on
xlabel('1인당 지역 총 소득(천원)')
ylabel('1인당 체납액(천원)')