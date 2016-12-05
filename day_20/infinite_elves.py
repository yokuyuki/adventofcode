presents, house = 36000000, 1
while True:
    current_presents = sum(i for i in range(1, house+1) if house % i == 0) * 10
    print(house, current_presents)
    if presents == current_presents:
        print(house)
        break
    else:
        house += 1
