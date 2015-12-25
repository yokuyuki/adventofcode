from collections import defaultdict

with open('instructions', 'r') as f:
    instructions, address, a, b = [x.rstrip() for x in f], 0, 0, 0
    next_register = defaultdict(lambda: lambda a, b: (a, b), (('hlf a', lambda a, b: (a/2, b)), ('hlf b', lambda a, b: (a, b/2)), ('tpl a', lambda a, b: (a*3, b)), ('tpl b', lambda a, b: (a, b*3)), ('inc a', lambda a, b: (a+1, b)), ('inc b', lambda a, b: (a, b+1))))
    next_address = defaultdict(lambda: lambda register, address, delta: address+1, (('jmp', lambda register, address, delta: address+int(delta)), ('jie', lambda register, address, delta: address+int(delta) if register % 2 == 0 else address+1), ('jio', lambda register, address, delta: address+int(delta) if register == 1 else address+1)))
    while address < len(instructions):
        a, b = next_register[instructions[address][:5]](a, b)
        address = next_address[instructions[address][:3]](a if 'a' == instructions[address][4] else b, address, instructions[address][4:].split(', ')[-1])
    print(b)
