with open('directions', 'r') as f:
    x, y, history = 0, 0, set()
    history.add((x,y))
    for direction in f.read():
        y = y if direction not in ('^', 'v') else y + 1 if direction == '^' else y - 1
        x = x if direction not in ('<', '>') else x + 1 if direction == '>' else x - 1
        history.add((x,y))
    print(len(history))
