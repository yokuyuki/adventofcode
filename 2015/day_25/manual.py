from functools import reduce

which_code = lambda row, col: sum(range(row + col - 1)) + col
print(reduce(lambda value, i: value * 252533 % 33554393, range(1, which_code(2947, 3029)), 20151125))
