with open('gifts', 'r') as f:
    print(sum(3*x*y + 2*x*z + 2*y*z for x, y, z in [sorted(map(int, size.rstrip().split('x'))) for size in f]))
