import collections, itertools, re

def light_pattern(instruction_translation):
    with open('lighting_configuration', 'r') as f:
        lights = collections.defaultdict(lambda: collections.defaultdict(int))
        for step in f:
            m = re.search(r"([\w ]+) (\d+),(\d+) through (\d+),(\d+)", step)
            instruction, x1, y1, x2, y2 = m.group(1), int(m.group(2)), int(m.group(3)), int(m.group(4)), int(m.group(5))
            fn = instruction_translation(instruction)
            for x, y in itertools.product(range(x1, x2 + 1), range(y1, y2 + 1)):
                lights[x][y] = fn(lights[x][y])
        print(sum(sum(col.values()) for col in lights.values()))

light_pattern(lambda instruction: (lambda light: 1) if instruction == 'turn on' else (lambda light: 0) if instruction == 'turn off' else (lambda light: (light + 1) % 2))
light_pattern(lambda instruction: (lambda light: light + 1) if instruction == 'turn on' else (lambda light: light - 1 if light > 0 else 0) if instruction == 'turn off' else (lambda light: light + 2))
