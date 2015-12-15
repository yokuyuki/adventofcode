import functools, re

with open('document', 'r') as f:
    numbers = re.findall(r"(?<!\")(-?\d+)(?!\")", f.read())
    print(functools.reduce(lambda sum, number: sum + int(number), numbers, 0))
