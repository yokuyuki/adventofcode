import functools

def is_nice_string(string):
    vowels, letter_pairs, bad_pairs = functools.reduce(lambda state, letter_pair: (state[0] + 1 if letter_pair[0] in 'aeiou' else state[0], state[1] + 1 if letter_pair[0] == letter_pair[1] else state[1], state[2] + 1 if letter_pair[1] + letter_pair[0] in ('ab', 'cd', 'pq', 'xy') else state[2]), zip(string, ' ' + string), (0, 0, 0))
    return vowels >= 3 and letter_pairs >= 1 and bad_pairs == 0

with open('strings', 'r') as f:
    print(functools.reduce(lambda count, string: count + 1 if is_nice_string(string) else count, f, 0))
