import functools

with open('floor_instructions', 'r') as f:
    print(functools.reduce(lambda floor, instruction: floor + 1 if instruction == '(' else floor - 1, f.read(), 0))
