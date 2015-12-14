import functools, itertools

def look_and_say(seq):
    return ''.join([str(len(tuple(grouper))) + letter for letter, grouper in itertools.groupby(seq)])

print(len(functools.reduce(lambda previous_seq, i: look_and_say(previous_seq), range(40), '3113322113')))
print(len(functools.reduce(lambda previous_seq, i: look_and_say(previous_seq), range(50), '3113322113')))
