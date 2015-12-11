def floor_incrementer(floor, enumeration):
    if floor == -1: print enumeration[0]
    return floor + 1 if enumeration[1] == '(' else floor - 1

with open('floor_instructions', 'r') as f:
    reduce(lambda floor, enumeration: floor_incrementer(floor, enumeration), enumerate(f.read()), 0)
