import functools, operator

with open('gifts', 'r') as f:
    sizes = [sorted(map(int, size.rstrip().split('x'))) for size in f]
    wrap = lambda dimensions: 2*(dimensions[0]+dimensions[1]) + functools.reduce(operator.mul, dimensions)
    print(functools.reduce(lambda sum, size: sum + wrap(size), sizes, 0))
