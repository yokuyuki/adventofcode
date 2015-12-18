from itertools import product

def lights_on(stuck_lights):
    with open('initial_state', 'r') as f:
        state = stuck_lights([[col == '#' for col in row.rstrip()] for row in f])
        get_state = lambda i, j, state: state[i][j] if 0 <= i < len(state) and 0 <= j < len(state[0]) else False
        evaluate_on_state = lambda i, j, state: True if 2 <= sum(int(get_state(y, x, state)) for y, x in product((i-1, i, i+1), (j-1, j, j+1)) if y != i or x != j) <= 3 else False
        evaluate_off_state = lambda i, j, state: True if sum(int(get_state(y, x, state)) for y, x in product((i-1, i, i+1), (j-1, j, j+1)) if y != i or x != j) == 3 else False
        for steps in range(100):
            state = stuck_lights([[evaluate_on_state(i, j, state) if state[i][j] else evaluate_off_state(i, j, state) for j in range(len(state[i]))] for i in range(len(state))])
        print(sum(sum(row) for row in state))

lights_on(lambda state: state)
lights_on(lambda state: [[True if i in (0, len(state)-1) and j in (0, len(state)-1) else state[i][j] for j in range(len(state[i]))] for i in range(len(state))])
