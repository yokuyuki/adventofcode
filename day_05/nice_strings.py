import collections, functools

def is_nice_string(string):
    vowels, letter_pairs, bad_pairs = functools.reduce(lambda state, letter_pair: (state[0] + 1 if letter_pair[0] in 'aeiou' else state[0], state[1] + 1 if letter_pair[0] == letter_pair[1] else state[1], state[2] + 1 if letter_pair[1] + letter_pair[0] in ('ab', 'cd', 'pq', 'xy') else state[2]), zip(string, ' ' + string), (0, 0, 0))
    return vowels >= 3 and letter_pairs >= 1 and bad_pairs == 0

def is_super_nice_string(string):
    non_overlapping_pair, three_letter_palindrome, letter_pairs = False, False, collections.defaultdict(list)
    for index, letter, previous_letter, previous_previous_letter in zip(range(len(string)), string, ' ' + string, '  ' + string):
        non_overlapping_pair = True if previous_previous_letter == letter else non_overlapping_pair
        three_letter_palindrome = True if previous_letter + letter in letter_pairs and any(index - old_index > 1 for old_index in letter_pairs[previous_letter + letter]) else three_letter_palindrome
        if non_overlapping_pair and three_letter_palindrome: return True
        letter_pairs[previous_letter + letter].append(index)

with open('strings', 'r') as f:
    strings = [line for line in f]
    print(functools.reduce(lambda count, string: count + 1 if is_nice_string(string) else count, strings, 0))
    print(functools.reduce(lambda count, string: count + 1 if is_super_nice_string(string) else count, strings, 0))
