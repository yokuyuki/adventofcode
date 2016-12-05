with open('directions', 'r') as f:
    x, y, history = [0, 0], [0, 0], set()
    history.add((0,0))
    for i, direction in enumerate(f.read()):
        y[i%2] = y[i%2] if direction not in ('^', 'v') else y[i%2] + 1 if direction == '^' else y[i%2] - 1
        x[i%2] = x[i%2] if direction not in ('<', '>') else x[i%2] + 1 if direction == '>' else x[i%2] - 1
        history.add((x[i%2],y[i%2]))
    print(len(history))
