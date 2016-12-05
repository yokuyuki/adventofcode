with open('santas_list', 'r') as f:
    print(sum(len(literal.strip()) - len(eval(literal.strip())) for literal in f))
