import re

with open('document', 'r') as f:
    print(sum(int(number) for number in re.findall(r"(?<!\")(-?\d+)(?!\")", f.read())))
