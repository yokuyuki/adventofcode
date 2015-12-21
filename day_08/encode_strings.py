with open('santas_list', 'r') as f:
    print(sum(2 + literal.count('\\') + literal.count('\"') for literal in f))
