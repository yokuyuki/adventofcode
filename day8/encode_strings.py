from functools import reduce

with open('santas_list', 'r') as f:
    print(reduce(lambda diff, literal: diff + 2 + literal.count('\\') + literal.count('\"'), f, 0))
