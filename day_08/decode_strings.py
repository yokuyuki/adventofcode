from functools import reduce

with open('santas_list', 'r') as f:
    print(reduce(lambda diff, literal: diff + len(literal.strip()) - len(eval(literal.strip())), f, 0))
