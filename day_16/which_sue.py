import re

with open('sues', 'r') as f:
    hint = {'children': 3, 'cats': 7, 'samoyeds': 2, 'pomeranians': 3, 'akitas': 0, 'vizslas': 0, 'goldfish': 5, 'trees': 3, 'cars': 2, 'perfumes': 1}
    for sue in f:
        m = re.search(r"^Sue (\d+): (\w+): (\d+), (\w+): (\d+), (\w+): (\d+)", sue)
        sue_num, item1, num1, item2, num2, item3, num3 = m.group(1), m.group(2), int(m.group(3)), m.group(4), int(m.group(5)), m.group(6), int(m.group(7))
        if item1 in hint and hint[item1] == num1 and item2 in hint and hint[item2] == num2 and item3 in hint and hint[item3] == num3:
            print(sue_num)
            break
