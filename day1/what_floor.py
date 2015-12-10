with open('floor_instructions', 'r') as f:
    instructions = f.read()
    print reduce(lambda floor, instruction: floor + 1 if instruction == '(' else floor - 1, instructions, 0)
