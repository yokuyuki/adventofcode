with open('floor_instructions', 'r') as f:
    print reduce(lambda floor, instruction: floor + 1 if instruction == '(' else floor - 1, f.read(), 0)
