import collections, sys

def make_medicine(reverse_conversions, molecule, exhausted, memoized_steps):
    if molecule == 'e':
        print("found")
        return 0
    else:
        min_steps, not_found = sys.maxsize, set(exhausted)
        for target, source in ((target, source) for target, source in reverse_conversions.items() if target not in not_found):
            found_target = molecule.find(target)
            if source == 'e' and len(molecule) != 1:
                continue
            if found_target >= 0:
                undone_molecule = molecule[0:found_target] + source + molecule[found_target+len(target):]
                if undone_molecule in memoized_steps:
                    min_steps = min(min_steps, memoized_steps[undone_molecule])
                else:
                    min_steps = min(min_steps, make_medicine(reverse_conversions, undone_molecule, not_found, memoized_steps))
            else:
                not_found.add(target)
        memoized_steps[molecule] = 1 + min_steps if min_steps != sys.maxsize else sys.maxsize
        return 1 + min_steps if min_steps != sys.maxsize else sys.maxsize

with open('calibration', 'r') as f:
    conversions, reverse_conversions, memoized_steps, molecule = collections.defaultdict(list), {}, {}, None
    for line in f:
        split_line = line.rstrip().split(' => ')
        if len(split_line) == 2:
            conversions[split_line[0]].append(split_line[1])
            reverse_conversions[split_line[1]] = split_line[0]
        elif line.rstrip():
            molecule = [line[i] if not line[i+1].islower() else line[i] + line[i+1] for i in range(len(line)) if line[i].isupper() or line[i].isdigit()]
            distinct_molecules, molecule = set(''.join(molecule[0:i]) + replacement + ''.join(molecule[i+1:]) for i in range(len(molecule)) for replacement in conversions[molecule[i]]), line.rstrip()
            print(len(distinct_molecules))
    print(make_medicine(reverse_conversions, molecule, set(), memoized_steps))
