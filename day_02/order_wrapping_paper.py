import functools

def wrap(dimensions):
    return functools.reduce(lambda sum, prods: sum + 2*prods[0]*prods[1], zip(dimensions, dimensions[1:] + dimensions[:1]), 0) + dimensions[0]*dimensions[1]

with open('gifts', 'r') as f:
    sizes = [sorted(map(int, size.rstrip().split('x'))) for size in f]
    print(functools.reduce(lambda sum, size: sum + wrap(size), sizes, 0))
