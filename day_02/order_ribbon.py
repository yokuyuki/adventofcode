with open('gifts', 'r') as f:
    print(sum(2*(x+y) + x*y*z for x, y, z in [sorted(map(int, size.rstrip().split('x'))) for size in f]))
