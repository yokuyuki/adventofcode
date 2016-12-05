from collections import defaultdict

def is_super_nice_string(string):
    letter_pairs = defaultdict(list)
    any(letter_pairs[previous_letter + letter].append(index) for index, letter, previous_letter in zip(range(len(string)), string, ' ' + string))
    return any(previous_previous_letter == letter for index, letter, previous_letter, previous_previous_letter in zip(range(len(string)), string, ' ' + string, '  ' + string)) and any(previous_letter + letter in letter_pairs and any(index - old_index > 1 for old_index in letter_pairs[previous_letter + letter]) for index, letter, previous_letter, previous_previous_letter in zip(range(len(string)), string, ' ' + string, '  ' + string))

with open('strings', 'r') as f:
    strings = [line for line in f]
    is_nice_string = lambda string: sum(1 for letter in string if letter in 'aeiou') >= 3 and any(letter == previous_letter for letter, previous_letter in zip(string, ' ' + string)) and all(previous_letter + letter not in ('ab', 'cd', 'pq', 'xy') for letter, previous_letter in zip(string, ' ' + string))
    print(sum(1 for string in strings if is_nice_string(string)))
    print(sum(1 for string in strings if is_super_nice_string(string)))
