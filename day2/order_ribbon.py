import functools, operator

def wrap(dimensions):
    return 2*(dimensions[0]+dimensions[1]) + functools.reduce(operator.mul, dimensions)

with open('gifts', 'r') as f:
    sizes = [sorted(map(int, size.rstrip().split('x'))) for size in f]
    print(functools.reduce(lambda sum, size: sum + wrap(size), sizes, 0))
