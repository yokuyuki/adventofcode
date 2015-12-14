from functools import reduce

def look_and_say(seq):
    new_seq, last_count, last_digit = reduce(lambda state, letter: (state[0] + str(state[1]) + state[2] if letter != state[2] else state[0], state[1] + 1 if letter == state[2] else 1, letter), seq, ['', '', ''])
    return new_seq + str(last_count) + last_digit

print(len(reduce(lambda previous_seq, i: look_and_say(previous_seq), range(40), '3113322113')))
