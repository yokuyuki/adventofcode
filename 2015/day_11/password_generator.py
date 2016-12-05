def next_password(password):
    is_valid_password = lambda password: len(set([letter*2 for prev_letter, letter in zip(' ' + password, password) if letter == prev_letter])) >= 2 and any(ord(prev_prev_letter) + 2 == ord(prev_letter) + 1 == ord(letter) for prev_prev_letter, prev_letter, letter in zip('  '+ password, ' ' + password, password)) and all(letter not in 'oil' for letter in password)
    integerize = lambda password: sum((ord(letter) - ord('a')) * 26**index for index, letter in enumerate(password[::-1]))
    alphabetize = lambda integer: '' if integer == 0 else alphabetize(integer // 26) + chr(integer % 26 + ord('a'))
    new_password = alphabetize(integerize(password) + 1)
    while not is_valid_password(new_password):
        new_password = alphabetize(integerize(new_password) + 1)
    print(new_password)
    return new_password

next_password(next_password('cqjxjnds'))
