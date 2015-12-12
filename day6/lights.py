import collections, functools, operator, re

with open('lighting_configuration', 'r') as f:
    lights = collections.defaultdict(lambda: collections.defaultdict(int))
    for step in f:
        m = re.search(r"([\w ]+) (\d+),(\d+) through (\d+),(\d+)", step)
        instruction, x1, y1, x2, y2 = m.group(1), int(m.group(2)), int(m.group(3)), int(m.group(4)), int(m.group(5))
        fn = (lambda light: 1) if instruction == 'turn on' else (lambda light: 0) if instruction == 'turn off' else (lambda light: (light + 1) % 2)
        for x in range(x1, x2 + 1):
            for y in range(y1, y2 + 1):
                lights[x][y] = fn(lights[x][y])
    print(functools.reduce(lambda sum, col: sum + functools.reduce(operator.add, col.values(), 0), lights.values(),0))
