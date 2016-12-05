with open('floor_instructions', 'r') as f:
    print(sum(1 if instruction == '(' else -1 for instruction in f.read()))
