import time
import os
import pandas as pd

path = './schedules/'

week = {"월" : 0,
        "화" : 1,
        "수" : 2,
        "목" : 3,
        "금" : 4,
        "토" : 5,
        "일" : 6,}

#최소 시작시간
start_hour = 9

#최대 시작시간
end_hour = 23

#5:평일만 7: 주말까지
week_search = 5

#최대 허용 인원
ignore_member = 0

#최소 회의시간 (분)
conf_time = 60

#시간 배열
schedules = [[[0 for m in range(60)] for h in range(24)] for w in range(7)]

def main():
    csvlist = os.listdir(path)
    #print(csvlist)

    #csv파일 가져오기
    os.chdir('schedules')
    for file in csvlist:
        data = pd.read_csv(file)
        print(data)
        for i in data.index:
            counter = data['시작시간'][i].split(':')
            counter[0] = int(counter[0])
            counter[1] = int(counter[1])
            endtime = data['끝시간'][i].split(':')
            endtime[0] = int(endtime[0])
            endtime[1] = int(endtime[1])
            weeker = week[data['요일'][i]]
            while counter[0] != endtime[0] and counter[1] != endtime[1]:
                schedules[weeker][counter[0]][counter[1]] += 1
                counter[1] += 1
                if counter[1] >= 60:
                    counter[0] += 1
                    counter[1] = 0

    cnt = 1
    for w in range(week_search):
        check_roatate = 0
        start_h = 0
        start_m = 0
        for h in range(start_hour, end_hour):
            for m in range(60):
                if schedules[w][h][m] <= ignore_member:
                    if check_roatate == 0:
                        start_h = h
                        start_m = m
                    check_roatate += 1
                elif check_roatate != 0:
                    if check_roatate >= conf_time:
                        for strs, checker in week.items():
                            if checker == w:
                                we = strs
                        print("회의시간 " + str(cnt) + "안: " + str(we) + "요일 " + str(start_h) + "시 " + str(start_m) + "분 ~ " + str(h) + "시 " + str(m) + "분")
                        cnt += 1
                    check_roatate = 0


if __name__=="__main__":
    main()