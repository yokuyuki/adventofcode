from functools import reduce

def is_valid_password(password):
	distinct_pairs = set([letter*2 for letter, previous_letter in zip(password, ' ' + password) if letter == previous_letter])
	three_consecutive_letters, no_bad_letters = reduce(lambda state, letter_triplet: (True if ord(letter_triplet[2]) + 2 == ord(letter_triplet[1]) + 1 == ord(letter_triplet[0]) else state[0], False if letter_triplet[0] in 'oil' else state[1]), zip(password, ' ' + password, '  '+ password), (False, True))
	return len(distinct_pairs) >= 2 and three_consecutive_letters and no_bad_letters

integerize = lambda password: reduce(lambda num, enumeration: num + (ord(enumeration[1]) - ord('a')) * 26**enumeration[0],enumerate(password[::-1]), 0)
alphabetize = lambda integer: '' if integer == 0 else alphabetize(integer // 26) + chr(integer % 26 + ord('a'))
password = 'cqjxjnds'
while not is_valid_password(password):
	password = alphabetize(integerize(password) + 1)
print(password)